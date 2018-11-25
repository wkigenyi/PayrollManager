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
import systems.tech247.hr.PrlMonthlyPAYETableTiers;

import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryPAYETiers extends ChildFactory<PrlMonthlyPAYETableTiers> implements LookupListener {
    
    QueryPAYETiers query;
    Lookup.Result<NodeRefreshPAYE> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPAYE.class);
    
    public FactoryPAYETiers(QueryPAYETiers query){
        this.query = query;
        rslt.addLookupListener(this);
    }
    
    @Override
    protected boolean createKeys(List<PrlMonthlyPAYETableTiers> toPopulate) {
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        for(PrlMonthlyPAYETableTiers tier: query.getList()){
            
            toPopulate.add(tier);
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(PrlMonthlyPAYETableTiers key) {
        
        Node node =  null;
        try {
            node = new PAYETierNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPAYE> rslt = (Lookup.Result<NodeRefreshPAYE>)ev.getSource();
        for(NodeRefreshPAYE nrp : rslt.allInstances()){
            refresh(true);
        }
    }
}
