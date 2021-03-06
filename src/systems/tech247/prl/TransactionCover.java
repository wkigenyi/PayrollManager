/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import systems.tech247.hr.TblPeriodTransactions;

/**
 *
 * @author Admin
 */
public class TransactionCover {
    private TblPeriodTransactions transaction;
    private String amount;
    NumberFormat nf = new DecimalFormat("#,###.00");
    private String category;
    
    public TransactionCover(TblPeriodTransactions t){
        transaction = t;
        amount = nf.format(t.getAmount());
        category = t.getPGroup();
    }

    /**
     * @return the transaction
     */
    public TblPeriodTransactions getTransaction() {
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

    
    
}
