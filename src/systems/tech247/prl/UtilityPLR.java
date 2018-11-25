/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Query;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import static systems.tech247.dbaccess.DataAccess.entityManager;
import systems.tech247.hr.HrsUsers;
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
    
     public static void closePeriod(TblPeriods period){
        
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
        Lookup.Result<HrsUsers> rslt = (Lookup.Result<HrsUsers>)ev.getSource();
        for(HrsUsers u:rslt.allInstances()){
            user=u;
        }
    }
    
    
    
    
    
}
