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
import systems.tech247.util.EMP;
import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author WKigenyi
 */
public class FactoryFinacialYears extends ChildFactory<Integer> {
    
    QueryFinancialYears query;
    EMP emp;
    public FactoryFinacialYears(){
        query = new QueryFinancialYears();
       
    }
    
    @Override
    protected boolean createKeys(List<Integer> toPopulate) {
        
        //get this ability from the look
        ReloadableQueryCapability r = query.getLookup().lookup(ReloadableQueryCapability.class);
        //Use the ability
        if(r != null){
            try{
                r.reload();
            }catch(Exception ex){
                
            }
        }
        
        
        
        
        
        for(int yr: query.getList()){
         
         toPopulate.add(yr);
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Integer key) {
        
        Node node =  null;
        try {
            node = new YearNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
