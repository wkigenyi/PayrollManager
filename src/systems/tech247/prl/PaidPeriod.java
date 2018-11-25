/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.List;
import systems.tech247.hr.TblPeriodTransactions;



import systems.tech247.util.EMP;
import systems.tech247.util.Month;

/**
 *
 * @author WKigenyi
 */
public class PaidPeriod {
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
    List<TblPeriodTransactions> transactions;
    
    QueryPayrollTransaction query;
    
    public PaidPeriod(EMP emp, Month month){
        this.emp = emp;
        this.month = month;
        this.empID = emp.getEm().getEmployeeID();
        this.monthNo = month.getMonth();
        
        this.selected = Boolean.FALSE;
        
        
        transactions = query.getList();
        
        
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
        return month.getYr();
    }

    /**
     * @param yr the yr to set
     */
    public void setYr(int yr) {
        this.yr = yr;
    }

    /**
     * @return the basicPay
     */
    public String getBasicPay() {
        String pay = "";
        NumberFormat nf = new DecimalFormat("#,###.00");
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(2)))){
                pay = nf.format(t.getAmount());
            }
        }
        return pay;
        
    }

    /**
     * @param basicPay the basicPay to set
     */
    public void setBasicPay(String basicPay) {
        this.basicPay = basicPay;
    }

    /**
     * @return the hAllowance
     */
    public String gethAllowance() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(3)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param hAllowance the hAllowance to set
     */
    public void sethAllowance(String hAllowance) {
        this.hAllowance = hAllowance;
    }

    /**
     * @return the serviceCharge
     */
    public String getServiceCharge() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(21)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param serviceCharge the serviceCharge to set
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * @return the taxablePay
     */
    public String getTaxablePay() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(5)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param taxablePay the taxablePay to set
     */
    public void setTaxablePay(String taxablePay) {
        this.taxablePay = taxablePay;
    }

    /**
     * @return the taxCharged
     */
    public String getTaxCharged() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(6)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param taxCharged the taxCharged to set
     */
    public void setTaxCharged(String taxCharged) {
        this.taxCharged = taxCharged;
    }

    /**
     * @return the PAYE
     */
    public String getPAYE() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(7)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param PAYE the PAYE to set
     */
    public void setPAYE(String PAYE) {
        this.PAYE = PAYE;
    }

    /**
     * @return the NHIF
     */
    public String getNHIF() {
        return NHIF;
    }

    /**
     * @param NHIF the NHIF to set
     */
    public void setNHIF(String NHIF) {
        this.NHIF = NHIF;
    }

    /**
     * @return the NSSF
     */
    public String getNSSF() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(9)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param NSSF the NSSF to set
     */
    public void setNSSF(String NSSF) {
        this.NSSF = NSSF;
    }

    /**
     * @return the cityLegder
     */
    public String getCityLedger() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(26)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param cityLegder the cityLegder to set
     */
    public void setCityLegder(String cityLegder) {
        this.cityLegder = cityLegder;
    }

    /**
     * @return the rambiRambi
     */
    public String getRambiRambi() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(35)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param rambiRambi the rambiRambi to set
     */
    public void setRambiRambi(String rambiRambi) {
        this.rambiRambi = rambiRambi;
    }

    /**
     * @return the lvsrSACCO
     */
    public String getLvsrSACCO() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(38)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param lvsrSACCO the lvsrSACCO to set
     */
    public void setLvsrSACCO(String lvsrSACCO) {
        this.lvsrSACCO = lvsrSACCO;
    }

    /**
     * @return the transportAllowance
     */
    public String getTransportAllowance() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(32)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @param transportAllowance the transportAllowance to set
     */
    public void setTransportAllowance(String transportAllowance) {
        this.transportAllowance = transportAllowance;
    }

    /**
     * @return the selectable
     */
    public Boolean getSelected() {
        return this.selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the netPay
     */
    public String getNetPay() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(10)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @return the outOfStation
     */
    public String getOutOfStation() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(20)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @return the grossPay
     */
    public String getGrossPay() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(1)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }

    /**
     * @return the advance
     */
    public String getAdvance() {
        String pay = "";
        
        for (TblPeriodTransactions t : transactions){
            if(t.getPayrollCodeID().getPayrollCodeID().equals(new BigInteger(Integer.toString(24)))){
                pay = t.getAmount().toString();
            }
        }
        return pay;
    }
    
    
}
