/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.hr.TblPayroll;

/**
 *
 * @author Admin
 */
public class PayrollCover {
    private TblPayroll tier;
    private Boolean suspendPosting;
    private String name;
    
    
    
    public PayrollCover(TblPayroll t){
        tier = t;
        
        suspendPosting = t.getSuspendTransactionPosting();
        name = t.getPayrollName();
        
        
        
    }

    public TblPayroll getTier() {
        return tier;
    }
    

    
    
 
    

   

    

    

    /**
     * @return the suspendPosting
     */
    public Boolean getSuspendPosting() {
        return suspendPosting;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    
}
