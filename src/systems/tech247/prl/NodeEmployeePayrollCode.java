/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblEmployeePayrollCode;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author Admin
 */
public class NodeEmployeePayrollCode extends  AbstractNode implements LookupListener{
    
    private final InstanceContent instanceContent;
    private TblEmployeePayrollCode code;
    Lookup.Result<NodeRefreshEmployeePayrollCode> rslt=UtilityPLR.getInstance().lookup.lookupResult(NodeRefreshEmployeePayrollCode.class);
    
    
    public NodeEmployeePayrollCode(TblEmployeePayrollCode p) throws IntrospectionException {
        this(p,new InstanceContent());
    }
    
    private NodeEmployeePayrollCode(TblEmployeePayrollCode p, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF, new AbstractLookup(ic));
        code = p;
        instanceContent = ic;
        instanceContent.add(p);
        
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new EmployeePayrollCodeEditorTopComponent(null, code);
                tc.open();
                tc.requestActive();
            }
        });
        
        
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                TblPayrollCode codes = DataAccess.entityManager.find(TblPayrollCode.class, code.getPayrollCodeID());
                try{
                    DataAccess.entityManager.getTransaction().begin();
                    DataAccess.entityManager.remove(codes);
                    DataAccess.entityManager.getTransaction().commit();
                    UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshEmployeePayrollCode()), null);
                }catch(NullPointerException ex){
                    NotifyUtil.error("This Code has associations", "Cannot be deleted", ex, true);
                }
            }
        });
        rslt.addLookupListener(this);
        setIconBaseWithExtension("systems/tech247/util/icons/document.png");
        setDisplayName(p.getPayrollCodeID().getPayrollCodeName());
    }

    @Override
    public Action getPreferredAction() {
        Action doNothing = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent editor = new EmployeePayrollCodeEditorTopComponent(null,code);
                editor.open();
                editor.requestActive();
            }
        };
        return doNothing;
        //return super.getPreferredAction(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    @Override 
    protected Sheet createSheet(){
        final TblEmployeePayrollCode bean = getLookup().lookup(TblEmployeePayrollCode.class);
        Sheet basicPCode = super.createSheet();
        
        Sheet.Set set = Sheet.createExpertSet();
        set.setName("details");
        
        set.setDisplayName("Details");
        final NumberFormat nf = new DecimalFormat("#,###.00");
        
        try{
            
            
            Property code;
            code = new PropertySupport("code", String.class, "Code Name", "Code", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getPayrollCodeID().getPayrollCodeName();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property activeProperty;
            activeProperty = new PropertySupport("active", Boolean.class, "Active", "Is this Code Active", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getActive();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property amount;
            amount = new PropertySupport("amount", String.class, "Amount", "Amount", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getAmount());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property deduction;
            deduction = new PropertySupport("deduction", Boolean.class, "Deduction", "Deduction", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getPayrollCodeID().getDeduction();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property currency = new PropertySupport("currency", String.class, "Currency", "Currency", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCurrencyID().getCurrencyCode();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            

            
            
            
           
            

            
           
            
            
            
            
            
            
            
            
            set.put(code);
            set.put(deduction);
            set.put(amount);
            set.put(activeProperty);
            set.put(currency);
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
            Logger.getLogger(NodeEmployeePayrollCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        basicPCode.put(set);
        
        return basicPCode;
        
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshEmployeePayrollCode> rslt = (Lookup.Result<NodeRefreshEmployeePayrollCode>)ev.getSource();
        for(NodeRefreshEmployeePayrollCode e:rslt.allInstances()){
            TblEmployeePayrollCode codes = DataAccess.entityManager.find(TblEmployeePayrollCode.class, code.getPayrollCodeID());
            try{
            setDisplayName(codes.getPayrollCodeID().getPayrollCodeName());
            }catch(NullPointerException ex){
                
            }
        }    
    }
    
    
    
}
