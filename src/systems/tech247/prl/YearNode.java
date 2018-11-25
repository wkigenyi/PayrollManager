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
 * @author WKigenyi
 */
public class YearNode  extends BeanNode<Integer>{
    
   
    
    public YearNode(Integer bean) throws IntrospectionException{
        super(bean,Children.LEAF,Lookups.singleton(bean));
        
        setDisplayName(bean+"");
        setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
    }
    
    
    
}
