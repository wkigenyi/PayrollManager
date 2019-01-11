/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.util.Comparator;
import systems.tech247.hr.TblPeriodTransactions;

/**
 *
 * @author Wilfred
 */
public class PayrollCodeGroupComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        String code1 = ((TblPeriodTransactions)o1).getPayrollCodeID().getPayrollCodeCode();
        String code2 = ((TblPeriodTransactions)o2).getPayrollCodeID().getPayrollCodeCode();
        return code1.compareTo(code2);
    }
    
}
