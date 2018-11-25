/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;

import systems.tech247.hr.CompanyDetails;
import systems.tech247.hr.Employees;
import systems.tech247.prleditorpanels.PLRCostCenterPanel;
import systems.tech247.prleditorpanels.PLRLHTPanel;
import systems.tech247.prleditorpanels.PLRNSSFPanel;
import systems.tech247.prleditorpanels.PLRPAYEPanel;
import systems.tech247.prleditorpanels.PLRPayPointsPanel;


import systems.tech247.prleditorpanels.PLRPeriodMgtPanel;
import systems.tech247.util.ChangeEditorPanel;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//Payroll//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayrollTopComponent",
        iconBase = "systems/tech247/util/icons/settings.png", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true, roles={"Payroll"})
//@ActionID(category = "PLR", id = "systems.tech247.prl.PayrollTopComponent")
//@ActionReference(path = "Menu/Payroll" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PayrollAction",
        
        preferredID = "PayrollTopComponent"
)
@Messages({
    "CTL_PayrollAction=Payroll",
    "CTL_PayrollTopComponent=Payroll",
    "HINT_PayrollTopComponent= "
})
public final class PayrollTopComponent extends TopComponent implements LookupListener, TreeSelectionListener{

    DataAccess da = new DataAccess();
    Lookup lookup;
    TopComponent empTc = WindowManager.getDefault().findTopComponent("EmployeesTopComponent");
    Lookup.Result<Employees> empRslt = empTc.getLookup().lookupResult(Employees.class);
    TopComponent companyTc = WindowManager.getDefault().findTopComponent("CompanyTopComponent");
    Lookup.Result<CompanyDetails> compRslt = companyTc.getLookup().lookupResult(CompanyDetails.class);
    
    JTree tree;
    DefaultMutableTreeNode selectedNode;
    public PayrollTopComponent() {
        initComponents();
        setName(Bundle.CTL_PayrollTopComponent());
        setToolTipText(Bundle.HINT_PayrollTopComponent());
        lookup = new AbstractLookup(UtilityPLR.editorPRLIC);
        final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Payroll Setup");
        panelHolder.setLayout(new BorderLayout());
        tree = new JTree(top);
        createNodes(top);      
        tree.setModel(new DefaultTreeModel(top));
        treeHolder.setLayout(new BorderLayout());
        treeHolder.add(tree);
        expandAllNodes(tree, 0, tree.getRowCount());
        tree.addTreeSelectionListener(this);
        tree.setSelectionPath(new TreePath(selectedNode.getPath()));
        
        
        
       compRslt.addLookupListener(this);
        resultChanged(new LookupEvent(empRslt));
        
        
        jtCurrentPeriod.setText(da.getCurrentPeriod().getPeriodMonth()+" "+da.getCurrentPeriod().getPeriodYear());
        
        
        //Associated lookups
       
        //Lookup employeePayslips =  ExplorerUtils.createLookup(UtilityPLR.emPLREmployeePayslips, getActionMap());
        
        
        Lookup paypoints =  ExplorerUtils.createLookup(UtilityPLR.emPLRPayPoints, getActionMap());
        
        
       
        
                
        
        Lookup payeTiers =  ExplorerUtils.createLookup(UtilityPLR.emPLRPayeTiers, getActionMap());
        
        Lookup lhtTiers =  ExplorerUtils.createLookup(UtilityPLR.emPLRLHTTiers, getActionMap());
        
        Lookup costCenters =  ExplorerUtils.createLookup(UtilityPLR.emPLRCostCenters, getActionMap());
        
        Lookup payrollPeriods =  ExplorerUtils.createLookup(UtilityPLR.emPLRPayrollPeriods, getActionMap());
        
        //Lookup employeeCostCenters =  ExplorerUtils.createLookup(UtilityPLR.emPLREmployeeCostCenters, getActionMap());
        /*
        Lookup employeeProf =  ExplorerUtils.createLookup(UtilityPDR.emEmployeeProf, getActionMap());
        Lookup employeeBankAccounts =  ExplorerUtils.createLookup(UtilityPDR.emEmployeeBankAccounts, getActionMap());
        */
        ProxyLookup mergedLookup = new ProxyLookup(lookup,paypoints,payeTiers,lhtTiers,costCenters,payrollPeriods);
        associateLookup(mergedLookup);
        
        jtCompany.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Object[] options = {new JButton("Close")};
                DialogDisplayer.getDefault().notify(new DialogDescriptor(companyTc, "Select A Company",true, options,null,DialogDescriptor.DEFAULT_ALIGN,null,null));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                            }
        });

    }
    
    private void createNodes(DefaultMutableTreeNode root){
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode detail = null;
        
        
        
        
        
        
        
        
        
        //Employee Payroll Info
        category = new DefaultMutableTreeNode("Employee Payroll Info");
        root.add(category);
        //selectedNode = new DefaultMutableTreeNode(new ChangeEditorPanel("Payslips", new PLREmployeePayslipPanel()));
        category.add(selectedNode);

        
        //detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Employee Cost Centers", new PLREmployeeCostCenterPanel()));
        category.add(detail);
        //Payroll Setup
        category = new DefaultMutableTreeNode("Payroll Setup");
        root.add(category);
        
        
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Pay Points", new PLRPayPointsPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Cost Centers", new PLRCostCenterPanel()));
        category.add(detail);
        
        

        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Period Management", new PLRPeriodMgtPanel()));
        category.add(detail);
        
       
        
        // Journal Entry
        category = new DefaultMutableTreeNode("Journal Entry");
        root.add(category);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Journal Entry Setup", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("JV General Codes", new JPanel()));
        category.add(detail);
        
        // Statutory Rates
        category = new DefaultMutableTreeNode("Statutory rates");
        root.add(category);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("P.A.Y.E Setup", new PLRPAYEPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("N.H.I.F Setup", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Benefits Setup", new JPanel()));
        category.add(detail);
        
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Monthly Local Tax Setup", new PLRLHTPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("N.S.S.F Contribution", new PLRNSSFPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Housing", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Owner Occupied Intrest", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Home Ownership Savings Plan", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("N.H.I.F", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Housing", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Motor Vehicle Benefit", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Premiums", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Defined Contribution", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Low Intrest / Fringe Benefits Tax", new JPanel()));
        category.add(detail);
        
        // Payroll Options
        category = new DefaultMutableTreeNode("Payroll Options");
        root.add(category);
        
        
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Net Pay Rounding", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Pension Deduction", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Net Pay", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("House Rent", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("P.A.Y.E Rounding", new JPanel()));
        category.add(detail);
        
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Posting Transactions", new JPanel()));
        category.add(detail);
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Proration", new JPanel()));
        category.add(detail);
        
        
        detail = new DefaultMutableTreeNode( new ChangeEditorPanel("Match Department to SUN Depts", new JPanel()));
        category.add(detail);
        
                
        
        
        
        
        
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHolder = new javax.swing.JPanel();
        treeHolder = new javax.swing.JPanel();
        jtCompany = new javax.swing.JTextField();
        jtCurrentPeriod = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        panelHolder.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelHolderLayout = new javax.swing.GroupLayout(panelHolder);
        panelHolder.setLayout(panelHolderLayout);
        panelHolderLayout.setHorizontalGroup(
            panelHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );
        panelHolderLayout.setVerticalGroup(
            panelHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        treeHolder.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout treeHolderLayout = new javax.swing.GroupLayout(treeHolder);
        treeHolder.setLayout(treeHolderLayout);
        treeHolderLayout.setHorizontalGroup(
            treeHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );
        treeHolderLayout.setVerticalGroup(
            treeHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jtCompany.setBackground(new java.awt.Color(0, 204, 0));
        jtCompany.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jtCompany.setText(org.openide.util.NbBundle.getMessage(PayrollTopComponent.class, "PayrollTopComponent.jtCompany.text")); // NOI18N

        jtCurrentPeriod.setEditable(false);
        jtCurrentPeriod.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jtCurrentPeriod.setText(org.openide.util.NbBundle.getMessage(PayrollTopComponent.class, "PayrollTopComponent.jtCurrentPeriod.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayrollTopComponent.class, "PayrollTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(treeHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtCurrentPeriod, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtCurrentPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(treeHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jtCompany;
    private javax.swing.JTextField jtCurrentPeriod;
    private javax.swing.JPanel panelHolder;
    private javax.swing.JPanel treeHolder;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        if(node == null){
            //Do nothing 
        }
        
        Object nodeInfo = node.getUserObject();
        if(node.isLeaf()){
            ChangeEditorPanel panel = (ChangeEditorPanel)nodeInfo;
            panelHolder.removeAll();
            panelHolder.add(panel.getPanel());
            panelHolder.setPreferredSize(panel.getPanel().getPreferredSize());
            panelHolder.revalidate();
            
            repaint();
           
        }else{
            panelHolder.removeAll();
            panelHolder.revalidate();
            repaint();
            
        }
  
    }
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
    for(int i=startingIndex;i<rowCount;++i){
        tree.expandRow(i);
    }

    if(tree.getRowCount()!=rowCount){
        expandAllNodes(tree, rowCount, tree.getRowCount());
    }
}

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<CompanyDetails> r = (Lookup.Result<CompanyDetails>)ev.getSource();
        for(CompanyDetails c: r.allInstances()){
            jtCompany.setText(c.getCompanyName());
        }
    }
}
