/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import systems.tech247.hr.TblCostCenter;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;

import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryCostCenters extends ChildFactory<Object> {
    
    QueryCostCenters query;
    boolean add;
    
    public FactoryCostCenters(boolean add){
        query = new QueryCostCenters();
        this.add=add;
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
        
        if(add){
            toPopulate.add(new AddTool(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
             //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }));
        }
        
        
        for(TblCostCenter tier: query.getList()){
            
            toPopulate.add(tier);
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Object key) {
        
        Node node =  null;
        try {
            if(key instanceof TblCostCenter)
                node = new NodeCostCenter(new CostCenterCover((TblCostCenter)key));
            else
                node = new NodeAddTool((AddTool)key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
