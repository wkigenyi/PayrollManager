/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.hr.TblPaypoint;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class PayPointNode extends  BeanNode<TblPaypoint>{
    
    private final InstanceContent instanceContent;
    
    public PayPointNode(TblPaypoint p) throws IntrospectionException {
        this(p,new InstanceContent());
    }
    
    private PayPointNode(TblPaypoint p, InstanceContent ic) throws IntrospectionException{
        super(p,Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(p);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                //We shall edit you
            }
        });
        
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                //We can Delete
            }
        });
        
        setDisplayName(p.getPayPointName());
        setIconBaseWithExtension("systems/tech247/util/icons/company.png");
    }
    
}
