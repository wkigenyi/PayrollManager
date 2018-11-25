/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

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
import systems.tech247.util.SetupItem;

/**
 *
 * @author WKigenyi
 */
public class FactorySetup extends ChildFactory<SetupItem> {
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {
        InstanceContent icp = new InstanceContent();
        icp.add(new CapCreatable() {
            @Override
            public void create() {
                TopComponent tc = new PayrollCodeEditorTopComponent();
                tc.open();
                tc.requestActive();
            }
        });
        toPopulate.add(new SetupItem("Payroll Codes",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new PayrollCodesTopComponent();
                tc.open();
                tc.requestActive();
            }
        }, icp));
        toPopulate.add(new SetupItem("Payroll Code Groups", Children.create(new FactoryPayrollCodeGroupSetup(), true)));
        toPopulate.add(new SetupItem("Pay Points"));
        toPopulate.add(new SetupItem("Cost Centers"));
        toPopulate.add(new SetupItem("Payrolls",Children.create(new FactoryPayroll(Boolean.TRUE), true)));
        toPopulate.add(new SetupItem("Statutory Rates Setup",Children.create(new FactoryStatutoryRatesSetup(), true)));
        toPopulate.add(new SetupItem("Match Departments TO SUN"));
        toPopulate.add(new SetupItem("Salary Calculator",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent salaryTc = WindowManager.getDefault().findTopComponent("SalaryCalculatorTopComponent");
                salaryTc.open();
            }
        }));
        
        
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
