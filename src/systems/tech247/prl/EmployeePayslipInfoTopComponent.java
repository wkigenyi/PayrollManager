/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.payrollreports.ReportPaySlip;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Contacts;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.CapEmail;
import systems.tech247.util.CapPreview;
import systems.tech247.util.CapPrint;
import systems.tech247.util.CetusUTL;
import systems.tech247.util.NotifyUtil;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//EmployeePayslipInfo//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EmployeePayslipInfoTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.EmployeePayslipInfoTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_EmployeePayslipInfoAction",
//        preferredID = "EmployeePayslipInfoTopComponent"
//)
@Messages({
    "CTL_EmployeePayslipInfoAction=EmployeePayslipInfo",
    "CTL_EmployeePayslipInfoTopComponent=EmployeePayslipInfo Window",
    "HINT_EmployeePayslipInfoTopComponent=This is a EmployeePayslipInfo window"
})
public final class EmployeePayslipInfoTopComponent extends TopComponent implements ExplorerManager.Provider,LookupListener {

    TopComponent tc = WindowManager.getDefault().findTopComponent("PeriodsTopComponent");
    Lookup.Result<TblPeriods> rslt = tc.getLookup().lookupResult(TblPeriods.class);
    ExplorerManager em = new ExplorerManager();
    Employees emp;
    InstanceContent content = new InstanceContent();
    TblPeriods period = DataAccess.getCurrentMonth();
    Lookup lookup = new AbstractLookup(content);
    JRDataSource payslipData;
    public EmployeePayslipInfoTopComponent() {
        this(null);
    }
    public EmployeePayslipInfoTopComponent(final Employees emp) {
        initComponents();
        setName("Payroll Info ->"+ emp.getSurName()+" "+emp.getOtherNames());
        setToolTipText(Bundle.HINT_EmployeePayslipInfoTopComponent());
        this.emp = emp;
        jpView.setLayout(new BorderLayout());
        OutlineView ov = new OutlineView("Payslip Info");
        ov.getOutline().setRootVisible(false);
        ov.addPropertyColumn("amount", "Amount");
        ov.addPropertyColumn("currency", "Currency");
        ov.addPropertyColumn("rate", "Ex. Rate");
        ov.addPropertyColumn("converted", "Base Amount");
        ov.addPropertyColumn("deduction", "Deduction");
        ov.addPropertyColumn("category", "Category");
        jpView.add(ov);
        associateLookup(lookup);
        jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
        content.add(new CapEmail() {
            @Override
            public void email() {
                //Do the emailing
                //Set up the email address of the user
                //Post a task:
                ProgressHandle ph = ProgressHandleFactory.createHandle("Getting Info Required to email payslip..");
                
                RequestProcessor.getDefault().post(new Runnable() {
                    @Override
                    public void run() {
                        ph.start();
                        ph.progress("Checking contact info..");
                        List<Contacts> list = DataAccess.searchEmployeeContacts(emp);
                List<Contacts> emails = new ArrayList();
                for(Contacts c: list){
                    if(CetusUTL.validateEmail(c.getContact())){
                        emails.add(c);
                    }
                }
                
                
                if(emails.size()>=1){
                    //Create the report
                    ph.progress("Putting info together..");
                            
                JasperReportBuilder report = (new ReportPaySlip(emp,payslipData,period)).getReport();    
                    // The smtp Server
                String host ="smtp.gmail.com";
                //Sender's email id
                String from = "hr.gtcanaan@gmail.com";
                final String username="hr.gtcanaan@gmail.com";
                final String password= "Canaan@2017";
                
                
                //The SMTP Properties
                String userName = "hr.gtcanaan@gmail.com";
                String passWord = "Canaan@2017";
                Properties props = new Properties();
                props.put("mail.smtp.auth", true);
                props.put("mail.smtp.starttls.enable", true);
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", 587);
                props.put("mail.user",userName);
                props.put("mail.password",passWord);
                
                //The autheniticator
                Authenticator auth = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }
                };
                
                //Get the Session
                Session session = Session.getInstance(props, auth);
                        
                
                //Create an  Email Message
                ph.progress("Creating the email");
                try{
                    
                    for(Contacts c: emails){
                        Message message =  new MimeMessage(session);
                
                        //Set FROM
                        message.setFrom(new InternetAddress(from));
                        //Set TO
                        InternetAddress[] toAddress = new InternetAddress[]{new InternetAddress(c.getContact())};
                        message.setRecipients(Message.RecipientType.TO, toAddress);
                
                        //Set Subject
                        message.setSubject("Payslip for" + period.getPeriodMonth()+" "+period.getPeriodYear());
                        //The sent Date
                        message.setSentDate(new Date());
                
                        //The Body parts
                
                        //The Message
                        MimeBodyPart messagePart = new MimeBodyPart();
                        messagePart.setText("Your Payslip this month");
                
                        try{
                            //The Attachment
                            MimeBodyPart attachmentPart = new MimeBodyPart();
                            String attachFile = "C:/Payslips/PaySlip.pdf";
                            attachmentPart.attachFile(attachFile);
                        
                
                            //Set the email to carry attachments
                            Multipart multipart = new MimeMultipart();
                
                   
                
                
                            //Add it all together
                            multipart.addBodyPart(messagePart);
                            multipart.addBodyPart(attachmentPart);
                        
                
                
                
                
                            //Now the actual message
                            message.setContent(multipart);
                        }catch(IOException ex){
                            NotifyUtil.error(from, userName, ex, false);
                        }
                        ph.progress("Sending email.. ");
                        Transport.send(message);
                        StatusDisplayer.getDefault().setStatusText("Payslip sent to: "+emp.getSurName()+" "+emp.getOtherNames()+"("+c.getContact()+")");
                    }
                    
                }catch(MessagingException ex){
                    NotifyUtil.error("Error", "Error", ex, false);
                }
            }else{
                StatusDisplayer.getDefault().setStatusText("Employee has no valid email address");
            }
                    ph.finish();
                    }
                    
                });
                
                
                    
        }
    });
        
        
        
        content.add(new CapPreview() {
            @Override
            public void preview() {
                //Preview
                RequestProcessor.getDefault().post(new Runnable() {
                    ProgressHandle ph = ProgressHandleFactory.createHandle("Compiling Payslip Info..");
                    @Override
                    public void run() {
                        ph.start();
                        ph.progress("Building the report..");
                                
                        JasperReportBuilder report = (new ReportPaySlip(emp,payslipData,period)).getReport();
                try {
                    report.show(false);
                   
                } catch (DRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                ph.finish();
                    }
                });
                
            }
        });
        content.add(new CapPrint() {
            @Override
            public void print() {
                //Print
                //Preview
                ProgressHandle ph = ProgressHandleFactory.createHandle("Building the report");
                RequestProcessor.getDefault().post(new Runnable() {
                    @Override
                    public void run() {
                        ph.start();
                        ph.progress("Putting the data together");
                        JasperReportBuilder report = (new ReportPaySlip(emp,payslipData,period)).getReport();
                try {
                    report.print(true);
                   
                } catch (DRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                ph.finish();
                    }
                });
                
                
                
                
                
            }
        });
        
        rslt.addLookupListener(this);
        
        jtPeriod.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(tc, "Select Period"));
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

        jpView = new javax.swing.JPanel();
        jtPeriod = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jpView.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpViewLayout = new javax.swing.GroupLayout(jpView);
        jpView.setLayout(jpViewLayout);
        jpViewLayout.setHorizontalGroup(
            jpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpViewLayout.setVerticalGroup(
            jpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jtPeriod.setText(org.openide.util.NbBundle.getMessage(EmployeePayslipInfoTopComponent.class, "EmployeePayslipInfoTopComponent.jtPeriod.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(EmployeePayslipInfoTopComponent.class, "EmployeePayslipInfoTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 136, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPeriod)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jpView;
    private javax.swing.JTextField jtPeriod;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        reload();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        RequestProcessor.getDefault().post(new Runnable() {
            @Override
            public void run() {
                payslipData = UtilityPLR.getInstance().generatePaySlipInfo(emp, period);
            }
        });
//To change body of generated methods, choose Tools | Templates.
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
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<TblPeriods> rslt = (Lookup.Result<TblPeriods>)ev.getSource();
        for(TblPeriods p: rslt.allInstances()){
            period = p;
            jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
            payslipData = UtilityPLR.getInstance().generatePaySlipInfo(emp, period);
            reload();
        }
    }
    
    void reload(){
        em.setRootContext(new AbstractNode(Children.create(new FactoryEmployeePayslipTransactions(emp,period), true)));
    }
    
    
    
    
    
    
}
