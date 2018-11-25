/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPeriodTransactions;
import systems.tech247.hr.TblPeriods;
import systems.tech247.api.ReloadableQueryCapability;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeePayslipTransactions extends ChildFactory<TblPeriodTransactions> implements LookupListener {
    
    QueryPayrollTransaction query;
    
    
    Lookup.Result<NodeRefreshPayslip> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPayslip.class);
    
    public FactoryEmployeePayslipTransactions(Employees emp, TblPeriods p){
        query = new QueryPayrollTransaction(emp, p);
        rslt.addLookupListener(this);
       
    }
    
    @Override
    protected boolean createKeys(List<TblPeriodTransactions> toPopulate) {
        
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        
        for(TblPeriodTransactions trans: query.getList()){
            //if(trans.getPayrollCodeID().getShowBalanceInPayslip()){
                toPopulate.add(trans);
            //}
         
         
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(TblPeriodTransactions key) {
        
        Node node =  null;
        try {
           
            node = new NodeEmployeePayrollTransaction(key);
            
        } catch (Exception ex) {
            NotifyUtil.error("Problem with transaction", key.getPayrollCodeID().getPayrollCodeName(), ex, false);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPayslip> rslt = (Lookup.Result<NodeRefreshPayslip>)ev.getSource();
        for(NodeRefreshPayslip nrp: rslt.allInstances()){
            refresh(true);
            
        }
    }
}
