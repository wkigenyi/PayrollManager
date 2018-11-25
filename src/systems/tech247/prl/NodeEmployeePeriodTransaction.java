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
import org.openide.windows.TopComponent;
import systems.tech247.hr.TblEmployeeTransactions;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class NodeEmployeePeriodTransaction extends  AbstractNode{
    
    private final InstanceContent instanceContent;
    
    public NodeEmployeePeriodTransaction(TblEmployeeTransactions trans) {
        this(trans,new InstanceContent());
    }
    
    private NodeEmployeePeriodTransaction(final TblEmployeeTransactions trans, InstanceContent ic){
        super(Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(trans);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new EmployeeTransactionEditorTopComponent(null, trans);
                tc.open();
                tc.requestActive();
            }
        });
        
        setDisplayName(trans.getPayrollCodeID().getPayrollCodeName());
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Set set = Sheet.createPropertiesSet();
        final NumberFormat nf = new DecimalFormat("#,###.00");
                
        
        final TblEmployeeTransactions trans = getLookup().lookup(TblEmployeeTransactions.class);
        
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
        set.put(category);
        set.put(deduction);
        set.put(amount);
        
        sheet.put(set);
        return sheet;
    }
    
    
    
    
}
