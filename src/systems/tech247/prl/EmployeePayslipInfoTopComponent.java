/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.BorderLayout;
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
import org.netbeans.api.settings.ConvertAsProperties;
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
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EmployeePayslipInfoAction",
        preferredID = "EmployeePayslipInfoTopComponent"
)
@Messages({
    "CTL_EmployeePayslipInfoAction=EmployeePayslipInfo",
    "CTL_EmployeePayslipInfoTopComponent=EmployeePayslipInfo Window",
    "HINT_EmployeePayslipInfoTopComponent=This is a EmployeePayslipInfo window"
})
public final class EmployeePayslipInfoTopComponent extends TopComponent implements ExplorerManager.Provider,LookupListener {

    Lookup.Result<TblPeriods> rslt = WindowManager.getDefault().findTopComponent("PeriodsTopComponent").getLookup().lookupResult(TblPeriods.class);
    ExplorerManager em = new ExplorerManager();
    Employees emp;
    InstanceContent content = new InstanceContent();
    TblPeriods period = DataAccess.getCurrentMonth();
    Lookup lookup = new AbstractLookup(content);
    public EmployeePayslipInfoTopComponent() {
        this(null);
    }
    public EmployeePayslipInfoTopComponent(final Employees emp) {
        initComponents();
        setName("Payroll Info ->"+ emp.getSurName()+" "+emp.getOtherNames());
        setToolTipText(Bundle.HINT_EmployeePayslipInfoTopComponent());
        this.emp = emp;
        setLayout(new BorderLayout());
        OutlineView ov = new OutlineView("Payslip Info");
        ov.getOutline().setRootVisible(false);
        ov.addPropertyColumn("amount", "Amount");
        ov.addPropertyColumn("deduction", "Deduction");
        ov.addPropertyColumn("category", "Category");
        add(ov);
        associateLookup(lookup);
        content.add(new CapEmail() {
            @Override
            public void email() {
                //Do the emailing
                //Set up the email address of the user
                List<Contacts> list = DataAccess.searchEmployeeContacts(emp);
                List<Contacts> emails = new ArrayList();
                for(Contacts c: list){
                    if(CetusUTL.validateEmail(c.getContact())){
                        emails.add(c);
                    }
                }
                
                
                if(emails.size()>=1){
                    //Create the report
                JasperReportBuilder report = (new ReportPaySlip(period, emp)).getReport();    
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
                        Transport.send(message);
                        StatusDisplayer.getDefault().setStatusText("Payslip sent to: "+emp.getSurName()+" "+emp.getOtherNames()+"("+c.getContact()+")");
                    }
                    
                }catch(MessagingException ex){
                    NotifyUtil.error("Error", "Error", ex, false);
                }
            }else{
                StatusDisplayer.getDefault().setStatusText("Employee has no valid email address");
            }
                    
        }
    });
        
        
        
        content.add(new CapPreview() {
            @Override
            public void preview() {
                //Preview
                JasperReportBuilder report = (new ReportPaySlip(period, emp)).getReport();
                try {
                    report.show(false);
                   
                } catch (DRException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
        content.add(new CapPrint() {
            @Override
            public void print() {
                //Print
                //Preview
                JasperReportBuilder report = (new ReportPaySlip(period, emp)).getReport();
                try {
                    report.print(true);
                   
                } catch (DRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                
                
                
                
            }
        });
        
        rslt.addLookupListener(this);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        reload();
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
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<TblPeriods> rslt = (Lookup.Result<TblPeriods>)ev.getSource();
        for(TblPeriods p: rslt.allInstances()){
            period = p;
            reload();
        }
    }
    
    void reload(){
        em.setRootContext(new AbstractNode(Children.create(new FactoryEmployeePayslipTransactions(emp,period), true)));
    }
    
    
    
    
    
    
}
