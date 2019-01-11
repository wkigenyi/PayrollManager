/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPeriods;


/**
 *
 * @author WKigenyi
 */
public class NodePayrollPeriod  extends AbstractNode implements LookupListener{
    
    InstanceContent content;
    TblPeriods period;
    Lookup.Result<NodeRefreshPeriod> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPeriod.class);
    public NodePayrollPeriod(PeriodCover bean) throws IntrospectionException{
        this(bean, new InstanceContent());
    }
    
    
    public NodePayrollPeriod(PeriodCover bean, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF,new AbstractLookup(ic));
        content = ic;
        period = bean.getPeriod();

        
        //this.ic.add(bean);
        period = bean.getPeriod();
        content.add(period);
        content.add(bean);
        setDisplayName(bean.getPeriodname());
        if(period.getStatus().equalsIgnoreCase("Closed"))
        setIconBaseWithExtension("systems/tech247/util/icons/yearCalendar.png");
        else
        setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");    
    }
    
    @Override 
    protected Sheet createSheet(){
        final PeriodCover bean = getLookup().lookup(PeriodCover.class);
        Sheet basic = super.createSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName("Details");
        
        try{
            Property opendateProperty;
            opendateProperty = new PropertySupport("opendate", String.class, "Opened Date", "Date when the period opened", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getOpenDate();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property closedateProperty;
            closedateProperty = new PropertySupport("closeddate", String.class, "Closed Date", "Date when the period Closed", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCloseDate();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property openbyProperty;
            openbyProperty = new PropertySupport("openby", String.class, "Opened By", "Employee That Opened the Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getOpenedby();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property closedbyProperty;
            closedbyProperty = new PropertySupport("closedby", String.class, "Closed By", "Employee That Closed the Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getClosedBy();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property nextPeriodProperty;
            nextPeriodProperty = new PropertySupport("nextperiod", String.class, "Next Period", "Next Period", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getNextPeriod();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            Property status;
            status = new PropertySupport("status", String.class, "Status", "Status", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getStatus();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            
            
            
            
            set.put(status);
            set.put(opendateProperty);
            set.put(closedateProperty);
            set.put(openbyProperty);
            set.put(closedbyProperty);
            set.put(nextPeriodProperty);
        }catch(Exception ex){
            
        }
        
        
        basic.put(set);
        return basic;
        
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPeriod> rslt = (Lookup.Result<NodeRefreshPeriod>)ev.getSource();
        for(NodeRefreshPeriod nrp: rslt.allInstances()){
            TblPeriods p = DataAccess.entityManager.find(TblPeriods.class, period.getPeriodID());
            if(p.getStatus().equalsIgnoreCase("Closed"))
                setIconBaseWithExtension("systems/tech247/util/icons/yearCalendar.png");
            else
                setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
            
        }
    }
    
    

    
    
    
    
}
