/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.util.SetupItem;
import systems.tech247.util.NodeSetupItem;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import systems.tech247.util.CapCreatable;

/**
 *
 * @author WKigenyi
 */
public class FactoryPLRReports extends ChildFactory<SetupItem> {
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {

        toPopulate.add(new SetupItem("Payroll Report",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        },"systems/tech247/util/icons/capex.png"));
        toPopulate.add(new SetupItem("Employee Payslips",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        },"systems/tech247/util/icons/capex.png"));
        toPopulate.add(new SetupItem("Payroll Code Variance",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        },"systems/tech247/util/icons/capex.png"));
        
        
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(SetupItem key) {
        
        Node node =  null;
        try {
            
            node = new NodeSetupItem(key);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}