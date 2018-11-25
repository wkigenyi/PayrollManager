/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.hr.TblPayrollCode;

/**
 *
 * @author Wilfred
 */
public class PayrollCodeSelectable {
    
    Boolean selected;
    TblPayrollCode code;
    

    public PayrollCodeSelectable(Boolean selected, TblPayrollCode code) {
        this.selected = selected;
        this.code = code;
    }

    public Boolean getSelected() {
        return selected;
    }

    public TblPayrollCode getCode() {
        return code;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    
    
}
