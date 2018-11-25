/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;
import systems.tech247.util.EMP;


/**
 *
 * @author WKigenyi
 */
public class PaidEmployeeNode extends AbstractNode {
    
    EMP bean;
    
    public PaidEmployeeNode(EMP bean) throws IntrospectionException{
        super(Children.create(new FactoryPaidYear(bean), true),Lookups.singleton(bean));
        setIconBaseWithExtension("systems/tech247/icons/user.png");
        setDisplayName(bean.getEm().getSurName()+" "+bean.getEm().getOtherNames());
        this.bean=bean;
    }
}
