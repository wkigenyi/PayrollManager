/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Currencies;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//SalaryCalculator//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SalaryCalculatorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.SalaryCalculatorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SalaryCalculatorAction",
        preferredID = "SalaryCalculatorTopComponent"
)
@Messages({
    "CTL_SalaryCalculatorAction=SalaryCalculator",
    "CTL_SalaryCalculatorTopComponent=Salary Calculator",
    "HINT_SalaryCalculatorTopComponent="
})
public final class SalaryCalculatorTopComponent extends TopComponent implements ChangeListener, LookupListener{
    
    TopComponent currencyTc = WindowManager.getDefault().findTopComponent("CurrenciesTopComponent");
    Lookup.Result<Currencies> currencyRslt = currencyTc.getLookup().lookupResult(Currencies.class);
    Currencies currency = DataAccess.getBaseCurrency();
    Double rate = currency.getConversionRate().doubleValue();
    NumberFormat nf = new DecimalFormat("#,###.00");
    public SalaryCalculatorTopComponent() {
        initComponents();
        setName(Bundle.CTL_SalaryCalculatorTopComponent());
        setToolTipText(Bundle.HINT_SalaryCalculatorTopComponent());
        jtCurrency.setText(currency.getCurrencyName());
        jsBasic.addChangeListener(this);
        jsOtherPay.addChangeListener(this);
        
        currencyRslt.addLookupListener(this);
        resultChanged(new LookupEvent(currencyRslt));
        
        jtCurrency.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = {new JButton("Close")};
                DialogDisplayer.getDefault().notify(new DialogDescriptor(currencyTc, "Select Currency",true, options,null,DialogDescriptor.DEFAULT_ALIGN,null,null));
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jftPay = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jftGross = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jftEmployerNSSF = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jsBasic = new javax.swing.JSpinner();
        jftEmployeeNSSF = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jsOtherPay = new javax.swing.JSpinner();
        jftNetPay = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jlInfo = new javax.swing.JLabel();
        jtCurrency = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jftPay.setEditable(false);
        jftPay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftPay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftPay.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftPay.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel5.text")); // NOI18N

        jftGross.setEditable(false);
        jftGross.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftGross.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftGross.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftGross.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel4.text")); // NOI18N

        jftEmployerNSSF.setEditable(false);
        jftEmployerNSSF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftEmployerNSSF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftEmployerNSSF.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftEmployerNSSF.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel6.text")); // NOI18N

        jsBasic.setModel(new javax.swing.SpinnerNumberModel(5000.0d, null, null, 10.0d));

        jftEmployeeNSSF.setEditable(false);
        jftEmployeeNSSF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftEmployeeNSSF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftEmployeeNSSF.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftEmployeeNSSF.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel2.text")); // NOI18N

        jsOtherPay.setModel(new javax.swing.SpinnerNumberModel());

        jftNetPay.setEditable(false);
        jftNetPay.setBackground(new java.awt.Color(0, 255, 51));
        jftNetPay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftNetPay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftNetPay.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftNetPay.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jsBasic, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jsOtherPay, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jftGross)
                    .addComponent(jftPay)
                    .addComponent(jftEmployeeNSSF)
                    .addComponent(jftNetPay)
                    .addComponent(jftEmployerNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jsBasic, jsOtherPay});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jsBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jsOtherPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftGross, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployeeNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftNetPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployerNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel8.text")); // NOI18N

        jlInfo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jlInfo, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jlInfo.text")); // NOI18N

        jtCurrency.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jtCurrency.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtCurrency)))))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInfo)
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField jftEmployeeNSSF;
    private javax.swing.JFormattedTextField jftEmployerNSSF;
    private javax.swing.JFormattedTextField jftGross;
    private javax.swing.JFormattedTextField jftNetPay;
    private javax.swing.JFormattedTextField jftPay;
    private javax.swing.JLabel jlInfo;
    private javax.swing.JSpinner jsBasic;
    private javax.swing.JSpinner jsOtherPay;
    private javax.swing.JTextField jtCurrency;
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

    @Override
    public void stateChanged(ChangeEvent e) {
        
        
            Double basic = new Double(jsBasic.getValue().toString());
            Double otherPay = new Double(jsOtherPay.getValue().toString());
            Double gross = (basic+otherPay)*rate;
            Double nssf =  DataAccess.getNSSF().getGrossPercentage()*gross/100;
            Double paye = new DataAccess().getPaye(gross).doubleValue();
            Double netPay = gross - nssf- paye.doubleValue();
            Double employerNSSF = nssf * DataAccess.getNSSF().getEmployerFactor();
            jftGross.setValue(gross/rate);
            jftPay.setValue(paye/rate);
            jftNetPay.setValue(netPay/rate);
            jftEmployeeNSSF.setValue(nssf/rate);
            jftEmployerNSSF.setValue(employerNSSF/rate);
        
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result result = (Lookup.Result)ev.getSource();
        for (Object e: result.allInstances()){
            if(e instanceof Currencies){
                
                currency = (Currencies)e;
                rate = currency.getConversionRate().doubleValue();
                jtCurrency.setText(((Currencies) e).getCurrencyName());
                if(currency.getIsBaseCurrency()){
                    jlInfo.setText("This is the base currency");
                }else{
                    jlInfo.setText("Conversion Rate is: "+ nf.format(currency.getConversionRate()));
                }
                
                stateChanged(new ChangeEvent(e));
                
            }
        }
    }
    
    
}
