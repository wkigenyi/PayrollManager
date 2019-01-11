/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.util.SetupItem;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.util.CapCreatable;


/**
 *
 * @author WKigenyi
 */
public class FactoryPLRSetup extends ChildFactory<SetupItem> {
    
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
                TopComponent tc = new PayrollCodesTopComponent("",true,false);
                tc.open();
                tc.requestActive();
            }
        }, icp));
        toPopulate.add(new SetupItem("Payroll Code Groups", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TopComponent tc = WindowManager.getDefault().findTopComponent("PayrollCodeGroupsTopComponent");
                        tc.open();
                        tc.requestActive();
                    }
            
        }));
        toPopulate.add(new SetupItem("Pay Points", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TopComponent tc = WindowManager.getDefault().findTopComponent("PayPointsTopComponent");
                        tc.open();
                        tc.requestActive();
                    }
            
        }));
        toPopulate.add(new SetupItem("Cost Centers", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TopComponent tc = WindowManager.getDefault().findTopComponent("CostCentersTopComponent");
                        tc.open();
                        tc.requestActive();
                    }
            
        }));
        toPopulate.add(new SetupItem("Payrolls", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TopComponent tc = new PayrollsTopComponent("edit");
                        tc.open();
                        tc.requestActive();
                    }
            
        }));
        InstanceContent icpp = new InstanceContent();
        icpp.add(new CapCreatable() {
            @Override
            public void create() {
                //Add New Tier
                TopComponent tc = new PAYETierEditorTopComponent();
                tc.open();
                tc.requestActive();
                
            }
        });
        toPopulate.add(new SetupItem("Pay As You Earn Tiers", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent payeTc =WindowManager.getDefault().findTopComponent("PAYETopComponent");
                payeTc.open();
                payeTc.requestActive();
            }
        },icpp));
        toPopulate.add(new SetupItem("N.S.S.F Setup", new AbstractAction() {
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
        toPopulate.add(new SetupItem("Local Service Tax Tiers",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent lstTc =WindowManager.getDefault().findTopComponent("LSTTopComponent");
                lstTc.open();
                lstTc.requestActive();
            }
        },ic));
        toPopulate.add(new SetupItem("Match Departments TO SUN"));
        toPopulate.add(new SetupItem("Salary Calculator",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent salaryTc = WindowManager.getDefault().findTopComponent("SalaryCalculatorTopComponent");
                salaryTc.open();
            }
        }));
        
        

        
        toPopulate.add(new SetupItem("Post Global Transaction", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TopComponent txEditor = WindowManager.getDefault().findTopComponent("TransactionEditorTopComponent");
                        txEditor.open();
                    }
            
        }));
        
        toPopulate.add(new SetupItem("Run Payroll",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT e FROM Employees e WHERE e.isDisengaged=0";
                List<Employees> employees  = DataAccess.searchEmployees(sql);
                UtilityPLR.postTx(employees);
            }
        }));
        
        toPopulate.add(new SetupItem("Import Transactions",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc;
                tc = WindowManager.getDefault().findTopComponent("ImportTransactionsTopComponent");
                tc.open();
                tc.requestActive();

            }
        }));
        
        toPopulate.add(new SetupItem("Send Employee Payslips",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = WindowManager.getDefault().findTopComponent("EmailerTopComponent");
                tc.open();
                tc.requestActive();
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
