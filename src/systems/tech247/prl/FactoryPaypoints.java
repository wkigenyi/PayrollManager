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
import systems.tech247.hr.TblPaypoint;
import systems.tech247.util.AddTool;
import systems.tech247.util.NodeAddTool;
import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryPaypoints extends ChildFactory<Object> {
    
    QueryPayPoints query;
    boolean add;
    
    public FactoryPaypoints(boolean add){
        query = new QueryPayPoints();
       this.add = add;
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
                    //add a paypoint
                }
            }));
        }
        
        
        for(TblPaypoint trans: query.getList()){
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
            if(key instanceof TblPaypoint)
            
            node = new PayPointNode((TblPaypoint)key);
            else
            node = new NodeAddTool((AddTool)key);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
