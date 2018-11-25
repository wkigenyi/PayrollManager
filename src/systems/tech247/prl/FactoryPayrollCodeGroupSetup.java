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
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;

/**
 *
 * @author Admin
 */
public class FactoryPayrollCodeGroupSetup extends ChildFactory<SetupItem> implements LookupListener {
    
    Lookup.Result<NodeRefreshPCodeGroup> rslt = UtilityPLR.getInstance().lookup.lookupResult(NodeRefreshPCodeGroup.class);
    
    public FactoryPayrollCodeGroupSetup(){
        rslt.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<SetupItem> list) {
        //Add the option to add a new payroll code
        SetupItem addnew = new SetupItem("Add New", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent editor = new PayrollCodeGroupEditorTopComponent();
                editor.open();
                editor.requestActive();
            }
        });
        list.add(addnew);

        //Get the system pcodes
        QueryPayrollCodeGroups querySys = new QueryPayrollCodeGroups();
        
        
        list.add(new SetupItem("Payroll Code Groups",Children.create(new FactoryPayrollCodeGroups(), true)));
        //Get the system pcodes
        
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

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPCodeGroup> rslt = (Lookup.Result<NodeRefreshPCodeGroup>)ev.getSource();
        for(NodeRefreshPCodeGroup n: rslt.allInstances()){
            refresh(true);
        }
    }
    
}
