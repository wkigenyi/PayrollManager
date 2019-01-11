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
import java.util.Arrays;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPayrollCodeGroups;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PayrollCodeEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayrollCodeEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.PayrollCodeEditorTopComponent")
//*@ActionReference(path = "Menu/Window" /*, position = 333 */)
/*@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PayrollCodeEditorAction",
        preferredID = "PayrollCodeEditorTopComponent"
)*/
@Messages({
    "CTL_PayrollCodeEditorAction=PayrollCodeEditor",
    "CTL_PayrollCodeEditorTopComponent=New Payroll Code",
    "HINT_PayrollCodeEditorTopComponent=This is a PayrollCodeEditor window"
})
public final class PayrollCodeEditorTopComponent extends TopComponent implements LookupListener {
    
    //when updating you need to have the updateable in memory
    TblPayrollCode updateable;
    
    //The Variables to fill out
    String codeName = "";
    String codeCode = "";
    int group = 0;
    String reportLabel="";
    String formular = "**No Formular**";
    Boolean taxable =  false;
    Boolean processInPayroll = false;
    Boolean payment = true;
    Double factor = 1.0;
    Boolean deduction = false;
    Boolean displayOnPayslip = false;
    Boolean active = false;
    
    TopComponent codeGrpTc = WindowManager.getDefault().findTopComponent("PCodeGroupsTopComponent");
    Lookup.Result<TblPayrollCodeGroups> codeGroupResult = codeGrpTc.getLookup().lookupResult(TblPayrollCodeGroups.class);
    InstanceContent ic = new InstanceContent();
    public PayrollCodeEditorTopComponent(){
        this(null);
    }

    public PayrollCodeEditorTopComponent(TblPayrollCode code) {
        initComponents();
        setName(Bundle.CTL_PayrollCodeEditorTopComponent());
        setToolTipText(Bundle.HINT_PayrollCodeEditorTopComponent());
        codeGroupResult.addLookupListener(this);
        resultChanged(new LookupEvent(codeGroupResult));
        
        try{
            updateable=DataAccess.entityManager.find(TblPayrollCode.class, code.getPayrollCodeID());
        }catch(NullPointerException ex){
            
        }
        fillTheFields();
        
        jtCodeCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                codeCode= jtCodeCode.getText();
                try{
                    updateable.setPayrollCodeCode(codeCode);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                codeCode= jtCodeCode.getText();
                try{
                    updateable.setPayrollCodeCode(codeCode);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                codeCode= jtCodeCode.getText();
                try{
                    updateable.setPayrollCodeCode(codeCode);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });
        //jtCodeName.getDocument().putProperty("owner", jtCodeName);
        jtCodeName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPayrollCodeName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPayrollCodeName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPayrollCodeName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });
        //jtFormular.getDocument().putProperty("owner", jtFormular);
        jftFactor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try{
                    factor= new Double(jftFactor.getText());
                    try{
                        updateable.setFactor(factor);
                    }catch(NullPointerException ex){
                    
                    }
                    modify();
                }catch(NumberFormatException ex){
                    
                }
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try{
                    factor= new Double(jftFactor.getText());
                    try{
                        updateable.setFactor(factor);
                    }catch(NullPointerException ex){
                    
                    }
                    modify();
                }catch(NumberFormatException ex){
                    
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try{
                    factor= new Double(jftFactor.getText());
                    try{
                        updateable.setFactor(factor);
                    }catch(NullPointerException ex){
                    
                    }
                    modify();
                }catch(NumberFormatException ex){
                    
                }
            }
        });
        
        jtReportLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reportLabel=jtReportLabel.getText();
                try{
                    updateable.setReportLabel(reportLabel);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reportLabel=jtReportLabel.getText();
                try{
                    updateable.setReportLabel(reportLabel);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reportLabel=jtReportLabel.getText();
                try{
                    updateable.setReportLabel(reportLabel);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });
        
        jtCodeGroup.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                TopComponent groupsTc = WindowManager.getDefault().findTopComponent("PCodeGroupsTopComponent");
                DialogDisplayer.getDefault().notify(new DialogDescriptor(groupsTc, "Select A Code Group"));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        jtCodeGroup.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TopComponent groupsTc = WindowManager.getDefault().findTopComponent("PCodeGroupsTopComponent");
                DialogDisplayer.getDefault().notify(new DialogDescriptor(groupsTc, "Select A Code Group"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        
        

    }
    void fillTheFields(){
        if(updateable!=null){
            try{
            setName(updateable.getPayrollCodeName());
            jtCodeName.setText(updateable.getPayrollCodeName());
            
            jtCodeGroup.setText(updateable.getPCodeGroupType());
            
            jtCodeCode.setText(updateable.getPayrollCodeCode());
            
            jftFactor.setValue(updateable.getFactor());
            
            jcbDisplayOnPayslip.setSelected(updateable.getDisplayPostedPayrollCodeInfoOnPaySlip());
            
            jcbPayment.setSelected(updateable.getPayment());
            
            //jrbDeduction.setSelected(updateable.getDeduction());
            
            jcbTable.setSelected(updateable.getTaxable());
            
            jcbProcessInPayment.setSelected(updateable.getProcessInPayroll());
            
            jcbIsActive.setSelected(updateable.getActive()==1);
            
            jtCodeID.setText(updateable.getPayrollCodeID()+"");
            jtReportLabel.setText(updateable.getReportLabel());
                  
            
        }catch(NullPointerException ex){
            //Creating new
        }
            
        }
        
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
        jtCodeName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtCodeGroup = new javax.swing.JTextField();
        jcbTable = new javax.swing.JCheckBox();
        jcbProcessInPayment = new javax.swing.JCheckBox();
        jcbDisplayOnPayslip = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jtCodeCode = new javax.swing.JTextField();
        jcbIsActive = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtCodeID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtReportLabel = new javax.swing.JTextField();
        jftFactor = new javax.swing.JFormattedTextField();
        jcbPayment = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel1.text")); // NOI18N

        jtCodeName.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jtCodeName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel2.text")); // NOI18N

        jtCodeGroup.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jtCodeGroup.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbTable, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jcbTable.text")); // NOI18N
        jcbTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTableActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbProcessInPayment, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jcbProcessInPayment.text")); // NOI18N
        jcbProcessInPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProcessInPaymentActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbDisplayOnPayslip, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jcbDisplayOnPayslip.text")); // NOI18N
        jcbDisplayOnPayslip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDisplayOnPayslipActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel4.text")); // NOI18N

        jtCodeCode.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jtCodeCode.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbIsActive, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jcbIsActive.text")); // NOI18N
        jcbIsActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbIsActiveActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel6.text")); // NOI18N

        jtCodeID.setEditable(false);
        jtCodeID.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jtCodeID.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jLabel7.text")); // NOI18N

        jtReportLabel.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jtReportLabel.text")); // NOI18N

        jftFactor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jftFactor.setText(org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jftFactor.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbPayment, org.openide.util.NbBundle.getMessage(PayrollCodeEditorTopComponent.class, "PayrollCodeEditorTopComponent.jcbPayment.text")); // NOI18N
        jcbPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPaymentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbTable)
                    .addComponent(jtCodeName, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jtCodeGroup, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jtCodeCode)
                    .addComponent(jtCodeID)
                    .addComponent(jtReportLabel)
                    .addComponent(jcbProcessInPayment)
                    .addComponent(jcbDisplayOnPayslip)
                    .addComponent(jcbIsActive)
                    .addComponent(jftFactor)
                    .addComponent(jcbPayment))
                .addContainerGap(110, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtCodeGroup, jtCodeName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtCodeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtCodeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtCodeCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtCodeGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jftFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtReportLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbProcessInPayment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbPayment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDisplayOnPayslip)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbIsActive)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbIsActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbIsActiveActionPerformed
        active = jcbIsActive.isSelected();
        int a;
        if(active){
            a=1;
        }else{
            a=0;
        }    
        try{
            updateable.setActive(a);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbIsActiveActionPerformed

    private void jcbDisplayOnPayslipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDisplayOnPayslipActionPerformed
        
        displayOnPayslip = jcbDisplayOnPayslip.isSelected();
        try{
            updateable.setDisplayPostedPayrollCodeInfoOnPaySlip(displayOnPayslip);
            updateable.setDisplayPostedPayrollCodeValueOnPaySlip(displayOnPayslip);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbDisplayOnPayslipActionPerformed

    private void jcbProcessInPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProcessInPaymentActionPerformed
        processInPayroll = jcbProcessInPayment.isSelected();
        try{
            updateable.setProcessInPayroll(processInPayroll);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbProcessInPaymentActionPerformed

    private void jcbTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTableActionPerformed
        taxable = jcbTable.isSelected();
        try{
            updateable.setTaxable(taxable);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbTableActionPerformed

    private void jcbPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPaymentActionPerformed
        payment = jcbPayment.isSelected();
        deduction = !payment;
        try{
            updateable.setPayment(payment);
            updateable.setDeduction(deduction);
            modify();        
        }catch(NullPointerException ex){
            
        }
    }//GEN-LAST:event_jcbPaymentActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgCodeType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JCheckBox jcbDisplayOnPayslip;
    private javax.swing.JCheckBox jcbIsActive;
    private javax.swing.JCheckBox jcbPayment;
    private javax.swing.JCheckBox jcbProcessInPayment;
    private javax.swing.JCheckBox jcbTable;
    private javax.swing.JFormattedTextField jftFactor;
    private javax.swing.JTextField jtCodeCode;
    private javax.swing.JTextField jtCodeGroup;
    private javax.swing.JTextField jtCodeID;
    private javax.swing.JTextField jtCodeName;
    private javax.swing.JTextField jtReportLabel;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        codeGroupResult.removeLookupListener(this);
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
    public void resultChanged(LookupEvent le) {
        Lookup.Result<TblPayrollCodeGroups> result = (Lookup.Result<TblPayrollCodeGroups>)le.getSource();
        for (TblPayrollCodeGroups group : result.allInstances()){
            jtCodeGroup.setText(group.getPCodeGroupName());
            try{
                updateable.setPCodeGroupID(group);
            }catch(NullPointerException ex){
                
            }
            this.group = group.getPCodeGroupID();
            modify();
        }
    }






    
    


    
    private class PayrollCodeSavable extends AbstractSavable{
        
        PayrollCodeSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            return "Payroll Code";
        }
        
        PayrollCodeEditorTopComponent tc(){
            return PayrollCodeEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            //New Transaction Code
            if(updateable==null){
            String sql = "INSERT INTO [dbo].[tblPayrollCode]\n" +
"           ([Company_ID]\n" +
"           ,[PayrollCode_Code]\n" +
"           ,[PayrollCode_Name]\n" +
"\n" +
"           ,[Taxable]\n" +
"           ,[Payment]\n" +
"           ,[Deduction]\n" +
"\n" +
"           ,[PayrollCode_Formula]\n" +
"           ,[DisplayPostedPayrollCodeValueOnPaySlip]\n" +
"           ,[DisplayPostedPayrollCodeInfoOnPaySlip]\n" +
"           ,[ProcessInPayroll]\n" +
"           \n" +
"           ,[PCodeGroupType]\n" +
"           ,[active]\n" +
"           ,[factor]\n" +                    
"           )\n" +
"     VALUES\n" +
"           (1" +
"           ,'"+codeCode+"'\n" +
"           ,'"+codeName+"'\n" +
"           ,'"+taxable+"'\n" +
"           ,'"+payment+"'\n" +
"           ,'"+deduction+"'\n" +
"           ,'"+formular+"'\n" +
"           ,'"+displayOnPayslip+"'\n" +
"           ,'"+displayOnPayslip+"'\n" +
"           ,'"+processInPayroll+"'\n" +
"           ,'"+group+"'\n" +
"           ,"+1+"\n" +
"           ,"+factor+"\n" +                    
"           )";
            if(
                    !codeCode.equals("") && 
                    (!codeName.equals("") &&
                    (group!=0))){
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.createNativeQuery(sql).executeUpdate();
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPayrollCode()), null);
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPCodeGroup()), null);
                tc().close();
            
            }else{
                System.out.println("Some infor is not  filled in");
            }
        }else{
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPayrollCode()), null);
                tc().close();
            }
            
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof PayrollCodeSavable){
                PayrollCodeSavable e = (PayrollCodeSavable)o;
                return tc() == e.tc();
            }
            return false;        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }
        
    }
    
    public void modify(){
        //new entry
        if(updateable==null){
            if(
                    codeCode.length()>1 && 
                    (codeName.length()>1 &&
                    (group>0))){
               
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
                }
            }
        }else{
            if(updateable.getPayrollCodeCode().length()<=1){
                StatusDisplayer.getDefault().setStatusText("Proper Transaction Code is Required");
            }else if(updateable.getPayrollCodeName().length()<=1){
                StatusDisplayer.getDefault().setStatusText("Proper Code Name is Required");
            }else if(updateable.getPCodeGroupID()==null){
                StatusDisplayer.getDefault().setStatusText("Specify A Code Group");
            }else{
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
            }
        }
    }
        
            
        
    
    }
}
