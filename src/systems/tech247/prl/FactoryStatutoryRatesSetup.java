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
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import systems.tech247.util.CapCreatable;

/**
 *
 * @author Admin
 */
public class FactoryStatutoryRatesSetup extends ChildFactory<SetupItem> {
    
    

    @Override
    protected boolean createKeys(List<SetupItem> list) {
        InstanceContent icp = new InstanceContent();
        icp.add(new CapCreatable() {
            @Override
            public void create() {
                //Add New Tier
                TopComponent tc = new PAYETierEditorTopComponent();
                tc.open();
                tc.requestActive();
                
            }
        });
        list.add(new SetupItem("Pay As You Earn Tiers", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent payeTc =WindowManager.getDefault().findTopComponent("PAYETopComponent");
                payeTc.open();
                payeTc.requestActive();
            }
        },icp));
        list.add(new SetupItem("N.S.S.F", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent nssfTc =WindowManager.getDefault().findTopComponent("NSSFTopComponent");
                nssfTc.open();
                nssfTc.requestActive();
            }
        }));
        InstanceContent ic = new InstanceContent();
        ic.add(new CapCreatable() {
            @Override
            public void create() {
                //Add New Tier
                TopComponent tc = new LSTTierEditorTopComponent();
                tc.open();
                tc.requestActive();
                
            }
        });
        list.add(new SetupItem("Local Service Tax Tiers",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent lstTc =WindowManager.getDefault().findTopComponent("LSTTopComponent");
                lstTc.open();
                lstTc.requestActive();
            }
        },ic));
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
