/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.math.BigInteger;

import java.util.List;




import systems.tech247.util.EMP;
import systems.tech247.util.Month;

/**
 *
 * @author WKigenyi
 */
public class MonthPaySlip {
    private EMP emp;
    private Month month;
    private String employeeName;
    private String employeeNumber;
    private String pinNumber;
    private String payMonth;
    private int yr;
    private String transportAllowance;
    private String basicPay;
    private String hAllowance;
    private String serviceCharge;
    private String taxablePay;
    private String taxCharged;
    private String PAYE;
    private String NHIF;
    private String NSSF;
    private String cityLegder;
    private String rambiRambi;
    private String lvsrSACCO;
    int empID;
    int monthNo;
    private String netPay;
    private String advance;
    private String outOfStation;
    private String grossPay;
    private Boolean selected;
   
    
    public MonthPaySlip(EMP emp, Month month){
        this.emp = emp;
        this.month = month;
        this.empID = emp.getEm().getEmployeeID();
        this.monthNo = month.getMonth();
        
        this.selected = Boolean.FALSE;
        
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
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return emp.getfName()+" "+emp.getlName();
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the employeeNumber
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * @param employeeNumber the employeeNumber to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * @return the pinNumber
     */
    public String getPinNumber() {
        return pinNumber;
    }

    /**
     * @param pinNumber the pinNumber to set
     */
    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    /**
     * @return the payMonth
     */
    public String getPayMonth() {
        return payMonth;
    }

    /**
     * @param payMonth the payMonth to set
     */
    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    /**
     * @return the yr
     */
    public int getYr() {
        return yr;
    }

    /**
     * @param yr the yr to set
     */
    public void setYr(int yr) {
        this.yr = yr;
    }

    

   
    

    

    
    

    
    
    
}
