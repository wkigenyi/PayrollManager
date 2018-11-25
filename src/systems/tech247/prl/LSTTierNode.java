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
import java.util.Arrays;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PrlMonthlyLocalTaxTableTiers;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;


/**
 *
 * @author WKigenyi
 */
public class LSTTierNode  extends AbstractNode{
    
    private final InstanceContent instanceContent;
    
    public LSTTierNode(PrlMonthlyLocalTaxTableTiers bean) throws IntrospectionException{
        this(bean,new InstanceContent());
    }
    
    private LSTTierNode(final PrlMonthlyLocalTaxTableTiers bean, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF,new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(bean);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new LSTTierEditorTopComponent(bean);
                tc.open();
                tc.requestActive();
            }
        });
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                PrlMonthlyLocalTaxTableTiers tier = DataAccess.entityManager.find(PrlMonthlyLocalTaxTableTiers.class, bean.getMonthlyLocalTaxTableTiersID());
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.remove(tier);
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshLST()), null);
            }
        });
        
        setDisplayName(bean.getTierLevel()+"");
        setIconBaseWithExtension("systems/tech247/util/icons/checklist.png");
    }
    
    @Override 
    protected Sheet createSheet(){
        final PrlMonthlyLocalTaxTableTiers bean = getLookup().lookup(PrlMonthlyLocalTaxTableTiers.class);
        Sheet sheetPaye = super.createSheet();
        Sheet.Set set = Sheet.createExpertSet();
        set.setDisplayName("Details");
        final NumberFormat nf = new DecimalFormat("#,###.00");
        
        try{
            Property lower;
            lower = new PropertySupport("lower", String.class, "Lower Boundary", "Higher Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getLowerBoundary());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property upper;
            upper = new PropertySupport("upper", String.class, "Upper Boundary", "Upper Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getLowerBoundary());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            
            
            
            
            
            Property rateProperty;
            rateProperty = new PropertySupport("rate", String.class, "Rate", "Tax Charged in this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getTierRate());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property upperProperty;
            upperProperty = new PropertySupport("upper", String.class, "Upper Boundary", "Higher Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    try{
                        return nf.format(bean.getUpperBoundary());
                    }catch(NullPointerException ex){
                        return "Not Set";
                    }
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            
            
            
            
            

            set.put(upperProperty);
            set.put(lower);
            set.put(rateProperty);
        }catch(Exception ex){
            
        }
        
        
        sheetPaye.put(set);
        return sheetPaye;
        
    }
    
    
    
}
