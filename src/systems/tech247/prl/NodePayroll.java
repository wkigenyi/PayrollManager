/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import systems.tech247.hr.TblPayroll;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class NodePayroll extends  AbstractNode{
    
    private final InstanceContent instanceContent;
    private TblPayroll payroll;
    
    public NodePayroll(TblPayroll p) throws IntrospectionException {
        this(p,new InstanceContent());
    }
    
    private NodePayroll(TblPayroll p, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF, new ProxyLookup(new AbstractLookup(ic),Lookups.singleton(p)));
        instanceContent = ic;
        instanceContent.add(p);
        this.payroll = p;
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new PayrollEditorTopComponent(payroll);
                tc.open();
                tc.requestActive();
           }
        });
        
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                //We can Delete
            }
        });
        
        setDisplayName(p.getPayrollName());
        setIconBaseWithExtension("systems/tech247/util/icons/company.png");
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] actions = new Action[]{
            new AbstractAction("Run Payroll") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            }
        };
        return actions; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    @Override
    public Action getPreferredAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc =  new PayrollEditorTopComponent(payroll);
                tc.open();
                tc.requestActive();
            }
        };
    }
    
    
    
    @Override 
    protected Sheet createSheet(){
        final TblPayroll bean = getLookup().lookup(TblPayroll.class);
        Sheet basicPayroll = super.createSheet();
        
        Sheet.Set set = Sheet.createExpertSet();
        set.setName("pdetails");
        
        set.setDisplayName("Details");
        
        try{
            
            Property number = new PropertySupport("number",String.class, "Number Employees", "Number Of Employees", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getEmployeesCollection().size()+"";
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property casual = new PropertySupport("casual", Boolean.class, "Casual", "Casual", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getIsCasual();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };

            
            Property suspend;
            suspend = new PropertySupport("suspend", Boolean.class, "Suspend Posting", "Suspend Posting", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getSuspendTransactionPosting();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            /*Property activeProperty;
            activeProperty = new PropertySupport("active", String.class, "Active", "Is this Code Active", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getActive();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property cashBasedProperty;
            cashBasedProperty = new PropertySupport("cashbased", Boolean.class, "Cash Based", "Is this Code Cash Based", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCashBased();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property debitProperty;
            debitProperty = new PropertySupport("debit", String.class, "Debit", "Is this code a debit?", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getDebit();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property deductionProperty;
            deductionProperty = new PropertySupport("deduction", Boolean.class, "Deduction", "Is this code a Deduction?", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getDeduction();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property infoPSProperty;
            infoPSProperty = new PropertySupport("infops", Boolean.class, "Info On Payslip", "Display Code on Info Payslip?", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCodeOnPaySlip();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property valuePSProperty;
            valuePSProperty = new PropertySupport("valueps", Boolean.class, "Value On Payslip", "Display Code Value on Payslip?", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getValueOnPayslip();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property factorProperty;
            factorProperty = new PropertySupport("factor", String.class, "Factor", "Factor", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getFactor();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property fixedProperty;
            fixedProperty = new PropertySupport("fixed", Boolean.class, "Fixed", "Fixed", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getFixed();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property fringeProperty;
            fringeProperty = new PropertySupport("fringe", String.class, "Fringe Benefit", "Is this a fringe Benefit", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getFringeBenefit();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property increasingProperty;
            increasingProperty = new PropertySupport("increase", String.class, "Increasing", "Increasing", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getIncreasing();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property increasingBalanceLimitProperty;
            increasingBalanceLimitProperty = new PropertySupport("increaseBal", String.class, "Increasing Balance Limit", "Increasing", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getIncreasingBalanceLimit();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            */
            set.put(number);
            set.put(casual);
            set.put(suspend);
            /*set.put(activeProperty);
            
            set.put(formularProperty);
            set.put(cashBasedProperty);
            set.put(debitProperty);
            set.put(deductionProperty);
            set.put(infoPSProperty);
            set.put(valuePSProperty);
            set.put(factorProperty);
            set.put(fixedProperty);
            set.put(fringeProperty);
            set.put(increasingProperty);
            set.put(increasingBalanceLimitProperty);*/
            
            
        }catch(Exception ex){
            Logger.getLogger(NodePayrollCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        basicPayroll.put(set);
        
        return basicPayroll;
        
    }
    
}
