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
import java.util.Arrays;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
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
import systems.tech247.hr.TblEmployeeTransactions;
import systems.tech247.hr.TblPayrollCode;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//EmployeeTransactionEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EmployeeTransactionEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
//@ActionID(category = "Window", id = "systems.tech247.prl.EmployeeTransactionEditorTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)

/*@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EmployeeTransactionEditorAction",
        preferredID = "EmployeeTransactionEditorTopComponent"
)*/
@Messages({
    "CTL_EmployeeTransactionEditorAction=TransactionEditor",
    "CTL_EmployeeTransactionEditorTopComponent=New Transaction",
    "HINT_EmployeeTransactionEditorTopComponent=This is a TransactionEditor window"
})
public final class EmployeeTransactionEditorTopComponent extends TopComponent implements LookupListener {
    
    Currencies selectedCurrency = DataAccess.getBaseCurrency();
    TblPayrollCode selectedCode = null;
    BigDecimal amount = BigDecimal.ZERO;
    String message = null;
    Employees emp;
    
    
    
    TopComponent currencyTC = WindowManager.getDefault().findTopComponent("CurrenciesTopComponent");
    TopComponent codeSelect = new PayrollCodesTopComponent("OTHER",false,false);
    TopComponent employeeselectorTC = WindowManager.getDefault().findTopComponent("PersonnelSelectorTopComponent");
    InstanceContent ic = new InstanceContent();
    TblEmployeeTransactions updateable;
    
    Lookup.Result<PayrollCodeSelectable> txCodeRslt = codeSelect.getLookup().lookupResult(PayrollCodeSelectable.class);
    Lookup.Result<Currencies> currencyRslt = currencyTC.getLookup().lookupResult(Currencies.class);
    public EmployeeTransactionEditorTopComponent(){
        
    }
    
    public EmployeeTransactionEditorTopComponent(Employees emp){
        this(emp, null);
    }
    
    public EmployeeTransactionEditorTopComponent(Employees emp, TblEmployeeTransactions tx) {
        initComponents();
        setName(Bundle.CTL_TransactionEditorTopComponent());
        setToolTipText(Bundle.HINT_TransactionEditorTopComponent());
        //String sql="SELECT e FROM Employees e WHERE e.isDisengaged =0";
        //empList = DataAccess.searchEmployees(sql);
        
        this.emp= emp;
        try{
            updateable = DataAccess.entityManager.find(TblEmployeeTransactions.class, tx.getId());
            this.emp = updateable.getEmployeeID();
        }catch(NullPointerException ex){
            
        }
        
        
        
        jtEmployeeName.setText(this.emp.getSurName()+" "+this.emp.getOtherNames());
        
        fillFields();
        

        
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
        
        
        jtCurrency.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency"));
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
        jtTransactionCode.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(codeSelect, "Select A Transaction Code"));
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
    
    void setTheAmount(){
        
        try{
            amount = new BigDecimal(jtAmount.getText());
            try{
                updateable.setAmount(amount);
            }catch(NullPointerException ex){
                
            }
            modify();
        }catch(Exception ex){
            
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
        jLabel5 = new javax.swing.JLabel();
        jtEmployeeName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jdcTxDate = new com.toedter.calendar.JDateChooser();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jLabel1.text")); // NOI18N

        jtTransactionCode.setText(org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jtTransactionCode.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jLabel3.text")); // NOI18N

        jtCurrency.setText(org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jtCurrency.text")); // NOI18N

        jtAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtAmount.setText(org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jtAmount.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jLabel5.text")); // NOI18N

        jtEmployeeName.setEditable(false);
        jtEmployeeName.setText(org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jtEmployeeName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(EmployeeTransactionEditorTopComponent.class, "EmployeeTransactionEditorTopComponent.jLabel6.text")); // NOI18N

        jdcTxDate.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtTransactionCode, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jtCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jtEmployeeName)
                    .addComponent(jdcTxDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtAmount, jtCurrency, jtTransactionCode});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jdcTxDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(165, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private com.toedter.calendar.JDateChooser jdcTxDate;
    private javax.swing.JFormattedTextField jtAmount;
    private javax.swing.JTextField jtCurrency;
    private javax.swing.JTextField jtEmployeeName;
    private javax.swing.JTextField jtTransactionCode;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        txCodeRslt.addLookupListener(this);
        currencyRslt.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        txCodeRslt.removeLookupListener(this);
        currencyRslt.removeLookupListener(this);
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
                TblPayrollCode txCode = ((PayrollCodeSelectable) o).getCode();
                jtTransactionCode.setText(txCode.getPayrollCodeName());
                selectedCode = txCode;
                try{
                updateable.setPayrollCodeID(selectedCode);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }else if(o instanceof Currencies){
                selectedCurrency = (Currencies)o;
                try{
                updateable.setCurrencyID(selectedCurrency);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        }
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class TransactionSavable extends AbstractSavable{
        
        TransactionSavable(){
            register();
        }
        
        EmployeeTransactionEditorTopComponent tc(){
            return EmployeeTransactionEditorTopComponent.this;
        }

        @Override
        protected String findDisplayName() {
            return "Transaction";
        }

        @Override
        protected void handleSave() throws IOException {
            if(updateable==null){//New Transaction
                DataAccess.saveEmployeeTransaction(emp, selectedCode, amount, selectedCurrency, message, new DataAccess().getCurrentPeriod());
            }else{//Existing Transaction
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.getTransaction().commit();
            }    
        UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshEmployeeTransaction()), null);
        tc().close();
                        //ph.progress(i+"/"+empList.size()+" "+e.getSurName()+" "+e.getOtherNames());
                    
            
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
        if(updateable == null){
            if( amount!=BigDecimal.ZERO && selectedCode!=null){
                if(getLookup().lookup(TransactionSavable.class)==null){
                    ic.add(new TransactionSavable());
                }
            }else if(amount==BigDecimal.ZERO){
                StatusDisplayer.getDefault().setStatusText("Amount is 0.0");
            }else if(selectedCode==null){
                StatusDisplayer.getDefault().setStatusText("Select A Transaction Code");
            }
        }else{
            if(getLookup().lookup(TransactionSavable.class)==null){
                ic.add(new TransactionSavable());
            }
            
        }
        
    } 
    
    public void fillFields(){
        if(updateable!=null){
            setName("Edit Transaction");
            jtAmount.setValue(updateable.getAmount());
            jtCurrency.setText(updateable.getCurrencyID().getCurrencyName());
            jdcTxDate.setDate(updateable.getDateTransaction());
            jtTransactionCode.setText(updateable.getPayrollCodeID().getPayrollCodeName());
        }
        
    }
}
