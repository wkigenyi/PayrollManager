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
import systems.tech247.hr.Currencies;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblEmployeePayrollCode;
import systems.tech247.hr.TblPayrollCode;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PayPointEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayPointEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.PayPointEditorTopComponent")
//*@ActionReference(path = "Menu/Window" /*, position = 333 */)
/*@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PayrollCodeEditorAction",
        preferredID = "PayrollCodeEditorTopComponent"
)*/
@Messages({
    "CTL_PayPointEditorAction=Pay Point Editor",
    "CTL_PayPointEditorTopComponent=Pay Point Editor",
    "HINT_PayPointEditorTopComponent="
})
public final class PayPointEditorTopComponent extends TopComponent implements LookupListener {
    
    //when updating you need to have the updateable in memory
    TblEmployeePayrollCode updateable;
    
    //The Variables to fill out
    String codeName = "";
    String codeCode = "";
    String group = "";
    Currencies curr;
    TblPayrollCode code;
    Employees emp;
    
    Double amount = 0.0;
    Boolean taxable =  false;
    Boolean processInPayroll = false;
    Boolean payment = true;
    Boolean deduction = false;
    Boolean displayOnPayslip = false;
    Boolean active = false;
    
    TopComponent currencyTC = WindowManager.getDefault().findTopComponent("CurrenciesTopComponent");
    TopComponent codeSelect = new PayrollCodesTopComponent("OTHER",false,false);
    Lookup.Result currencyResult = currencyTC.getLookup().lookupResult(Currencies.class);
    Lookup.Result codeResult = codeSelect.getLookup().lookupResult(TblPayrollCode.class);
    
    InstanceContent ic = new InstanceContent();
    public PayPointEditorTopComponent(){
        this(null);
    }
    
    public PayPointEditorTopComponent(Employees e){
        this(e,null);
    }

    public PayPointEditorTopComponent(Employees emp,TblEmployeePayrollCode code) {
        initComponents();
        setName(Bundle.CTL_PayPointEditorTopComponent());
        setToolTipText(Bundle.HINT_PayPointEditorTopComponent());
        this.emp = emp;
        resultChanged(new LookupEvent(codeResult));
        codeResult.addLookupListener(this);
        resultChanged(new LookupEvent(currencyResult));
        currencyResult.addLookupListener(this);
        try{
            updateable = DataAccess.entityManager.find(TblEmployeePayrollCode.class, code.getEmployeeCodeID());
        }catch(NullPointerException ex){
            
        }        
        fillTheFields();
       
        //jtFormular.getDocument().putProperty("owner", jtFormular);
        jftAmount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String amt= jftAmount.getText();
                try{
                    amount = new Double(amt);
                    updateable.setAmount(amount);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String amt= jftAmount.getText();
                try{
                    amount = new Double(amt);
                    updateable.setAmount(amount);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String amt= jftAmount.getText();
                try{
                    amount = new Double(amt);
                    updateable.setAmount(amount);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });
        
        try{
            updateable=DataAccess.entityManager.find(TblEmployeePayrollCode.class, code.getEmployeeCodeID());
        }catch(NullPointerException ex){
            
        }
        
        
        
        
        
        jtCodeName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                DialogDisplayer.getDefault().notify(new DialogDescriptor(codeSelect, "Select A Payroll Code"));
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
        
        jtCurrency.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency Code"));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency Code"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency Code"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        jtCurrency.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTC, "Select A Currency Code"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            setName(updateable.getPayrollCodeID().getPayrollCodeName()+"->"+updateable.getEmployeeID().getSurName()+" "+updateable.getEmployeeID().getOtherNames());
            jtCodeName.setText(updateable.getPayrollCodeID().getPayrollCodeName());
            
            jtCurrency.setText(updateable.getCurrencyID().getCurrencyName());
            
            jftAmount.setValue(updateable.getAmount());
            
            jcbIsActive.setSelected(updateable.getActive());
            
            
                  
            
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
        jcbIsActive = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jftAmount = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jftAmount1 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jtCurrency = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jLabel1.text")); // NOI18N

        jtCodeName.setText(org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jtCodeName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbIsActive, org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jcbIsActive.text")); // NOI18N
        jcbIsActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbIsActiveActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jLabel5.text")); // NOI18N

        jftAmount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftAmount.setText(org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jftAmount.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jLabel6.text")); // NOI18N

        jftAmount1.setEditable(false);
        jftAmount1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftAmount1.setText(org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jftAmount1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jLabel7.text")); // NOI18N

        jtCurrency.setText(org.openide.util.NbBundle.getMessage(PayPointEditorTopComponent.class, "PayPointEditorTopComponent.jtCurrency.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbIsActive)
                    .addComponent(jtCodeName, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jftAmount)
                    .addComponent(jftAmount1)
                    .addComponent(jtCurrency))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtCodeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jftAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jftAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbIsActive)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbIsActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbIsActiveActionPerformed
        active = jcbIsActive.isSelected();
        
            
        try{
            updateable.setActive(active);
        }catch(NullPointerException ex){
            
        }
        modify();
    }//GEN-LAST:event_jcbIsActiveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgCodeType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JCheckBox jcbIsActive;
    private javax.swing.JFormattedTextField jftAmount;
    private javax.swing.JFormattedTextField jftAmount1;
    private javax.swing.JTextField jtCodeName;
    private javax.swing.JTextField jtCurrency;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        codeResult.addLookupListener(this);
        currencyResult.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        codeResult.removeLookupListener(this);
        currencyResult.removeLookupListener(this);
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
        Lookup.Result result = (Lookup.Result)le.getSource();
        for (Object o : result.allInstances()){
            if(o instanceof Currencies){
                curr = (Currencies)o;
                try{
                    jtCurrency.setText(curr.getCurrencySymbol());
                updateable.setCurrencyID(curr);
                
                }catch(Exception ex){
                    
                }
                modify();
            }else
            
            if(o instanceof TblPayrollCode){
                code = (TblPayrollCode)o;
                try{
                    jtCodeName.setText(code.getPayrollCodeName());
                    updateable.setPayrollCodeID(code);
                    
                }catch(Exception ex){
                   
                }
                modify();
            }
            
        }
    }






    
    


    
    private class PayrollCodeSavable extends AbstractSavable{
        
        PayrollCodeSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            return "Employee Payroll Code";
        }
        
        PayPointEditorTopComponent tc(){
            return PayPointEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            //New Transaction Code
            if(updateable==null){
            String sql = "INSERT INTO [dbo].[tblEmployeePayrollCode]\n" +
"           ([Currency_ID]\n" +
"           ,[Employee_ID]\n" +
"           ,[EmpCode]\n" +
"           ,[PayrollCode_ID]\n" +
"           ,[Amount]\n" +
"           ,[OBalance]\n" +
"           ,[Status]\n" +
"           ,[Active])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?,?)";
            
            if(
                    curr!=null && 
                    (code!=null)){
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.createNativeQuery(sql)
                        .setParameter(1, curr.getCurrencyID())
                        .setParameter(2, emp.getEmployeeID())
                        .setParameter(4, code.getPayrollCodeID())
                        .setParameter(5, amount)
                        .setParameter(8, active)
                        .executeUpdate();
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshEmployeePayrollCode()), null);
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
            if(curr==null){
                StatusDisplayer.getDefault().setStatusText("Specify The Currency");
            }else if(code==null){
                StatusDisplayer.getDefault().setStatusText("Specify The Transaction Code");
            }else{
               
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
                }
            }
        }else{
            if(updateable.getPayrollCodeID()==null){
                StatusDisplayer.getDefault().setStatusText("Proper Transaction Code is Required");
            }else{
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
            }
        }
    }
        
            
        
    
    }
    
    void fillFields(){
        if(updateable != null){
            jtCodeName.setText(updateable.getPayrollCodeID().getPayrollCodeName());
            jtCurrency.setText(updateable.getCurrencyID().getCurrencyCode());
            jftAmount.setValue(updateable.getAmount());
            jcbIsActive.setSelected(updateable.getActive());
            
        }
    }
}
