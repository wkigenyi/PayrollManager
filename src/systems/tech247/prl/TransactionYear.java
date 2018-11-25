/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;


import systems.tech247.util.EMP;
import systems.tech247.util.Year;

/**
 *
 * @author WKigenyi
 */
public class TransactionYear {
    private EMP emp;
    private Year yr;
    private Boolean selected;
    
    public TransactionYear(EMP emp,Year yr){
        this.emp = emp;
        this.yr = yr;
    }

    /**
     * @return the emp
     */
    public EMP getEmp() {
        return emp;
    }

    /**
     * @param emp the emp to set
     */
    public void setEmp(EMP emp) {
        this.emp = emp;
    }

    /**
     * @return the yr
     */
    public Year getYr() {
        return yr;
    }

    /**
     * @param yr the yr to set
     */
    public void setYr(Year yr) {
        this.yr = yr;
    }

    /**
     * @return the selectable
     */
    public Boolean getSelected() {
        return false;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelectable(Boolean selected) {
        this.selected = selected;
    }
    
}
