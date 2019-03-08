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
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Currencies;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.CetusUTL;
import systems.tech247.util.NotifyUtil;
import systems.tech247.view.CategoriesTopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//Emailer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EmailerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.EmailerTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_EmailerAction",
//        preferredID = "EmailerTopComponent"
//)
@Messages({
    "CTL_EmailerAction=Emailer",
    "CTL_EmailerTopComponent=Emailer",
    "HINT_EmailerTopComponent=Emailer"
})
public final class EmailerTopComponent extends TopComponent implements LookupListener {
    
   
    TblPayrollCode selectedCode = null;
    BigDecimal amount = BigDecimal.ZERO;
    String message = null;
    List<Employees> empList = new ArrayList<>();
    TblPeriods period = DataAccess.getCurrentMonth();
    
    
    
    TopComponent periodsTC = WindowManager.getDefault().findTopComponent("PeriodsTopComponent");
    TopComponent codeSelect = new PayrollCodesTopComponent("OTHER",false,false);
    TopComponent employeeselectorTC = WindowManager.getDefault().findTopComponent("PersonnelSelectorTopComponent");
    InstanceContent ic = new InstanceContent();
    
    Lookup.Result<PayrollCodeSelectable> txCodeRslt = codeSelect.getLookup().lookupResult(PayrollCodeSelectable.class);
    Lookup.Result<TblPeriods> periodRslt = periodsTC.getLookup().lookupResult(TblPeriods.class);
    public EmailerTopComponent() {
        initComponents();
        setName(Bundle.CTL_EmailerTopComponent());
        setToolTipText(Bundle.HINT_EmailerTopComponent());
        //String sql="SELECT e FROM Employees e WHERE e.isDisengaged =0";
        //empList = DataAccess.searchEmployees(sql);
        jtPeriod.setText(period.getPeriodMonth()+" "+period.getPeriodYear());
        jtReference.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                message = jtReference.getText();
                empList = DataAccess.getSelectedEmployees();
                modify();
            }
        });
        
        
        
        
        resultChanged(new LookupEvent(txCodeRslt));
        txCodeRslt.addLookupListener(this);
        resultChanged(new LookupEvent(periodRslt));
        periodRslt.addLookupListener(this);
        
        
            jtPeriod.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(periodsTC, "Select A Period"));
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
            
            jtPeriod.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(periodsTC, "Select A Period"));
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
        
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jtPeriod = new javax.swing.JTextField();
        jbEmployeeSelector = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtReference = new javax.swing.JTextField();
        jbCategorySelector = new javax.swing.JButton();
        jbSendEmails = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jLabel3.text")); // NOI18N

        jtPeriod.setText(org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jtPeriod.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jbEmployeeSelector, org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jbEmployeeSelector.text")); // NOI18N
        jbEmployeeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEmployeeSelectorActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jLabel4.text")); // NOI18N

        jtReference.setText(org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jtReference.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jbCategorySelector, org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jbCategorySelector.text")); // NOI18N
        jbCategorySelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCategorySelectorActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jbSendEmails, org.openide.util.NbBundle.getMessage(EmailerTopComponent.class, "EmailerTopComponent.jbSendEmails.text")); // NOI18N
        jbSendEmails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSendEmailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtPeriod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtReference, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbEmployeeSelector, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbCategorySelector, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                        .addComponent(jbSendEmails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbEmployeeSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCategorySelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSendEmails)
                .addContainerGap(156, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbEmployeeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEmployeeSelectorActionPerformed
        DataAccess.resetSelectedEmployees();
        
        DialogDisplayer.getDefault().notify(new DialogDescriptor(employeeselectorTC, "Select Employee(s)"));
        CetusUTL.loadSelectableEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0",false);
    }//GEN-LAST:event_jbEmployeeSelectorActionPerformed

    private void jbCategorySelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCategorySelectorActionPerformed
        DataAccess.resetSelectedEmployees();
        
        DialogDisplayer.getDefault().notify(new DialogDescriptor(new CategoriesTopComponent("multi"), "Select Categories"));
        CetusUTL.loadSelectableEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0",false);
    }//GEN-LAST:event_jbCategorySelectorActionPerformed

    private void jbSendEmailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSendEmailsActionPerformed
        empList = DataAccess.getSelectedEmployees();
        try{
        UtilityPLR.emailPayslip(empList, period);
        }catch(IOException | MessagingException ex){
            NotifyUtil.error("Something went wrong", "Something went wrong", ex, false);
        }    
    }//GEN-LAST:event_jbSendEmailsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbCategorySelector;
    private javax.swing.JButton jbEmployeeSelector;
    private javax.swing.JButton jbSendEmails;
    private javax.swing.JTextField jtPeriod;
    private javax.swing.JTextField jtReference;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        
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
        Lookup.Result r = (Lookup.Result)le.getSource();
        for(Object o : r.allInstances()){
            if(o instanceof TblPeriods){
                
                
                period = (TblPeriods)o;
                jtPeriod.setText(period.getPeriodMonth()+" "+period.getPeriodYear());
                //modify();
            }
        }
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class TransactionSavable extends AbstractSavable{
        
        TransactionSavable(){
            register();
        }
        
        EmailerTopComponent tc(){
            return EmailerTopComponent.this;
        }

        @Override
        protected String findDisplayName() {
            return "Transaction";
        }

        @Override
        protected void handleSave() throws IOException {
            

            
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof TransactionSavable){
                TransactionSavable e = (TransactionSavable)o;
                return tc() == e.tc();
            }
            return false;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int hashCode() {
            return tc().hashCode();
        }
        
    }
    
    public void modify(){
        if(!empList.isEmpty() && amount!=BigDecimal.ZERO && selectedCode!=null){
            if(getLookup().lookup(TransactionSavable.class)==null){
                ic.add(new TransactionSavable());
            }
        }else if(amount==BigDecimal.ZERO){
            StatusDisplayer.getDefault().setStatusText("Amount is 0.0");
        }else if(selectedCode==null){
            StatusDisplayer.getDefault().setStatusText("Select A Transaction Code");
        }
    } 
    
    public void resetFields(){
        empList.removeAll(empList);
        amount = BigDecimal.ZERO;
        selectedCode = null;
        jtReference.setText("");
        message = "";
    }
}
