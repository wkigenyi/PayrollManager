/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Admin
 */
public class NodeTransaction extends BeanNode<TransactionCover> {
    
    public NodeTransaction(TransactionCover bean) throws IntrospectionException {
        super(bean, Children.LEAF,Lookups.singleton(bean));
        setDisplayName(bean.getTransaction().getPDescription());
    }
    
}
