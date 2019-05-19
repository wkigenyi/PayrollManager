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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Currencies;
import systems.tech247.util.NotifyUtil;

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
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_SalaryCalculatorAction",
//        preferredID = "SalaryCalculatorTopComponent"
//)
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
    Double basic = 0.0;
    Double otherPay = 0.0;
    Double gross = 0.0;
    Double nssf =  0.0;
    Double paye = 0.0;
    Double netPay = 0.0;
    Double employerNSSF = 0.0;
    Double basicPay2 = 0.0;
    Double netPay2 = 0.0;
    boolean bpaye = true;
    boolean bnssf = true;
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
        
        jftNetPay2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try{
                    netPay2 = new Double(jftNetPay2.getText());
                    jbEstimateBasicPay.setEnabled(true);
                }catch(NumberFormatException ex){
                    //NotifyUtil.error("Not A Number", "Only Numbers Are Accepted", ex, false);
                    //jbEstimateBasicPay.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try{
                    netPay2 = new Double(jftNetPay2.getText());
                    jbEstimateBasicPay.setEnabled(true);
                }catch(NumberFormatException ex){
                    //NotifyUtil.error("Not A Number", "Only Numbers Are Accepted", ex, false);
                    //jbEstimateBasicPay.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try{
                    netPay2 = new Double(jftNetPay2.getText());
                    jbEstimateBasicPay.setEnabled(true);
                }catch(NumberFormatException ex){
                    //NotifyUtil.error("Not A Number", "Only Numbers Are Accepted", ex, false);
                    //jbEstimateBasicPay.setEnabled(false);
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

        jpBasicToNet = new javax.swing.JPanel();
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
        jpNet2Basic = new javax.swing.JPanel();
        jftPaye2 = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jftGross1 = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jftEmployerNSSF1 = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jftEmployeeNSSF1 = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jftNetPay2 = new javax.swing.JFormattedTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jftBasic2 = new javax.swing.JFormattedTextField();
        jcbPAYE = new javax.swing.JCheckBox();
        jcbNSSF = new javax.swing.JCheckBox();
        jbEstimateBasicPay = new javax.swing.JButton();

        jpBasicToNet.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jpBasicToNet.border.title"))); // NOI18N

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

        javax.swing.GroupLayout jpBasicToNetLayout = new javax.swing.GroupLayout(jpBasicToNet);
        jpBasicToNet.setLayout(jpBasicToNetLayout);
        jpBasicToNetLayout.setHorizontalGroup(
            jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBasicToNetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jsBasic, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jsOtherPay, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jftGross)
                    .addComponent(jftPay)
                    .addComponent(jftEmployeeNSSF)
                    .addComponent(jftNetPay)
                    .addComponent(jftEmployerNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jpBasicToNetLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jsBasic, jsOtherPay});

        jpBasicToNetLayout.setVerticalGroup(
            jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBasicToNetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jsBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jsOtherPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftGross, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployeeNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftNetPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBasicToNetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployerNSSF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel8.text")); // NOI18N

        jlInfo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jlInfo, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jlInfo.text")); // NOI18N

        jtCurrency.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jtCurrency.text")); // NOI18N

        jpNet2Basic.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jpNet2Basic.border.title"))); // NOI18N

        jftPaye2.setEditable(false);
        jftPaye2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftPaye2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftPaye2.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftPaye2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel10.text")); // NOI18N

        jftGross1.setEditable(false);
        jftGross1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftGross1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftGross1.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftGross1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel11.text")); // NOI18N

        jftEmployerNSSF1.setEditable(false);
        jftEmployerNSSF1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftEmployerNSSF1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftEmployerNSSF1.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftEmployerNSSF1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel14.text")); // NOI18N

        jftEmployeeNSSF1.setEditable(false);
        jftEmployeeNSSF1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftEmployeeNSSF1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftEmployeeNSSF1.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftEmployeeNSSF1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jLabel15.text")); // NOI18N

        jftNetPay2.setBackground(new java.awt.Color(0, 255, 51));
        jftNetPay2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftNetPay2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftNetPay2.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftNetPay2.text")); // NOI18N

        jFormattedTextField1.setEditable(false);
        jFormattedTextField1.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jFormattedTextField1.text")); // NOI18N

        jftBasic2.setEditable(false);
        jftBasic2.setBackground(new java.awt.Color(255, 255, 0));
        jftBasic2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        jftBasic2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jftBasic2.setText(org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jftBasic2.text")); // NOI18N

        javax.swing.GroupLayout jpNet2BasicLayout = new javax.swing.GroupLayout(jpNet2Basic);
        jpNet2Basic.setLayout(jpNet2BasicLayout);
        jpNet2BasicLayout.setHorizontalGroup(
            jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpNet2BasicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jftGross1)
                    .addComponent(jftPaye2)
                    .addComponent(jftEmployeeNSSF1)
                    .addComponent(jftNetPay2)
                    .addComponent(jftEmployerNSSF1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1)
                    .addComponent(jftBasic2))
                .addContainerGap())
        );
        jpNet2BasicLayout.setVerticalGroup(
            jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpNet2BasicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jftBasic2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftGross1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftPaye2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployeeNSSF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftNetPay2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpNet2BasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftEmployerNSSF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jcbPAYE, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jcbPAYE.text")); // NOI18N
        jcbPAYE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPAYEActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbNSSF, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jcbNSSF.text")); // NOI18N
        jcbNSSF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbNSSFActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jbEstimateBasicPay, org.openide.util.NbBundle.getMessage(SalaryCalculatorTopComponent.class, "SalaryCalculatorTopComponent.jbEstimateBasicPay.text")); // NOI18N
        jbEstimateBasicPay.setEnabled(false);
        jbEstimateBasicPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEstimateBasicPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpBasicToNet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbPAYE)
                            .addComponent(jcbNSSF)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 95, Short.MAX_VALUE)
                        .addComponent(jbEstimateBasicPay)
                        .addContainerGap(95, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpNet2Basic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(420, 420, 420))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpBasicToNet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpNet2Basic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jcbPAYE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbNSSF)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbEstimateBasicPay)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jtCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlInfo)
                .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbEstimateBasicPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEstimateBasicPayActionPerformed
        basicPay2 = DataAccess.getNetToBasic(netPay2,bpaye,bnssf);
        jftBasic2.setValue(basicPay2);
        if(bnssf){
            jftEmployeeNSSF1.setValue(DataAccess.getEmployeeNssf(basicPay2));
        }
        if(bpaye){
            jftPaye2.setValue(DataAccess.getPAYE(basicPay2));
        }
    }//GEN-LAST:event_jbEstimateBasicPayActionPerformed

    private void jcbPAYEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPAYEActionPerformed
        bpaye = jcbPAYE.isSelected();
    }//GEN-LAST:event_jcbPAYEActionPerformed

    private void jcbNSSFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbNSSFActionPerformed
        bnssf = jcbNSSF.isSelected();
    }//GEN-LAST:event_jcbNSSFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbEstimateBasicPay;
    private javax.swing.JCheckBox jcbNSSF;
    private javax.swing.JCheckBox jcbPAYE;
    private javax.swing.JFormattedTextField jftBasic2;
    private javax.swing.JFormattedTextField jftEmployeeNSSF;
    private javax.swing.JFormattedTextField jftEmployeeNSSF1;
    private javax.swing.JFormattedTextField jftEmployerNSSF;
    private javax.swing.JFormattedTextField jftEmployerNSSF1;
    private javax.swing.JFormattedTextField jftGross;
    private javax.swing.JFormattedTextField jftGross1;
    private javax.swing.JFormattedTextField jftNetPay;
    private javax.swing.JFormattedTextField jftNetPay2;
    private javax.swing.JFormattedTextField jftPay;
    private javax.swing.JFormattedTextField jftPaye2;
    private javax.swing.JLabel jlInfo;
    private javax.swing.JPanel jpBasicToNet;
    private javax.swing.JPanel jpNet2Basic;
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
        
            try{
                basic = new Double(jsBasic.getValue().toString());
            }catch(NumberFormatException ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            try{
                otherPay = new Double(jsOtherPay.getValue().toString());
            }catch(NumberFormatException ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            
            try{
                gross = (basic+otherPay)*rate;
            }catch(Exception ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            
            try{
                nssf =  DataAccess.getEmployeeNssf(gross);
            }catch(Exception ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            
            try{
                paye = DataAccess.getPAYE(gross);
            }catch(Exception ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            
            netPay = gross - nssf- paye;
            employerNSSF = nssf*2;
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
