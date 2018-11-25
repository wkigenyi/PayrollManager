/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.util.ConcurrentModificationException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.api.ReloadableQueryCapability;
import systems.tech247.hr.TblPayrollCodeGroups;

/**
 *
 * @author WKigenyi
 */
public class FactoryPayrollCodes extends ChildFactory<PayrollCodeSelectable> implements LookupListener {
    
    QueryPayrollCodes query;
    Boolean edit;
    Lookup.Result<NodeRefreshPayrollCode> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPayrollCode.class);
    
    public FactoryPayrollCodes(QueryPayrollCodes query,Boolean edit){
        this.query = query;
        this.edit = edit;
        rslt.addLookupListener(this);
        
       
    }
    
    public FactoryPayrollCodes(TblPayrollCodeGroups g){
        query =new QueryPayrollCodes();
        this.edit = false;
        String sql = "SELECT * FROM TblPayrollCode p WHERE p.pcodegroupID="+g.getPCodeGroupID()+"";
        query.setSqlString(sql);
        
    }
    
    @Override
    protected boolean createKeys(List<PayrollCodeSelectable> toPopulate) {
        
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        try{
        for(TblPayrollCode trans: query.getList()){
            //if(trans.getPayrollCodeID().getShowBalanceInPayslip()){
                toPopulate.add(new PayrollCodeSelectable(Boolean.FALSE, trans));
            //}
         
         
        }
        }catch(ConcurrentModificationException ex){
            
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(PayrollCodeSelectable key) {
        
        Node node =  null;
        try {
            
            node = new NodePayrollCode(key,edit);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPayrollCode> rslt = (Lookup.Result<NodeRefreshPayrollCode>)ev.getSource();
        for(NodeRefreshPayrollCode nrp:rslt.allInstances()){
            refresh(true);
        }
    }
}
