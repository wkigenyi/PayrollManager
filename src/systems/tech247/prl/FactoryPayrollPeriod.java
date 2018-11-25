/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import systems.tech247.hr.TblPeriods;

import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryPayrollPeriod extends ChildFactory<TblPeriods> implements LookupListener {
    
    QueryPayrollPeriod query;
    Lookup.Result<NodeRefreshPeriod> rslt =  UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPeriod.class);
    
    public FactoryPayrollPeriod(){
        query = new QueryPayrollPeriod();
        rslt.addLookupListener(this);
    }
    
    @Override
    protected boolean createKeys(List<TblPeriods> toPopulate) {
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        for(TblPeriods month: query.getList()){
            
            toPopulate.add(month);
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(TblPeriods key) {
        
        Node node =  null;
        try {
            node = new NodePayrollPeriod(new PeriodCover(key));
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPeriod> rslt = (Lookup.Result<NodeRefreshPeriod>)ev.getSource();
        for(NodeRefreshPeriod nrp: rslt.allInstances()){
            refresh(true);
        }
    }
}
