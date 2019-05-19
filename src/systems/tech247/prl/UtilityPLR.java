/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.payrollreports.ReportPaySlip;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Query;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import static systems.tech247.dbaccess.DataAccess.entityManager;
import systems.tech247.hr.Contacts;
import systems.tech247.hr.Employees;
import systems.tech247.hr.HrsUsers;
import systems.tech247.hr.TblEmployeePayrollCode;
import systems.tech247.hr.TblEmployeeTransactions;
import systems.tech247.hr.TblPeriodTransactions;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.CetusUTL;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author Wilfred
 */
public class UtilityPLR implements Lookup.Provider,LookupListener {
    
    //HashMap For Actions
    public static HashMap actionMap = new HashMap();
    
    Lookup.Result<HrsUsers> rslt = CetusUTL.getInstance().getLookup().lookupResult(HrsUsers.class);
    static HrsUsers user;
    public static InstanceContent prlIC = new InstanceContent();
    public Lookup lookup = new AbstractLookup(prlIC);
    // instance
    static UtilityPLR instance;

    public UtilityPLR() {
        rslt.addLookupListener(this);
        resultChanged(new LookupEvent(rslt));
       
    }
    
    

    public static UtilityPLR getInstance() {
        if(instance==null){
            instance = new UtilityPLR();
        }
        return instance;
    }
    
    //Next Period
    public static TblPeriods getNextPeriod(TblPeriods currentPeriod){
        int nextPeriodYear = currentPeriod.getPeriodYear();
        String nextMonth;
        if(currentPeriod.getPeriodMonth().equalsIgnoreCase("December")){
            nextPeriodYear = nextPeriodYear+1;
            nextMonth = "January";
            
            TblPeriods p = new TblPeriods();
            p.setPeriodYear(nextPeriodYear);
            p.setPeriodMonth(nextMonth);
            return p;
        }else{
            int month  = CetusUTL.covertMonthsToInt(currentPeriod.getPeriodMonth());
            String[] months = DateFormatSymbols.getInstance().getMonths();
            nextMonth = months[month];
            TblPeriods p = new TblPeriods();
            p.setPeriodYear(nextPeriodYear);
            p.setPeriodMonth(nextMonth);
            return p;
            
        }
    }
    
     public void closePeriod(TblPeriods period){
         
        Object result = DialogDisplayer.getDefault().notify(new NotifyDescriptor("You are about to close the period "+period.getPeriodMonth()+" "+period.getPeriodYear()+"\nHave all transactions been imported and Payroll Run?"+"\nThis action is not reversable"+"\nProceed with period closure?", "Period Closure", NotifyDescriptor.YES_NO_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE, null, null));
        if(result==NotifyDescriptor.YES_OPTION){
                    Date date = new Date();
                String sql = 
                "UPDATE tblPeriods "
                + "SET status= 'Closed'"
                + ",closeDate=? "
                + ",CloseEmpID=? "
                + "WHERE PeriodID = "+period.getPeriodID()+"";
                entityManager.getTransaction().begin();
                Query query =  entityManager.createNativeQuery(sql);
                query.setParameter(1, date);
                if(user!=null){
                query.setParameter(2, user.getEmployeeID());
                }else{
                NotifyUtil.warn("User is null", "User not detected", false);
                }
                query.executeUpdate();
        
        
        
        
        
        String sqlCreateNew = "INSERT INTO [dbo].[tblPeriods]\n" +
"           ([CompanyID]\n" +
"           ,[PeriodMonth]\n" +
"           ,[PeriodYear]\n" +
"           ,[OpenDate]\n" +
"           ,[CloseDate]\n" +
"           ,[OpenEmpID]\n" +                
"           ,[Status])\n" +
"     VALUES\n" +
"           (1\n" +
"           ,'"+getNextPeriod(period).getPeriodMonth()+"'\n" +
"           ,"+getNextPeriod(period).getPeriodYear()+"\n" +
"           ,?"+
"           ,?" +
"           ,?" +                
"           ,'Open')";
        Date newDate = new Date();
        Query queryNewPeriod = entityManager.createNativeQuery(sqlCreateNew);
        queryNewPeriod.setParameter(1, newDate);
        queryNewPeriod.setParameter(2, newDate);
        if(user!=null){
            queryNewPeriod.setParameter(3, user.getEmployeeID());
        }else{
            NotifyUtil.warn("User is null", "User not detected", false);
        }
        queryNewPeriod.executeUpdate();
        entityManager.getTransaction().commit();
        LifecycleManager.getDefault().markForRestart();
        LifecycleManager.getDefault().exit();
        }
        

         
        
    }
    
    
    
    
    
    
    
    //Employee Payroll Information
    
    
           
    /*PLR Setup*/
    public static InstanceContent editorPRLIC = new InstanceContent();
    
    
    
    /* Payroll Code Groups Here */
    public static ExplorerManager emPLRPCodeGroups = new ExplorerManager();
    public static void loadPCodeGroups(){
        
        emPLRPCodeGroups.setRootContext(new AbstractNode(Children.create(new FactoryPayrollCodeGroups(),true)));
    }
    
    /* Payroll Code Groups Here */
    public static ExplorerManager emPLRsetup = new ExplorerManager();
    public static void loadPayrollSetup(){
        
        emPLRsetup.setRootContext(new NodePLRSetup());
    }
    
    
    
    
    /* Payroll Periods Here */
    public static ExplorerManager emPLRPayrollPeriods = new ExplorerManager();
    public static void loadPayrollPeriods(){
        
        emPLRPayrollPeriods.setRootContext(new AbstractNode(Children.create(new FactoryPayrollPeriod(),true)));
    }
    

    
    /* Pay Points */
    public static ExplorerManager emPLRPayPoints = new ExplorerManager();
    public static void loadPayPoints(){
        emPLRPayPoints.setRootContext(new PaypointRootNode());
    }
    
    
    
    
    
    /* Financial Years  */
    public static ExplorerManager emPLRYears = new ExplorerManager();
    public static void loadYears(){
        emPLRYears.setRootContext(new AbstractNode(Children.create(new FactoryFinacialYears(), true)));
    }
    
    /* PAYE Tiers  */
    public static ExplorerManager emPLRPayeTiers = new ExplorerManager();
    public static void loadPAYETiers(QueryPAYETiers query){
        emPLRPayeTiers.setRootContext(new PAYETierRootNode(query) );
    }
    
    /* LHT Tiers  */
    public static ExplorerManager emPLRLHTTiers = new ExplorerManager();
    public static void loadLHTTiers(QueryLHTTiers query){
        emPLRLHTTiers.setRootContext(new LHTTierRootNode(query) );
    }
    
    /* Cost Centers  */
    public static ExplorerManager emPLRCostCenters = new ExplorerManager();
    public static void loadCostCenters(){
        emPLRCostCenters.setRootContext(new CostCenterRootNode() );
    }
    
//    /* Cost Centers  */
//    public static ExplorerManager emPLREmployeeCostCenters = new ExplorerManager();
//    public static void loadEmployeeCostCenters(Employees emp){
//        emPLREmployeeCostCenters.setRootContext(new EmployeeCostCenterRootNode(emp) );
//    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<HrsUsers> result = (Lookup.Result<HrsUsers>)ev.getSource();
        for(HrsUsers u:result.allInstances()){
            user=u;
            
        }
    }
    
    
    
    public JRDataSource generatePaySlipInfo(Employees e,TblPeriods p){
        DRDataSource data = new DRDataSource("name","amount","currency","group");
        DataAccess da = new DataAccess();
        List<TblPeriodTransactions> list = da.getTransactions(e.getEmployeeID(), p.getPeriodYear(), CetusUTL.covertMonthsToInt(p.getPeriodMonth()));
        Collections.sort(list, new PayrollCodeGroupComparator());
        for(TblPeriodTransactions t: list){
            if(t.getPayrollCodeID().getDisplayPostedPayrollCodeValueOnPaySlip()){
                data.add(t.getPayrollCodeID().getPayrollCodeName(),new BigDecimal(t.getAmount()),t.getCurrencyID().getCurrencyCode(),t.getPayrollCodeID().getPCodeGroupID().getPCodeGroupName());
            }
        }    
        return data;
    }
    
    public static void postTx(final List<Employees> list){
        final TblPeriods p = DataAccess.getCurrentMonth();
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("(Re-)Calculate Payroll "+ p.getPeriodMonth()+" "+ p.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            
            
            
            
            
            //Do the posting for this month

            Runnable task = new Runnable() {
                @Override
                public void run() {
                    final ProgressHandle ph = ProgressHandleFactory.createHandle("Posting Payroll Transactions");
                    ph.start();
                    
                    ph.progress("Checking Existing Transactions..");
                    for(Employees e: list){
                        ph.progress("Checking Transactions For: "+e.getSurName()+" "+e.getOtherNames());
                        DataAccess.deletePeriodTransactions(p,e);    
                    }
                    
                    Long start = System.currentTimeMillis();
                    for(int i=0; i<list.size();i++){
                        final Employees e = list.get(i);
                        int j = i+1;
                        StatusDisplayer.getDefault().setStatusText(j+"/"+list.size()+" "+ e.getSurName()+" "+e.getOtherNames());
                        ph.progress(e.getSurName()+" "+e.getOtherNames());
                        final List<TblEmployeeTransactions> transactions = DataAccess.getEmployeeTransactions(e.getEmployeeID(), p.getPeriodYear(), DataAccess.covertMonthsToInt(p.getPeriodMonth()));
                        //Runnable employeeTask = new Runnable() {
                            //@Override
                            //public void run() {
                                //final ProgressHandle ph = ProgressHandleFactory.createHandle(e.getSurName()+" "+e.getSurName());
                                List processed = new ArrayList<>();
                                for(TblEmployeeTransactions t :transactions){
                                    
                                    if(!processed.contains(t.getPayrollCodeID())){
                                        ph.progress("Posting Fixed Transactions "+t.getPayrollCodeID().getPayrollCodeName()+" "+e.getSurName()+"--"+e.getOtherNames());
                                        try{
                                            DataAccess.postPayrollTransaction(e, t.getPayrollCodeID(), p);
                                        }catch(Exception ex){
                                            NotifyUtil.error("Error", "Error While Precessing for" + e.getSurName()+" "+e.getOtherNames(), ex, false);
                                        }
                                    }
                                    processed.add(t.getPayrollCodeID());
                                }
     
                        ph.progress("Posting Variable Transactions For "+e.getSurName()+" "+e.getOtherNames());
                        
                        for(TblEmployeePayrollCode code: e.getTblEmployeePayrollCodeCollection()){
                            ph.progress(code.getPayrollCodeID().getPayrollCodeName());
                            DataAccess.postPayrollTransaction(e, code.getPayrollCodeID(), p);
                        }
                        
                        
                        
                        
                    }
                    Long endTime = System.currentTimeMillis();
                    NotifyUtil.info("Task Length", endTime-start+" Milli seconds" , false);
                    ph.finish();
                    UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPayslip()), null);
                }
            };
            RequestProcessor.getDefault().post(task);
            
            
        }
        
    }
    
    public static void emailPayslip(final List<Employees> list,final TblPeriods period) throws MessagingException, AddressException,IOException{
        //Setup The Server
        // The smtp Server
                String host ="smtp.gmail.com";
                //Sender's email id
                final String from = "hr.gtcanaan@gmail.com";
                final String username="hr.gtcanaan@gmail.com";
                final String password= "Canaan@2017";
                
                
                //The SMTP Properties
                String userName = "hr.gtcanaan@gmail.com";
                String passWord = "Canaan@2017";
                final Properties props = new Properties();
                props.put("mail.smtp.auth", true);
                props.put("mail.smtp.starttls.enable", true);
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", 587);
                props.put("mail.user",userName);
                props.put("mail.password",passWord);
                
                //The autheniticator
                final Authenticator auth = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }
                };
        
        Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        //Prepare The Slips and send them
                        final ProgressHandle ph = ProgressHandleFactory.createHandle("Sending payslips to Employees");
                        ph.start();
                        Long start = System.currentTimeMillis();
                        for(Employees e: list){
                            ph.progress("Working on: "+e.getSurName()+" "+e.getOtherNames());
                            List<Contacts> contacts = DataAccess.searchEmployeeContacts(e);
                            List<Contacts> emails = new ArrayList();
                            for(Contacts c: contacts){
                            if(CetusUTL.validateEmail(c.getContact())){
                                emails.add(c);
                            }
                        }
                        if(emails.size()>=1){
                            JRDataSource payslipData = UtilityPLR.getInstance().generatePaySlipInfo(e, period);
                            JasperReportBuilder report = (new ReportPaySlip(e,payslipData,period)).getReport();
                
                
                            //Get the Session
                            Session session = Session.getInstance(props, auth);
                
                            for(Contacts c: emails){
                                Message message =  new MimeMessage(session);
                                
                                try{
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
                        
                                Transport.send(message);
                                StatusDisplayer.getDefault().setStatusText("Payslip sent to: "+e.getSurName()+" "+e.getOtherNames()+"("+c.getContact()+")");
                                }catch(MessagingException | IOException ex){
                                    NotifyUtil.error("Something went wrong", "Something went wrong", ex, false);
                                }
                            }
                    
               
                        }else{
                            StatusDisplayer.getDefault().setStatusText(e.getSurName()+" "+e.getOtherNames()+"has no valid email address");
                        }
            
                    }
                    Long endTime = System.currentTimeMillis();
                    NotifyUtil.info("Task Length", endTime-start+" Milli seconds" , false);
                    ph.finish();
                        
                }
            };
        RequestProcessor.getDefault().post(task);
        
        
    }
    
}
