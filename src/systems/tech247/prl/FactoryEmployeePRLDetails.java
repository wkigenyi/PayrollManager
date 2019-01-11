/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import systems.tech247.util.SetupItem;
import systems.tech247.util.NodeSetupItem;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.hr.Employees;
import systems.tech247.util.CapCreatable;
import systems.tech247.util.CapEmail;
import systems.tech247.util.CapPreview;


/**
 *
 * @author WKigenyi
 */
public class FactoryEmployeePRLDetails extends ChildFactory<SetupItem> {
    
    Employees emp;
    
    public FactoryEmployeePRLDetails(Employees emp){
        this.emp = emp;
    }
    
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {
        InstanceContent icp = new InstanceContent();
        icp.add(new CapCreatable() {
            @Override
            public void create() {
                TopComponent tc = new EmployeePayrollCodeEditorTopComponent(emp);
                tc.open();
                tc.requestActive();
            }
        });
        toPopulate.add(new SetupItem("Payroll Codes",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new EmployeePayrollCodesTopComponent(emp);
                tc.open();
                tc.requestActive();
            }
        }));
        
        toPopulate.add(new SetupItem("Period Transactions",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new EmployeePeriodTransactionsTopComponent(emp);
                tc.open();
                tc.requestActive();
            }
        }));
        
        InstanceContent ic =  new InstanceContent();
        ic.add(new CapPreview() {
            @Override
            public void preview() {
                //We Preview
            }
        });
        ic.add(new CapEmail() {
            @Override
            public void email() {
                //We email the payslip to the employee
            }
        });
        toPopulate.add(new SetupItem("Payslip Information", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new EmployeePayslipInfoTopComponent(emp);
                tc.open();
                tc.requestActive();
                        
            }
        }
                ));
        
        
        
        
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
