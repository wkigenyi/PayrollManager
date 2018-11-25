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
import systems.tech247.hr.TblEmployeeTransactions;
import systems.tech247.hr.TblPeriods;
import systems.tech247.api.ReloadableQueryCapability;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;

/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeeTransactions extends ChildFactory<Object> implements LookupListener {
    
    Lookup.Result<NodeRefreshEmployeeTransaction> result;
    QueryEmployeeTransaction query;
    Boolean edit;
    Employees emp;
    
    public FactoryEmployeeTransactions(Employees emp, TblPeriods p,Boolean edit){
        query = new QueryEmployeeTransaction(emp, p);
        this.edit = edit;
        this.emp = emp;
        result = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshEmployeeTransaction.class);
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
                    TopComponent tc = new EmployeeTransactionEditorTopComponent(emp);
                    tc.open();
                    tc.requestActive();
                }
            }));
        }
        
        
        
        
        for(TblEmployeeTransactions trans: query.getList()){
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
            if(key instanceof TblEmployeeTransactions){
                node = new NodeEmployeePeriodTransaction((TblEmployeeTransactions)key);
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
        Lookup.Result<NodeRefreshEmployeeTransaction> rslt = (Lookup.Result<NodeRefreshEmployeeTransaction>)ev.getSource();
        for(NodeRefreshEmployeeTransaction r: rslt.allInstances()){
            refresh(true);
        }
    }
}
