/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import systems.tech247.util.SetupItem;

/**
 *
 * @author Admin
 */
public class NodeSetupItem extends AbstractNode{
    
    SetupItem item;
    public NodeSetupItem(SetupItem item){
        super(item.getKids());
        this.item = item;
        setDisplayName(item.getDescription());
    }

    @Override
    public Action getPreferredAction() {
        return item.getDefaultAction();
    }
    
    
    
}
