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
import systems.tech247.util.EMP;

/**
 *
 * @author WKigenyi
 */
public class FactoryPaidEmployee extends ChildFactory<EMP> {
    
    EMP emp;
    public FactoryPaidEmployee(EMP emp){
        this.emp = emp;
    }
    
    @Override
    protected boolean createKeys(List<EMP> toPopulate) {
        
        toPopulate.add(emp);
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(EMP key) {
        
        Node node = null;
        try{
            node = new PaidEmployeeNode(key);
        }catch(IntrospectionException ex){
            ex.printStackTrace();
        }
        return node;
    }
}
