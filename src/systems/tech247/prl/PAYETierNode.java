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
import systems.tech247.hr.PrlMonthlyPAYETableTiers;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;


/**
 *
 * @author WKigenyi
 */
public class PAYETierNode  extends AbstractNode{
    
    private final InstanceContent instanceContent;
    
    public PAYETierNode(PrlMonthlyPAYETableTiers bean) throws IntrospectionException{
        this(bean,new InstanceContent());
    }
    
    private PAYETierNode(final PrlMonthlyPAYETableTiers bean, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF,new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(bean);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new PAYETierEditorTopComponent(bean);
                tc.open();
                tc.requestActive();
            }
        });
        instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                PrlMonthlyPAYETableTiers tier = DataAccess.entityManager.find(PrlMonthlyPAYETableTiers.class, bean.getMonthlyPAYETableTiersID());
                DataAccess.entityManager.getTransaction().begin();
                DataAccess.entityManager.remove(tier);
                DataAccess.entityManager.getTransaction().commit();
                UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPAYE()), null);
            }
        });
        
        setDisplayName("Tier "+bean.getTierLevel());
        setIconBaseWithExtension("systems/tech247/util/icons/checklist.png");
    }
    
    @Override 
    protected Sheet createSheet(){
        final PrlMonthlyPAYETableTiers bean = getLookup().lookup(PrlMonthlyPAYETableTiers.class);
        final NumberFormat nf = new DecimalFormat("#,###.00");
        Sheet sheetPaye = super.createSheet();
        Sheet.Set set = Sheet.createExpertSet();
        set.setDisplayName("Details");
        
        try{
            Property amountProperty;
            amountProperty = new PropertySupport("lower", String.class, "Lower Boundary", "Higher Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getLowerBoundry());
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
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property upperProperty;
            upperProperty = new PropertySupport("upper", String.class, "Upper Boundary", "Higher Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getUpperBoundary());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property standingProperty;
            standingProperty = new PropertySupport("standing", String.class, "Standing Amount", "Higher Boundary Of this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getStandingAmount());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property additionalProperty;
            additionalProperty = new PropertySupport("additional", String.class, "Additional Rate", "Tax Charged in this tier", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return nf.format(bean.getAdditionalRate());
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            
            set.put(additionalProperty);
            set.put(standingProperty);
            set.put(upperProperty);
            set.put(amountProperty);
            set.put(rateProperty);
        }catch(Exception ex){
            
        }
        
        
        sheetPaye.put(set);
        return sheetPaye;
        
    }
    
    
    
}
