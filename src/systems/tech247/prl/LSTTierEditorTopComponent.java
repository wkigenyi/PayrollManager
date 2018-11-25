/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PrlMonthlyLocalTaxTableTiers;
import systems.tech247.util.NotifyUtil;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//LSTTierEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LSTTierEditorTopComponent",
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
    "CTL_LSTTierEditorAction=LST Editor",
    "CTL_LSTTierEditorTopComponent=LST Editor",
    "HINT_LSTTierEditorTopComponent="
})
public final class LSTTierEditorTopComponent extends TopComponent {
    
    
    BigDecimal upper = BigDecimal.ZERO;
    BigDecimal lower = BigDecimal.ZERO;
    BigDecimal rate  = BigDecimal.ZERO; 
    int newLevel = 0;
    int year = 0;
    int id =0;
    
    
    
    
    
    
    
    
    
    InstanceContent ic = new InstanceContent();
    PrlMonthlyLocalTaxTableTiers updateable;
    
    public LSTTierEditorTopComponent(){
        this(null);
        
    }
    

    
    public LSTTierEditorTopComponent(PrlMonthlyLocalTaxTableTiers tx) {
        initComponents();
        setName(Bundle.CTL_LSTTierEditorTopComponent());
        setToolTipText(Bundle.HINT_LSTTierEditorTopComponent());
        //String sql="SELECT e FROM Employees e WHERE e.isDisengaged =0";
        //empList = DataAccess.searchEmployees(sql);
        
        
        try{
            updateable = DataAccess.entityManager.find(PrlMonthlyLocalTaxTableTiers.class, tx.getMonthlyLocalTaxTableTiersID());
            
        }catch(NullPointerException ex){
            //We are dealing with a new a new entry
            
        }
        
        
        
        
        
        fillFields();
        

        
        jtLower.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String low = jtLower.getText();
                try{
                    lower = new BigDecimal(low);
                    try{
                        updateable.setLowerBoundary(lower);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String low = jtLower.getText();
                try{
                    lower = new BigDecimal(low);
                    try{
                        updateable.setLowerBoundary(lower);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String low = jtLower.getText();
                try{
                    lower = new BigDecimal(low);
                    try{
                        updateable.setLowerBoundary(lower);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                   // NotifyUtil.error("Problem", "Problem", ex, false);
                }
                
            }
        });
        
        jtUpper.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String upperS = jtUpper.getText();
                try{
                    upper = new BigDecimal(upperS);
                    try{
                        updateable.setUpperBoundary(upper);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String upperS = jtUpper.getText();
                try{
                    upper = new BigDecimal(upperS);
                    try{
                        updateable.setUpperBoundary(upper);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String upperS = jtUpper.getText();
                try{
                    upper = new BigDecimal(upperS);
                    try{
                        updateable.setUpperBoundary(upper);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }
        });
        jtRate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String low = jtRate.getText();
                try{
                    rate = new BigDecimal(low);
                    try{
                        updateable.setTierRate(rate);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String low = jtRate.getText();
                try{
                    rate = new BigDecimal(low);
                    try{
                        updateable.setTierRate(rate);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String low = jtRate.getText();
                try{
                    rate = new BigDecimal(low);
                    try{
                        updateable.setTierRate(rate);
                    }catch(NullPointerException ex){
                        
                    }
                    modify();
                }catch(Exception ex){
                    //NotifyUtil.error("Problem", "Problem", ex, false);
                }
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

        jLabel1 = new javax.swing.JLabel();
        jtLevel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtLower = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jtYear = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtUpper = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jtRate = new javax.swing.JFormattedTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jLabel1.text")); // NOI18N

        jtLevel.setEditable(false);
        jtLevel.setText(org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jtLevel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jLabel2.text")); // NOI18N

        jtLower.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jtLower.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtLower.setText(org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jtLower.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jLabel5.text")); // NOI18N

        jtYear.setEditable(false);
        jtYear.setText(org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jtYear.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jLabel4.text")); // NOI18N

        jtUpper.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jtUpper.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtUpper.setText(org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jtUpper.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jLabel7.text")); // NOI18N

        jtRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jtRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtRate.setText(org.openide.util.NbBundle.getMessage(LSTTierEditorTopComponent.class, "LSTTierEditorTopComponent.jtRate.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jtLower, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jtYear)
                    .addComponent(jtUpper)
                    .addComponent(jtRate))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtLevel, jtLower});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtLower, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtUpper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(165, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jtLevel;
    private javax.swing.JFormattedTextField jtLower;
    private javax.swing.JFormattedTextField jtRate;
    private javax.swing.JFormattedTextField jtUpper;
    private javax.swing.JTextField jtYear;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
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


    
    private class TransactionSavable extends AbstractSavable{
        
        TransactionSavable(){
            register();
        }
        
        LSTTierEditorTopComponent tc(){
            return LSTTierEditorTopComponent.this;
        }

        @Override
        protected String findDisplayName() {
            return "LST Tier";
        }

        @Override
        protected void handleSave() throws IOException {
            if(updateable==null){//New Tier
                String sql = "INSERT INTO [dbo].[prlMonthlyLocalTaxTableTiers]\n" +
"           ([MonthlyLocalTaxTableID]\n" +
"           ,[TierLevel]\n" +
"           ,[LowerBoundary]\n" +
"           ,[TierRate]\n" +
"           ,[UpperBoundary])\n" +
"     VALUES\n" +
"           (?,?,?,?,?)";
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.createNativeQuery(sql)
                        .setParameter(1, id)
                        .setParameter(2, newLevel)
                        .setParameter(3, lower)
                        .setParameter(4, rate)
                        .setParameter(5, upper)
                        .executeUpdate();
                DataAccess.entityManager.getTransaction().commit();
            }else{//Existing Transaction
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.getTransaction().commit();
            }    
        UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshLST()), null);
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
        //When the entry is new
        if(updateable == null){
            if(id==0){
                NotifyUtil.error("Table Not Found","Use Notifications", false);
                this.close();
                TopComponent lstTc =WindowManager.getDefault().findTopComponent("LSTTopComponent");
                lstTc.open();
                lstTc.requestActive();
            }else 
            //check if important values are enterered
            if(rate.compareTo(BigDecimal.ZERO)<=0){ //check tier rate
                StatusDisplayer.getDefault().setStatusText("Specify the tier rate");
            }else if(lower.compareTo(BigDecimal.ZERO)<=0){
                //check lower boundary
                StatusDisplayer.getDefault().setStatusText("Specify the lower boundary");
            }else if(upper.compareTo(BigDecimal.ZERO)<=0){//check upper boundary
                StatusDisplayer.getDefault().setStatusText("Specify the Upper Boundary");
            }else if(upper.compareTo(lower)<=0){ //upper should be greater than lower
                StatusDisplayer.getDefault().setStatusText("Upper Boundary Must be greater than Lower Boundary");
            }else{
                
                //enable save
                if(getLookup().lookup(TransactionSavable.class)==null){
                    ic.add(new TransactionSavable());
                }
            }
         
        }else{
            //check if important values are enterered
            if(updateable.getTierRate()==null){ //check tier rate
                StatusDisplayer.getDefault().setStatusText("Specify the tier rate");
            }else if(updateable.getLowerBoundary()==null){
                //check lower boundary
                StatusDisplayer.getDefault().setStatusText("Specify the lower boundary");
            }else if(updateable.getUpperBoundary()==null){//check upper boundary
                StatusDisplayer.getDefault().setStatusText("Specify the Upper Boundary");
            }else if(updateable.getUpperBoundary().compareTo(updateable.getLowerBoundary())<0){ //upper should be greater than lower
                StatusDisplayer.getDefault().setStatusText("Upper Boundary Must be greater than Lower Boundary");
            }else{
                
                //enable save
                if(getLookup().lookup(TransactionSavable.class)==null){
                    ic.add(new TransactionSavable());
                }
            }
            
            
        }
    } 
    
    public void fillFields(){
        if(updateable!=null){
            setName("LST Tier "+updateable.getTierLevel()+"-"+updateable.getMonthlyLocalTaxTableID().getPeriodYear());
            jtLevel.setText(updateable.getTierLevel()+"");
            jtYear.setText(updateable.getMonthlyLocalTaxTableID().getPeriodYear()+"");
            jtLower.setValue(updateable.getLowerBoundary());
            jtUpper.setValue(updateable.getUpperBoundary());
            jtRate.setValue(updateable.getTierRate());

        }else{
            Calendar cal=Calendar.getInstance();
             year = cal.get(Calendar.YEAR);
            try{
                id = (int)DataAccess.entityManager.createNativeQuery("SELECT  MonthlyLocalTaxTableID FROM prlMonthlyLocalTaxTable WHERE PeriodYear="+year+"").getSingleResult();
                newLevel = (int)DataAccess.entityManager.createNativeQuery("SELECT Max(TierLevel)+1 FROM prlMonthlyLocalTaxTableTiers WHERE MonthlyLocalTaxTableID=(SELECT  MonthlyLocalTaxTableID FROM prlMonthlyLocalTaxTable WHERE PeriodYear="+year+")").getSingleResult();
            }catch(Exception ex){
                NotifyUtil.error("Table Not Found", "Use the notifications to create entries", ex, false);
            }
            setName("New LST Tier");
            jtLevel.setText(newLevel+"");
            jtYear.setText(year+"");
            
           
            
            
        }
        
    }
}
