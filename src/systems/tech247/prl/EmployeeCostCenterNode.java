/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.hr.TblEmployeeCostCenter;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class EmployeeCostCenterNode extends  BeanNode<TblEmployeeCostCenter>{
    
    private final InstanceContent instanceContent;
    
    public EmployeeCostCenterNode(TblEmployeeCostCenter trans) throws IntrospectionException {
        this(trans,new InstanceContent());
    }
    
    private EmployeeCostCenterNode(TblEmployeeCostCenter trans, InstanceContent ic) throws IntrospectionException{
        super(trans,Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(trans);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                //We shall edit you
            }
        });
        
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                //We can Delete
            }
        });
        
        setDisplayName(trans.getTblCostCenter().getDescription());
        setIconBaseWithExtension("systems/tech247/util/icons/company.png");
        
    }
    
    @Override 
    protected Sheet createSheet(){
        final TblEmployeeCostCenter bean = getLookup().lookup(TblEmployeeCostCenter.class);
        Sheet basic = super.createSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName("Details");
        final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final NumberFormat nf = new DecimalFormat("##.##%");
        
        try{
            Property rateProperty;
            rateProperty = new PropertySupport.ReadOnly<String>("rate", String.class, "Percentage", "Percentage") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(nf.format(bean.getPercentage()));
                }
                
                
            };
            
            Property dateProperty;
            dateProperty = new PropertySupport.ReadOnly<String>("date", String.class, "Date Entered", "Date Entered") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return df.format(bean.getDateEntered());
                }
                
                
            };
            
            
            
            
            
            
            set.put(rateProperty);
            set.put(dateProperty);
        }catch(Exception ex){
            
        }
        
        
        basic.put(set);
        return basic;
        
    }
    
}
