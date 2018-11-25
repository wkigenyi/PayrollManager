/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import systems.tech247.hr.Employees;
import systems.tech247.api.ReloadableQueryCapability;
import systems.tech247.hr.TblEmployeePayrollCode;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;

/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeePayrollCodes extends ChildFactory<Object> implements LookupListener {
    
    Lookup.Result<NodeRefreshEmployeePayrollCode> result;
    QueryEmployeeTransactionCode query;
    Boolean edit;
    Employees emp;
    
    public FactoryEmployeePayrollCodes(Employees emp,Boolean edit){
        query = new QueryEmployeeTransactionCode(emp);
        this.edit = edit;
        this.emp = emp;
        result = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshEmployeePayrollCode.class);
        result.addLookupListener(this);
       
    }
    
    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        if(edit){
            toPopulate.add(new AddTool(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TopComponent tc = new EmployeePayrollCodeEditorTopComponent(emp);
                    tc.open();
                    tc.requestActive();
                }
            }));
        }
        
        
        
        
        for(TblEmployeePayrollCode trans: query.getList()){
            //if(trans.getPayrollCodeID().getShowBalanceInPayslip()){
                toPopulate.add(trans);
            //}
         
         
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Object key) {
        
        Node node =  null;
        try {
            if(key instanceof TblEmployeePayrollCode){
                node = new NodeEmployeePayrollCode((TblEmployeePayrollCode)key);
            }else if(key instanceof AddTool){
                node = new NodeAddTool((AddTool)key);
            }
            
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshEmployeePayrollCode> rslt = (Lookup.Result<NodeRefreshEmployeePayrollCode>)ev.getSource();
        for(NodeRefreshEmployeePayrollCode r: rslt.allInstances()){
            refresh(true);
        }
    }
}
