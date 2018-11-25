/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.util.CapEditable;


/**
 *
 * @author WKigenyi
 */
public class NodeCostCenter  extends BeanNode<CostCenterCover>{
    
    private final InstanceContent instanceContent;
    
    public NodeCostCenter(CostCenterCover bean) throws IntrospectionException{
        this(bean,new InstanceContent());
    }
    
    private NodeCostCenter(CostCenterCover bean, InstanceContent ic) throws IntrospectionException{
        super(bean,Children.LEAF,new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(bean);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        setDisplayName(bean.getDescription());
        setIconBaseWithExtension("systems/tech247/util/icons/company.png");
        
        
    }
    
    @Override 
    protected Sheet createSheet(){
        final CostCenterCover bean = getLookup().lookup(CostCenterCover.class);
        Sheet basicCostCenter = super.createSheet();
        Sheet.Set set = Sheet.createExpertSet();
        set.setDisplayName("Details");
        
        try{
            Property codeProperty;
            codeProperty = new PropertySupport.ReadOnly<String>("code", String.class, "Code", "Code") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCode();
                }
                
                
            };
            
            Property nameProperty;
            nameProperty = new PropertySupport.ReadOnly<String>("name", String.class, "Description", "Description") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getDescription();
                }
                
                
            };
            
            Property companyProperty;
            companyProperty = new PropertySupport.ReadOnly<String>("company", String.class, "Company Name", "Company Name") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                      return bean.getCompany();
                }
            };
            
            Property empnoProperty;
            
            empnoProperty = new PropertySupport.ReadOnly<String>("empno", String.class, "Employee Number", "Number Of Employees in this Cost Center") {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getEmpNo();
                }
            };
            
            
            
            
            
            
            set.put(codeProperty);
            set.put(nameProperty);
            set.put(companyProperty);
            set.put(empnoProperty);
            
        }catch(Exception ex){
            
        }
        
        
        basicCostCenter.put(set);
        return basicCostCenter;
        
    }
    
    
    
}
