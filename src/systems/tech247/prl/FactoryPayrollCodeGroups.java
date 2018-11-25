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
import systems.tech247.hr.TblPayrollCodeGroups;
import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryPayrollCodeGroups extends ChildFactory<TblPayrollCodeGroups > {
    
    QueryPayrollCodeGroups query;
    
    public FactoryPayrollCodeGroups(){
        query = new QueryPayrollCodeGroups();
       
    }
    
    @Override
    protected boolean createKeys(List<TblPayrollCodeGroups> toPopulate) {
        
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        
        for(TblPayrollCodeGroups trans: query.getList()){
            //if(trans.getPayrollCodeID().getShowBalanceInPayslip()){
                toPopulate.add(trans);
            //}
         
         
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(TblPayrollCodeGroups key) {
        
        Node node =  null;
        try {
            
            node = new NodePayrollCodeGroup(key);
            
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
