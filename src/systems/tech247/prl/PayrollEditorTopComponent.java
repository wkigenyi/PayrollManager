/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayroll;
import systems.tech247.util.NotifyUtil;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PayrollEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayrollEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.PayrollEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PayrollEditorAction",
        preferredID = "PayrollEditorTopComponent"
)
@Messages({
    "CTL_PayrollEditorAction=Payroll Editor",
    "CTL_PayrollEditorTopComponent=New Payroll",
    "HINT_PayrollEditorTopComponent=This is a PayrollEditor window"
})
public final class PayrollEditorTopComponent extends TopComponent {
    
    
    EntityManager em = DataAccess.entityManager;
    //The Variables to fill out
    String pName = "";
    String pCode = "";
    Date date;
    
    String criteria = "**No Criteria**";
    BigDecimal rounding;
    Boolean isCasual = false;
    Boolean suspendTransactions = true;
    TblPayroll updateable;
    
    
    
    
    InstanceContent ic = new InstanceContent();
    public PayrollEditorTopComponent(){
        this(null);
    }

    public PayrollEditorTopComponent(TblPayroll code) {
        initComponents();
        setName(Bundle.CTL_PayrollEditorTopComponent());
        setToolTipText(Bundle.HINT_PayrollEditorTopComponent());
        try{
            updateable = em.find(TblPayroll.class,code.getPayrollID());
        }catch(NullPointerException ex){
            
        }
        jtPayrollCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!"".equals(jtPayrollCode.getText())){
                    pCode = jtPayrollCode.getText();
                    try{
                        updateable.setPayrollCode(pCode);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                                if(!"".equals(jtPayrollCode.getText())){
                    pCode = jtPayrollCode.getText();
                    try{
                        updateable.setPayrollCode(pCode);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                                if(!"".equals(jtPayrollCode.getText())){
                    pCode = jtPayrollCode.getText();
                    try{
                        updateable.setPayrollCode(pCode);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }
        });
        
        jtPayrollName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!jtPayrollName.getText().equals("")){
                    pName = jtPayrollName.getText();
                    try{
                        updateable.setPayrollName(pName);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(!jtPayrollName.getText().equals("")){
                    pName = jtPayrollName.getText();
                    try{
                        updateable.setPayrollName(pName);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!jtPayrollName.getText().equals("")){
                    pName = jtPayrollName.getText();
                    try{
                        updateable.setPayrollName(pName);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }
            }
        });
        
        jftRounding.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try{
                    rounding = new BigDecimal(jftRounding.getText());
                    try{
                        updateable.setRounding(rounding);
                    }catch(Exception ex){
                        
                    }
                }catch(Exception ex){
                    
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try{
                    rounding = new BigDecimal(jftRounding.getText());
                    try{
                        updateable.setRounding(rounding);
                    }catch(Exception ex){
                        
                    }
                }catch(Exception ex){
                    
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try{
                    rounding = new BigDecimal(jftRounding.getText());
                    try{
                        updateable.setRounding(rounding);
                    }catch(Exception ex){
                        
                    }
                }catch(Exception ex){
                    
                }
            }
        });
        
        //jtCodeName.getDocument().putProperty("owner", jtCodeName);
        
        //jtFormular.getDocument().putProperty("owner", jtFormular);
       
        
        
            
        
        
        
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgCodeType = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jtPayrollName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jcbCasual = new javax.swing.JCheckBox();
        jcbSuspendTransactions = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jtPayrollCode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtCriteria = new javax.swing.JTextField();
        jdcDateEntered = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jftRounding = new javax.swing.JFormattedTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jLabel1.text")); // NOI18N

        jtPayrollName.setText(org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jtPayrollName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbCasual, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jcbCasual.text")); // NOI18N
        jcbCasual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCasualActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbSuspendTransactions, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jcbSuspendTransactions.text")); // NOI18N
        jcbSuspendTransactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSuspendTransactionsActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jLabel4.text")); // NOI18N

        jtPayrollCode.setText(org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jtPayrollCode.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jLabel5.text")); // NOI18N

        jtCriteria.setText(org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jtCriteria.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jLabel6.text")); // NOI18N

        jftRounding.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jftRounding.setText(org.openide.util.NbBundle.getMessage(PayrollEditorTopComponent.class, "PayrollEditorTopComponent.jftRounding.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbCasual)
                    .addComponent(jtPayrollName, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jtPayrollCode, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jtCriteria, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jcbSuspendTransactions)
                    .addComponent(jdcDateEntered, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jftRounding))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtPayrollName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtPayrollCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jdcDateEntered, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jftRounding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCasual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSuspendTransactions)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbSuspendTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSuspendTransactionsActionPerformed
        suspendTransactions = jcbSuspendTransactions.isSelected();
        try{
            updateable.setSuspendTransactionPosting(suspendTransactions);
        }catch(NullPointerException ex){
            
        }
    }//GEN-LAST:event_jcbSuspendTransactionsActionPerformed

    private void jcbCasualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCasualActionPerformed
        isCasual = jcbCasual.isSelected();
        try{
            updateable.setIsCasual(isCasual);
        }catch(NullPointerException ex){
            
        }
    }//GEN-LAST:event_jcbCasualActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgCodeType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JCheckBox jcbCasual;
    private javax.swing.JCheckBox jcbSuspendTransactions;
    private com.toedter.calendar.JDateChooser jdcDateEntered;
    private javax.swing.JFormattedTextField jftRounding;
    private javax.swing.JTextField jtCriteria;
    private javax.swing.JTextField jtPayrollCode;
    private javax.swing.JTextField jtPayrollName;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        fillFields(updateable);
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
    

    






    
    


    
    private class PayrollSavable extends AbstractSavable{
        
        PayrollSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            return "Payroll";
        }
        
        PayrollEditorTopComponent tc(){
            return PayrollEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            
            if(updateable==null){
                //New Transaction Code
            String sql = "INSERT INTO [dbo].[tblPayroll]\n" +
"           ([Payroll_Code]\n" +
"           ,[Payroll_Name]\n" +
"           ,[Company_ID]\n" +
"           ,[Date_Entered]\n" +
"           ,[Rounding]\n" +
"           ,[IsCasual]\n" +
"           ,[TaxCalculationCriteria]\n" +
"           ,[SuspendTransactionPosting])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?,?)";
            
            
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, pCode);
            query.setParameter(2, pName);
            query.setParameter(3, 1);
            query.setParameter(4, new Date());
            query.setParameter(5, rounding);
            query.setParameter(6, isCasual);
            query.setParameter(7, criteria);
            query.setParameter(8, suspendTransactions);
            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();
            }else{
                //Existing Payroll
                em.getTransaction().begin();
                em.getTransaction().commit();
                
            }
            
            UtilityPLR.loadPayrollSetup();
            tc().close();
            
            
            
            
            
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof PayrollSavable){
                PayrollSavable e = (PayrollSavable)o;
                return tc() == e.tc();
            }
            return false;        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }
        
    }
    
    public void modify(){
        
        
        if(pCode.length()>1 && pName.length()>1) 
             {
               
                if(getLookup().lookup(PayrollSavable.class)==null){
                    ic.add(new PayrollSavable());
                }
            }else{
                if(pCode.length()<=1){
                    StatusDisplayer.getDefault().setStatusText("Payroll Code is Not Proper, Rectify the Payroll Code");
                }else if(pName.length()<=1){
                    StatusDisplayer.getDefault().setStatusText("Payroll Name is Not Proper, Rectify the Payroll Name");
                }
            }
        
            
        
    }
    
    void fillFields(TblPayroll code){
        if(code!=null){
            jtPayrollName.setText(code.getPayrollName());
            jtPayrollCode.setText(code.getPayrollCode());
            jdcDateEntered.setDate(code.getDateEntered());
            jcbCasual.setSelected(code.getIsCasual());
            jcbSuspendTransactions.setSelected(code.getSuspendTransactionPosting());
        }
    }
}
