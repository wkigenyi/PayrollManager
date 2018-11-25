/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.io.IOException;
import java.util.Arrays;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.awt.ActionID;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Currencies;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPayrollCodeGroups;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PayrollCodeGroupEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PayrollCodeGroupEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.PayrollCodeGroupEditorTopComponent")
//*@ActionReference(path = "Menu/Window" /*, position = 333 */)
/*@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PayrollCodeEditorAction",
        preferredID = "PayrollCodeEditorTopComponent"
)*/
@Messages({
    "CTL_PayrollCodeGroupEditorAction=PayrollCodeGroupEditor",
    "CTL_PayrollCodeGroupEditorTopComponent=Payroll Code Group",
    "HINT_PayrollCodeGroupEditorTopComponent="
})
public final class PayrollCodeGroupEditorTopComponent extends TopComponent {
    
    //when updating you need to have the updateable in memory
    TblPayrollCodeGroups updateable;
    
    //The Variables to fill out
    String codeName = "";
    String comment = "";
    String group = "";
    Currencies curr;
    
    Employees emp;
    
    Double amount = 0.0;
    Boolean taxable =  false;
    Boolean processInPayroll = false;
    Boolean payment = true;
    Boolean deduction = false;
    Boolean displayOnPayslip = false;
    Boolean active = false;
    
    
    
    InstanceContent ic = new InstanceContent();
    public PayrollCodeGroupEditorTopComponent(){
        this(null);
    }
    
    

    public PayrollCodeGroupEditorTopComponent(TblPayrollCodeGroups code) {
        initComponents();
        setName(Bundle.CTL_PayrollCodeGroupEditorTopComponent());
        setToolTipText(Bundle.HINT_PayrollCodeGroupEditorTopComponent());
        
        try{
            updateable=DataAccess.entityManager.find(TblPayrollCodeGroups.class, code.getPCodeGroupID());
        }catch(NullPointerException ex){
            
        }
        fillTheFields();
       
        jtCodeName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPCodeGroupName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPCodeGroupName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                codeName= jtCodeName.getText();
                try{
                    updateable.setPCodeGroupName(codeName);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });
        
        jtComment.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                comment= jtComment.getText();
                try{
                    updateable.setComment(comment);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                comment= jtComment.getText();
                try{
                    updateable.setComment(comment);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                comment= jtComment.getText();
                try{
                    updateable.setComment(comment);
                }catch(NullPointerException ex){
                    
                }
                modify();
            }
        });

        
       
        
        
        
        
        
        
        
        
        
        

    }
    void fillTheFields(){
        if(updateable!=null){
            try{
            
            jtCodeName.setText(updateable.getPCodeGroupName());
            jtComment.setText(updateable.getComment());
            
            

            
            
                  
            
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtComment = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PayrollCodeGroupEditorTopComponent.class, "PayrollCodeGroupEditorTopComponent.jLabel1.text")); // NOI18N

        jtCodeName.setText(org.openide.util.NbBundle.getMessage(PayrollCodeGroupEditorTopComponent.class, "PayrollCodeGroupEditorTopComponent.jtCodeName.text")); // NOI18N

        jtComment.setColumns(20);
        jtComment.setRows(5);
        jScrollPane1.setViewportView(jtComment);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(PayrollCodeGroupEditorTopComponent.class, "PayrollCodeGroupEditorTopComponent.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtCodeName, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgCodeType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtCodeName;
    private javax.swing.JTextArea jtComment;
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

    






    
    


    
    private class PayrollCodeSavable extends AbstractSavable{
        
        PayrollCodeSavable(){
            register();
        }

        @Override
        protected String findDisplayName() {
            return "Payroll Code Group";
        }
        
        PayrollCodeGroupEditorTopComponent tc(){
            return PayrollCodeGroupEditorTopComponent.this;
        }

        @Override
        protected void handleSave() throws IOException {
            //New Transaction Code
            if(updateable==null){
            String sql = "INSERT INTO [dbo].[tblPayrollCodeGroups]\n" +
"           ([PCodeGroupName]\n" +
"           ,[Comment])\n" +
"     VALUES\n" +
"           (?,?)";
            

                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.createNativeQuery(sql)
                        .setParameter(1, codeName)
                        .setParameter(2, comment)
                      
                        .executeUpdate();
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPCodeGroup()), null);
                tc().close();
            
            
        }else{
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPCodeGroup()), null);
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
            if(codeName.length()<4){
                StatusDisplayer.getDefault().setStatusText("Specify A Proper Name");
            }else{
               
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
                }
            }
        }else{
            if(updateable.getPCodeGroupName().length()<4){
                StatusDisplayer.getDefault().setStatusText("Proper Transaction Code is Required");
            }else{
                //Real Changes have happened
                if(!codeName.equals(updateable.getPCodeGroupName()) || !comment.equals(updateable.getComment()))
                if(getLookup().lookup(PayrollCodeSavable.class)==null){
                    ic.add(new PayrollCodeSavable());
            }
        }
    }
        
            
        
    
    }
}
