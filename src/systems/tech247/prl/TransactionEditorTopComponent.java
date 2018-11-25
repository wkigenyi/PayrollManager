/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
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
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Currencies;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.util.CetusUTL;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//TransactionEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TransactionEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.TransactionEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TransactionEditorAction",
        preferredID = "TransactionEditorTopComponent"
)
@Messages({
    "CTL_TransactionEditorAction=TransactionEditor",
    "CTL_TransactionEditorTopComponent=New Transaction",
    "HINT_TransactionEditorTopComponent=This is a TransactionEditor window"
})
public final class TransactionEditorTopComponent extends TopComponent implements LookupListener {
    
    Currencies selectedCurrency = DataAccess.getBaseCurrency();
    TblPayrollCode selectedCode = null;
    BigDecimal amount = BigDecimal.ZERO;
    String message = null;
    List<Employees> empList = new ArrayList<>();
    
    
    
    TopComponent currencyTC = WindowManager.getDefault().findTopComponent("CurrenciesTopComponent");
    TopComponent codeSelect = new PayrollCodesTopComponent("OTHER",false,false);
    TopComponent employeeselectorTC = WindowManager.getDefault().findTopComponent("PersonnelSelectorTopComponent");
    InstanceContent ic = new InstanceContent();
    
    Lookup.Result<PayrollCodeSelectable> txCodeRslt = codeSelect.getLookup().lookupResult(PayrollCodeSelectable.class);
    Lookup.Result<Currencies> currencyRslt = currencyTC.getLookup().lookupResult(Currencies.class);
    public TransactionEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_TransactionEditorTopComponent());
        setToolTipText(Bundle.HINT_TransactionEditorTopComponent());
        //String sql="SELECT e FROM Employees e WHERE e.isDisengaged =0";
        //empList = DataAccess.searchEmployees(sql);
        
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
        
        jtAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setTheAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setTheAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setTheAmount();
            }
        });
        
        jtCurrency.setText(selectedCurrency.getCurrencyCode());
        resultChanged(new LookupEvent(txCodeRslt));
        txCodeRslt.addLookupListener(this);
        resultChanged(new LookupEvent(currencyRslt));
        currencyRslt.addLookupListener(this);
        
        
        jtCurrency.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        jtTransactionCode.addKeyListener(new KeyListener() {
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
                DialogDisplayer.getDefault().notify(new DialogDescriptor(codeSelect, "Select A Transaction Code"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    void setTheAmount(){
        try{
            amount = new BigDecimal(jtAmount.getText());
            modify();
        }catch(Exception ex){
            StatusDisplayer.getDefault().setStatusText(ex.getLocalizedMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtTransactionCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtCurrency = new javax.swing.JTextField();
        jtAmount = new javax.swing.JFormattedTextField();
        jbEmployeeSelector = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtReference = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jLabel1.text")); // NOI18N

        jtTransactionCode.setText(org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jtTransactionCode.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jLabel3.text")); // NOI18N

        jtCurrency.setText(org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jtCurrency.text")); // NOI18N

        jtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtAmount.setText(org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jtAmount.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jbEmployeeSelector, org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jbEmployeeSelector.text")); // NOI18N
        jbEmployeeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEmployeeSelectorActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jLabel4.text")); // NOI18N

        jtReference.setText(org.openide.util.NbBundle.getMessage(TransactionEditorTopComponent.class, "TransactionEditorTopComponent.jtReference.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbEmployeeSelector, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtTransactionCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtCurrency, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtReference, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtAmount, jtCurrency, jtTransactionCode});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbEmployeeSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtTransactionCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(162, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbEmployeeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEmployeeSelectorActionPerformed
        DataAccess.resetSelectedEmployees();
        
        DialogDisplayer.getDefault().notify(new DialogDescriptor(employeeselectorTC, "Select Employee(s)"));
        CetusUTL.loadSelectableEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0",false);
    }//GEN-LAST:event_jbEmployeeSelectorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbEmployeeSelector;
    private javax.swing.JFormattedTextField jtAmount;
    private javax.swing.JTextField jtCurrency;
    private javax.swing.JTextField jtReference;
    private javax.swing.JTextField jtTransactionCode;
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
            if(o instanceof PayrollCodeSelectable){
                
                
                selectedCode = (TblPayrollCode)((PayrollCodeSelectable) o).getCode();
                jtTransactionCode.setText(selectedCode.getPayrollCodeName());
                modify();
            }
        }
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class TransactionSavable extends AbstractSavable{
        
        TransactionSavable(){
            register();
        }
        
        TransactionEditorTopComponent tc(){
            return TransactionEditorTopComponent.this;
        }

        @Override
        protected String findDisplayName() {
            return "Transaction";
        }

        @Override
        protected void handleSave() throws IOException {
            
            Runnable task = new  Runnable() {
                @Override
                public void run() {
                    
                    
                    final ProgressHandle ph = ProgressHandleFactory.createHandle("Sending Transactions");
                    ph.start();
                    for(int i=0;i<empList.size();i++){
                        Employees e = empList.get(i);
                        int j = i+1;
                        DataAccess.saveEmployeeTransaction(e, selectedCode, amount, selectedCurrency, message, new DataAccess().getCurrentPeriod());
                        StatusDisplayer.getDefault().setStatusText("Processing For: "+j+"/"+empList.size()+" "+e.getSurName()+" "+ e.getOtherNames());
                        //ph.progress(i+"/"+empList.size()+" "+e.getSurName()+" "+e.getOtherNames());
                    }
                    ph.finish();
                    UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshEmployeeTransaction()), null);
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            RequestProcessor.getDefault().post(task);
            tc().close();
            
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
        jtAmount.setText("");
        jtTransactionCode.setText("");
        jtReference.setText("");
        message = "";
    }
}
