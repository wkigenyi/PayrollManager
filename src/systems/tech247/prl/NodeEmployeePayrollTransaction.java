/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.hr.TblPeriodTransactions;

/**
 *
 * @author Admin
 */
public class NodeEmployeePayrollTransaction extends  AbstractNode{
    
    private final InstanceContent instanceContent;
    
    public NodeEmployeePayrollTransaction(TblPeriodTransactions trans) {
        this(trans,new InstanceContent());
    }
    
    private NodeEmployeePayrollTransaction(TblPeriodTransactions trans, InstanceContent ic){
        super(Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(trans);
        
        
        setDisplayName(trans.getPayrollCodeID().getPayrollCodeName());
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Set set = Sheet.createPropertiesSet();
        final NumberFormat nf = new DecimalFormat("#,###.00");
                
        
        final TblPeriodTransactions trans = getLookup().lookup(TblPeriodTransactions.class);
        
        Property amount;
        amount = new PropertySupport("amount", String.class, "Amount", "Amount", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return nf.format(trans.getAmount());
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        Property deduction = new PropertySupport("deduction", Boolean.TYPE, "Deduction", "Deduction", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return trans.getPayrollCodeID().getDeduction();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        Property category = new PropertySupport("category", String.class, "Category", "Category", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return trans.getPayrollCodeID().getPCodeGroupID().getPCodeGroupName();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        Property currency = new PropertySupport("currency", String.class, "Currency", "Currency", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return trans.getCurrencyID().getCurrencyCode();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Property rate = new PropertySupport("rate", String.class, "Rate", "Rate", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return nf.format(trans.getConversionRate());
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Property converted = new PropertySupport("converted", String.class, "Converted", "Converted", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return nf.format(trans.getConvertedAmount());
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(converted);
        set.put(rate);
        set.put(currency);
        set.put(category);
        set.put(deduction);
        set.put(amount);
        
        sheet.put(set);
        return sheet;
    }
    
    
    
    
}
