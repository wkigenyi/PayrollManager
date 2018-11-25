/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.SimpleDateFormat;
import systems.tech247.hr.TblPeriods;

/**
 *
 * @author Admin
 */
public class PeriodCover {
    private TblPeriods period;
    private String periodname;
    private String openDate;
    private String closeDate;
    private String status;
    private String closedBy;
    private String openedby;
    private String nextPeriod;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
    
    public PeriodCover(TblPeriods p){
        period = p;
        periodname = period.getPeriodYear()+" "+period.getPeriodMonth();
        openDate = sdf.format(period.getOpenDate());
        closeDate=sdf.format(period.getCloseDate());
        status = period.getStatus();
        try{
        openedby = period.getOpenEmpID().getSurName()+" "+period.getOpenEmpID().getOtherNames();
        }catch(Exception ex){
            
        }
        try{
        closedBy = period.getCloseEmpID().getSurName()+" "+period.getCloseEmpID().getOtherNames();
        }catch(Exception ex){
            
        }
        
        TblPeriods per = UtilityPLR.getNextPeriod(period);
        nextPeriod = per.getPeriodMonth()+" "+per.getPeriodYear();
        
    }

    /**
     * @return the period
     */
    public TblPeriods getPeriod() {
        return period;
    }

    /**
     * @return the periodname
     */
    public String getPeriodname() {
        return periodname;
    }

    /**
     * @return the openDate
     */
    public String getOpenDate() {
        return openDate;
    }

    /**
     * @return the closeDate
     */
    public String getCloseDate() {
        return closeDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the closedBy
     */
    public String getClosedBy() {
        return closedBy;
    }

    /**
     * @param closedBy the closedBy to set
     */
    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    /**
     * @return the openedby
     */
    public String getOpenedby() {
        return openedby;
    }

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @return the nextPeriod
     */
    public String getNextPeriod() {
        return nextPeriod;
    }

    

 
    

    

    
    
}
