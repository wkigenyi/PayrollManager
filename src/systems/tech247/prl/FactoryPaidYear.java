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
import systems.tech247.util.Year;

/**
 *
 * @author WKigenyi
 */
public class FactoryPaidYear extends ChildFactory<TransactionYear> {
    
    QueryPaidYear query;
    EMP emp;
    public FactoryPaidYear(EMP emp){
        query = new QueryPaidYear(emp.getEm());
       this.emp = emp; 
    }
    
    @Override
    protected boolean createKeys(List<TransactionYear> toPopulate) {
        
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
         TransactionYear tyr = new TransactionYear(emp, new Year(yr));
         toPopulate.add(tyr);
        }
        return true;
    }
    
    @Override
    protected Node createNodeForKey(TransactionYear key) {
        
        Node node =  null;
        try {
            node = new TransactionYearNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
