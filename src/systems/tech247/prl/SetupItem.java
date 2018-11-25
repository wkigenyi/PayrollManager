/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.Children;

/**
 *
 * @author Admin
 */
public class SetupItem {
    
    String description;
    Children kids;
    private Action defaultAction;
    
    public SetupItem(String description){
        this(description, Children.LEAF, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }
    
    public SetupItem(String description, Children kids){
        this(description, kids, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    public SetupItem(String description, Action defaultAction){
        this(description, Children.LEAF, defaultAction);
    }

    public String getDescription() {
        return description;
    }

    public Children getKids() {
        return kids;
    }
    
    
    
    public SetupItem(String description, Children kids, Action defaultAction){
        this.kids = kids;
        this.description = description;
        this.defaultAction = defaultAction;
    }

    /**
     * @return the defaultAction
     */
    public Action getDefaultAction() {
        return defaultAction;
    }
    
}
