/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblEmployeePayrollCode;
import systems.tech247.hr.TblEmployeeTransactions;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.CapClosePeriod;
import systems.tech247.util.NotifyUtil;


/**
 *
 * @author WKigenyi
 */
public class NodePayrollPeriod  extends AbstractNode implements LookupListener{
    
    InstanceContent content;
    TblPeriods period;
    Lookup.Result<NodeRefreshPeriod> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPeriod.class);
    public NodePayrollPeriod(PeriodCover bean) throws IntrospectionException{
        this(bean, new InstanceContent());
    }
    
    
    public NodePayrollPeriod(PeriodCover bean, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF,new AbstractLookup(ic));
        content = ic;
        period = bean.getPeriod();
        if(!period.getStatus().equalsIgnoreCase("Closed")){
            content.add(new CapClosePeriod() {
            @Override
            public void closePeriod() {
                NotifyUtil.warn("Period Closure Warning", "Have all transactions for this period been entered?\nThe Period cannot be opened once closed!\nThe system will Restart after closing the period.", false);
                Object result = DialogDisplayer.getDefault().notify(new NotifyDescriptor.Confirmation("Close "+period.getPeriodMonth()+" "+period.getPeriodYear()+" ?\n","Closing Period",NotifyDescriptor.YES_NO_CANCEL_OPTION));
                if(result.equals(NotifyDescriptor.YES_OPTION)){
                    UtilityPLR.closePeriod(period);
                    LifecycleManager.getDefault().markForRestart();//we want to Restart
                    LifecycleManager.getDefault().exit();
                }
                
            }
        });
        }
        
        //this.ic.add(bean);
        period = bean.getPeriod();
        content.add(period);
        setDisplayName(bean.getPeriodname());
        if(period.getStatus().equalsIgnoreCase("Closed"))
        setIconBaseWithExtension("systems/tech247/util/icons/yearCalendar.png");
        else
        setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");    
    }
    
    @Override 
    protected Sheet createSheet(){
        final PeriodCover bean = getLookup().lookup(PeriodCover.class);
        Sheet basic = super.createSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName("Details");
        
        try{
            Property opendateProperty;
            opendateProperty = new PropertySupport("opendate", String.class, "Opened Date", "Date when the period opened", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getOpenDate();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property closedateProperty;
            closedateProperty = new PropertySupport("closeddate", String.class, "Closed Date", "Date when the period Closed", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCloseDate();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property openbyProperty;
            openbyProperty = new PropertySupport("openby", String.class, "Opened By", "Employee That Opened the Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getOpenedby();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property closedbyProperty;
            closedbyProperty = new PropertySupport("closedby", String.class, "Closed By", "Employee That Closed the Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getClosedBy();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property nextPeriodProperty;
            nextPeriodProperty = new PropertySupport("nextPeriod", String.class, "Next Period", "Next Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getNextPeriod();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            
            
            
            
            set.put(opendateProperty);
            set.put(closedateProperty);
            set.put(openbyProperty);
            set.put(closedbyProperty);
            set.put(nextPeriodProperty);
        }catch(Exception ex){
            
        }
        
        
        basic.put(set);
        return basic;
        
    }

    @Override
    public Action[] getActions(boolean context) {
        
        Action addGobalTransaction =new AbstractAction("Add Global Transaction") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TopComponent txEditor = WindowManager.getDefault().findTopComponent("TransactionEditorTopComponent");
                    txEditor.open();
                }
            };
        
        Action runPayroll = new AbstractAction("Run Payroll") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Object result = DialogDisplayer.getDefault().notify(new DialogDescriptor(new PayrollRunOptions(period), "Select An Option To Run"));
                    if(result.equals(DialogDescriptor.OK_OPTION)){
                        String action =  (String)(UtilityPLR.actionMap.get("action"));
                        
                        switch(action){
                            case    "post":
                                
                                postTx();
                                
                                
                                break;
                            case    "closeperiod":
                                closePeriod();
                                UtilityPLR.loadPayrollPeriods();
                                break;
                            case    "showpayrollreport":
                                SetupReport();
                                break;        
                            default:
                                
                        }
                        
                        
                    }else{
                        StatusDisplayer.getDefault().setStatusText("Tosazeewo!!");
                    }
                }
            };
        Action viewPayrollReport = new AbstractAction("View Payroll Report") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SetupReport();
                }
            };
        //Closed Periods are only for Reports
        List<Action> actionList = new ArrayList();
        if(!period.getStatus().equalsIgnoreCase("Closed")){
            actionList.add(addGobalTransaction);
            actionList.add(runPayroll);
        }
        actionList.add(viewPayrollReport);
        int width = actionList.size();
        Action[] actions = new Action[width];
        for(int i=0;i<width;i++){
            actions[i] = actionList.get(i);
        }
        return actions;
        //return super.getActions(context); //To change body of generated methods, choose Tools | Templates.
    }
    
    void SetupReport(){
        TopComponent tc = new ReportEditorTopComponent(period);
        tc.open();
        tc.requestActive();
    }
    
    
    void postTx(){
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("(Re-)Calculate Payroll "+ period.getPeriodMonth()+" "+ period.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            
            final DataAccess da = new DataAccess();
            
            //Delete existing transactions
            DataAccess.deletePeriodTransactions(period);
            //Do the posting for this month
            String sql = "SELECT e FROM Employees e WHERE e.isDisengaged=0";
            final List<Employees> employees  = DataAccess.searchEmployees(sql);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    final ProgressHandle ph = ProgressHandleFactory.createHandle("Posting Payroll Transactions");
                    ph.start();
                    Long start = System.currentTimeMillis();
                    for(int i=0; i<employees.size();i++){
                        final Employees e = employees.get(i);
                        int j = i+1;
                        StatusDisplayer.getDefault().setStatusText(j+"/"+employees.size()+" "+ e.getSurName()+" "+e.getOtherNames());
                        ph.progress(e.getSurName()+" "+e.getOtherNames());
                        final List<TblEmployeeTransactions> transactions = DataAccess.getEmployeeTransactions(e.getEmployeeID(), period.getPeriodYear(), DataAccess.covertMonthsToInt(period.getPeriodMonth()));
                        //Runnable employeeTask = new Runnable() {
                            //@Override
                            //public void run() {
                                //final ProgressHandle ph = ProgressHandleFactory.createHandle(e.getSurName()+" "+e.getSurName());
                                for(TblEmployeeTransactions t :transactions){
                                    ph.progress("Posting Fixed Transactions "+t.getPayrollCodeID().getPayrollCodeName()+" "+e.getSurName()+"--"+e.getOtherNames());
                                    DataAccess.savePayrollTransaction(t);
                                    
                                }
     
                        ph.progress("Variable Transactions For "+e.getSurName()+" "+e.getOtherNames());
                        
                        for(TblEmployeePayrollCode code: e.getTblEmployeePayrollCodeCollection()){
                            ph.progress(code.getPayrollCodeID().getPayrollCodeName());
                            DataAccess.postPayrollTransaction(e, code.getPayrollCodeID(), period);
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
    
    
    
    
    
    void closePeriod(){
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("Close Period "+ period.getPeriodMonth()+" "+ period.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            UtilityPLR.closePeriod(period);
            UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPeriod()), null);
            
        }
        
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPeriod> rslt = (Lookup.Result<NodeRefreshPeriod>)ev.getSource();
        for(NodeRefreshPeriod nrp: rslt.allInstances()){
            TblPeriods p = DataAccess.entityManager.find(TblPeriods.class, period.getPeriodID());
            if(p.getStatus().equalsIgnoreCase("Closed"))
                setIconBaseWithExtension("systems/tech247/util/icons/yearCalendar.png");
            else
                setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
            
        }
    }
    
    

    
    
    
    
}
