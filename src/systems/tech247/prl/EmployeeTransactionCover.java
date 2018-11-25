/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import systems.tech247.hr.TblEmployeeTransactions;

/**
 *
 * @author Admin
 */
public class EmployeeTransactionCover {
    private TblEmployeeTransactions transaction;
    private String amount;
    NumberFormat nf = new DecimalFormat("#,###.00");
    private String category;
    private String name;
    
    public EmployeeTransactionCover(TblEmployeeTransactions t){
        transaction = t;
        amount = nf.format(t.getAmount());
        name = t.getPayrollCodeID().getPayrollCodeName();
    }

    public TblEmployeeTransactions getTransaction() {
        return transaction;
    }
    
    

    
    
 
    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    
}
