/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import systems.tech247.hr.TblPayrollCode;

/**
 *
 * @author Admin
 */
public class PayrollCodeCover {
    private TblPayrollCode txCode;
    private String active;
    private Boolean cashBased;
    private String debit;
    private Boolean deduction;
    private Boolean codeOnPaySlip;
    private Boolean valueOnPayslip;
    private String factor;
    private Boolean fixed;
    private String fringeBenefit;
    private String increasing;
    private String increasingBalanceLimit;
    private String intrestRate;
    Boolean amount;
    private Boolean loan;
    private Boolean pension;
    private Boolean statement;
    private Boolean journalEntry;
    private String limit;
    private String noBalance;
    private String p9Col;
    private String groupType;
    private Boolean payment;
    private Boolean processInPayroll;
    private String recoveredFrom;
    private String reducing;
    private String resultCode;
    private String rounding;
    private Boolean balanceInPayslip;
    private Boolean taxable;
    private Boolean varied;
    String name;
    private String code;
    private String formular;
    private NumberFormat nf = new DecimalFormat("#,###.00");
    
    
    public PayrollCodeCover(TblPayrollCode t){
        txCode = t;
        code = txCode.getPayrollCodeCode();
        name = txCode.getPayrollCodeName();
        formular= txCode.getPayrollCodeFormula();
                active=txCode.getActive()+"";
                cashBased=txCode.getCashBased();
                        debit=txCode.getDebit()+"";
                        deduction=txCode.getDeduction();
                        codeOnPaySlip=txCode.getDisplayPostedPayrollCodeInfoOnPaySlip();
                        valueOnPayslip=txCode.getDisplayPostedPayrollCodeValueOnPaySlip();
                      try{
                          factor=nf.format(  txCode.getFactor());
                      }catch(Exception ex){
                          
                      }
                      
                      fixed=txCode.getFixed();
                      fringeBenefit=txCode.getFringeBenefit();
                      try{
                      increasing=nf.format(txCode.getIncreasing());
                      }catch(Exception ex){
                          
                      }
                      try{
                      increasingBalanceLimit=nf.format(txCode.getIncreasingBalanceLimit());
                      }catch(Exception ex){
                          
                      }
                      try{
                      intrestRate=nf.format(txCode.getInterestRate());
                      }catch(Exception ex){
                          
                      }
                      amount=txCode.getIsAmount();
                      loan=txCode.getIsLoan();
                      pension=txCode.getIsPension();
                      statement=txCode.getIsStatement();
    }

    
    
 
    

   

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    

    /**
     * @param txCode the txCode to set
     */
    public void setTier(TblPayrollCode txCode) {
        this.txCode = txCode;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @return the cashBased
     */
    public Boolean getCashBased() {
        return cashBased;
    }

    /**
     * @return the debit
     */
    public String getDebit() {
        return debit;
    }

    /**
     * @return the deduction
     */
    public Boolean getDeduction() {
        return deduction;
    }

    /**
     * @return the codeOnPaySlip
     */
    public Boolean getCodeOnPaySlip() {
        return codeOnPaySlip;
    }

    /**
     * @return the valueOnPayslip
     */
    public Boolean getValueOnPayslip() {
        return valueOnPayslip;
    }

    /**
     * @return the factor
     */
    public String getFactor() {
        return factor;
    }

    /**
     * @return the fixed
     */
    public Boolean getFixed() {
        return fixed;
    }

    /**
     * @return the fringeBenefit
     */
    public String getFringeBenefit() {
        return fringeBenefit;
    }

    /**
     * @return the increasing
     */
    public String getIncreasing() {
        return increasing;
    }

    /**
     * @return the increasingBalanceLimit
     */
    public String getIncreasingBalanceLimit() {
        return increasingBalanceLimit;
    }

    /**
     * @return the intrestRate
     */
    public String getIntrestRate() {
        return intrestRate;
    }

    /**
     * @return the loan
     */
    public Boolean getLoan() {
        return loan;
    }

    /**
     * @return the pension
     */
    public Boolean getPension() {
        return pension;
    }

    /**
     * @return the statement
     */
    public Boolean getStatement() {
        return statement;
    }

    /**
     * @return the journalEntry
     */
    public Boolean getJournalEntry() {
        return journalEntry;
    }

    /**
     * @return the limit
     */
    public String getLimit() {
        return limit;
    }

    /**
     * @return the noBalance
     */
    public String getNoBalance() {
        return noBalance;
    }

    /**
     * @return the p9Col
     */
    public String getP9Col() {
        return p9Col;
    }

    /**
     * @return the groupType
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     * @return the payment
     */
    public Boolean getPayment() {
        return payment;
    }

    /**
     * @return the processInPayroll
     */
    public Boolean getProcessInPayroll() {
        return processInPayroll;
    }

    /**
     * @return the recoveredFrom
     */
    public String getRecoveredFrom() {
        return recoveredFrom;
    }

    /**
     * @return the reducing
     */
    public String getReducing() {
        return reducing;
    }

    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @return the rounding
     */
    public String getRounding() {
        return rounding;
    }

    /**
     * @return the balanceInPayslip
     */
    public Boolean getBalanceInPayslip() {
        return balanceInPayslip;
    }

    /**
     * @return the taxable
     */
    public Boolean getTaxable() {
        return taxable;
    }

    /**
     * @return the varied
     */
    public Boolean getVaried() {
        return varied;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the formular
     */
    public String getFormular() {
        return formular;
    }

    /**
     * @return the nf
     */
    public NumberFormat getNf() {
        return nf;
    }

    /**
     * @return the txCode
     */
    public TblPayrollCode getTxCode() {
        return txCode;
    }

    
    
}
