/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PayrollCodes//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayrollCodesTopComponent",
        iconBase = "systems/tech247/util/icons/settings.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Payroll", id = "systems.tech247.prl.PayrollCodesComponent")
//@ActionReference(path = "Menu/Payroll" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_PayrollCodesAction",
//        preferredID = "PayrollCodesTopComponent"
//)
@Messages({
    "CTL_PayrollCodesAction=Payroll Codes",
    "CTL_PayrollCodesTopComponent=Payroll Codes",
    "HINT_PayrollCodesTopComponent= "
})
public final class PayrollCodesTopComponent extends TopComponent implements ExplorerManager.Provider {
    
    ExplorerManager em = new ExplorerManager();
    QueryPayrollCodes query = new QueryPayrollCodes();
   
    String searchString;
    String sqlString;
    Boolean edit;
    Boolean select;

    public PayrollCodesTopComponent() {
        this("",false,false);
        
    }
    
    
    
   
    public PayrollCodesTopComponent(final String type,Boolean edit,Boolean select) {
        initComponents();
        setName(Bundle.CTL_PayrollCodesTopComponent());
        setToolTipText(Bundle.HINT_PayrollCodesTopComponent());
        OutlineView ov = new OutlineView("Payroll Code Name");
        ov.getOutline().setRootVisible(false);
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(ov);
        this.edit = edit;
        this.select = select;
        if(edit){
            ov.addPropertyColumn("id", "ID");
            ov.addPropertyColumn("code", "CODE");
            ov.addPropertyColumn("group", "Code Group");
            ov.addPropertyColumn("sops", "Show ON PS");
            ov.addPropertyColumn("active", "Process");
            //ov.addPropertyColumn("isSelected", "Select");
        }else if(select){
            ov.addPropertyColumn("group", "Code Group");
            ov.addPropertyColumn("isSelected", "Select");
        }else{
            ov.addPropertyColumn("id", "ID");
            ov.addPropertyColumn("group", "Code Group");
        }
        if(type==""){
            sqlString = "SELECT * FROM TblPayrollCode";
        }else{
            sqlString = "SELECT * FROM TblPayrollCode  WHERE t.pCodeGroupType='"+type+"'";
        }
        
        //query.setSqlString(sqlString);
        //UtilityPLR.loadPayrollCodes(query,false);
             
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        
        search.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                searchString = search.getText();
                if(type==""){
                    sqlString = "SELECT * FROM TblPayrollCode t WHERE t.payrollCode_Name LIKE '%"+searchString+"%'";
                }else{
                    sqlString = "SELECT * FROM TblPayrollCode t WHERE t.payrollCode_Name LIKE '%"+searchString+"%' AND t.pCodeGroupType='"+type+"'";
                }
                
                query.setSqlString(sqlString);
                em.setRootContext(new AbstractNode(Children.create(new FactoryPayrollCodes(query,true), true)));
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        sqlString = "SELECT * FROM TblPayrollCode";
        query.setSqlString(sqlString);
        em.setRootContext(new AbstractNode(Children.create(new FactoryPayrollCodes(query,true), true)));
        
        
      

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPanel = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        viewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        search.setBackground(new java.awt.Color(0, 204, 0));
        search.setText(org.openide.util.NbBundle.getMessage(PayrollCodesTopComponent.class, "PayrollCodesTopComponent.search.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayrollCodesTopComponent.class, "PayrollCodesTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField search;
    private javax.swing.JPanel viewPanel;
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
    public ExplorerManager getExplorerManager() {
        return em;
    }

   
    
    
    
    
  }
