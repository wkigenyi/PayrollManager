/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;

/**
 *
 * @author WKigenyi
 */
public class TransactionYearNode  extends BeanNode{
    
    public TransactionYearNode(TransactionYear bean) throws IntrospectionException{
        super(bean,Children.LEAF);
        
        setDisplayName(bean.getYr().getYear()+"");
        setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
        
        
    }
}
