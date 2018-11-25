/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Admin
 */
public class NodeSetup extends  AbstractNode{

    public NodeSetup() {
        super(Children.create(new FactorySetup(), true));
        setDisplayName("Setup");
    }
    
}
