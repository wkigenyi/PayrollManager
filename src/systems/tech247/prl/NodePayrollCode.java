/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import systems.tech247.dbaccess.BooleanEditor;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author Admin
 */
public class NodePayrollCode extends  AbstractNode implements LookupListener{
    
    private final InstanceContent instanceContent;
    private PayrollCodeSelectable code;
    Boolean edit;
    List<TblPayrollCode> list = new ArrayList();
    Lookup.Result<NodeRefreshPayrollCode> rslt=UtilityPLR.getInstance().lookup.lookupResult(NodeRefreshPayrollCode.class);
    
    
    public NodePayrollCode(PayrollCodeSelectable p) throws IntrospectionException {
        this(p,new InstanceContent(),false);
    }
    
    public NodePayrollCode(PayrollCodeSelectable p,Boolean edit) throws IntrospectionException {
        
        this(p,new InstanceContent(),edit);
    }
    
    
    
    
    private NodePayrollCode(PayrollCodeSelectable p, InstanceContent ic,Boolean edit) throws IntrospectionException{
        super(Children.LEAF, new AbstractLookup(ic));
        code = p;
        list.add(code.getCode());
        instanceContent = ic;
        
        instanceContent.add(p);
        ic.add(p.getCode());
                
        this.edit = edit;
        if(edit){
            instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new PayrollCodeEditorTopComponent(code.getCode());
                tc.open();
                tc.requestActive();
            }
            });
            
            instanceContent.add(new CapDeletable() {
            @Override
            public void delete() {
                TblPayrollCode codes = DataAccess.entityManager.find(TblPayrollCode.class, code.getCode().getPayrollCodeID());
                try{
                    DataAccess.entityManager.getTransaction().begin();
                    DataAccess.entityManager.remove(codes);
                    DataAccess.entityManager.getTransaction().commit();
                    UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPayrollCode()), null);
                }catch(Exception ex){
                    NotifyUtil.error("This Code has related entries", "This code be deleted", ex, false);
                }
            }
            });
        }
        
        
        
        rslt.addLookupListener(this);
        setIconBaseWithExtension("systems/tech247/util/icons/document.png");
        setDisplayName(p.getCode().getPayrollCodeName());
    }

    @Override
    public Action getPreferredAction() {
        Action doNothing = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TopComponent editor = new PayrollCodeEditorTopComponent(code);
//                editor.open();
//                editor.requestActive();
            }
        };
        return doNothing;
        //return super.getPreferredAction(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Action[] getActions(boolean context) {
        Action[] actions = new Action[]{
            
            new AbstractAction("Assign Code To Employees") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TopComponent tc = new AssignCodesTopComponent(list);
                    tc.open();
                    tc.requestActive();
                }
            }
        
        };
        return actions;
    }

    
    
    
    
    @Override 
    protected Sheet createSheet(){
        final PayrollCodeSelectable bean = getLookup().lookup(PayrollCodeSelectable.class);
        Sheet basicPCode = super.createSheet();
        
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("details");
        
        set.setDisplayName("Details");
        
        try{
            
            Property group;
            group = new PropertySupport("group", String.class, "Group", "Code Group", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    if(bean.getCode().getPCodeGroupID()==null)
                        return "No Group";
                    else
                        return bean.getCode().getPCodeGroupID().getPCodeGroupName();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            set.put(group);
            
            Property showonslip;
            showonslip = new PropertySupport("sops", Boolean.class, "Show On Payslip", "Show On Payslip", true, true) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    
                        return bean.getCode().getDisplayPostedPayrollCodeInfoOnPaySlip();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            set.put(showonslip);
            
            Property id;
            id = new PropertySupport("id", String.class, "Payroll Code ID", "Payroll Code ID", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    
                        return bean.getCode().getPayrollCodeID()+"";
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            set.put(id);
            
            //final PropertySupport.Reflection processTest;
            //processTest = new PropertySupport.Reflection(bean.getCode(),Boolean.class,"processIn");
            Property active = new PropertySupport("active", Boolean.TYPE, "Active","Active", true, true) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.getCode().getProcessInPayroll();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            set.put(active);
            
            Property pcode = new PropertySupport("code", String.class, "CODE","CODE", true, false) {
                @Override
                public Object getValue() throws IllegalAccessException, InvocationTargetException {
                    return bean.code.getPayrollCodeCode();
                }
                
                @Override
                public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            set.put(pcode);
            
            final PropertySupport.Reflection test;
            Property isSelected;
            
            try{
                
                test = new PropertySupport.Reflection(bean, Boolean.class, "selected");
                test.setPropertyEditorClass(BooleanEditor.class);
                
                isSelected = new PropertySupport("isSelected", Boolean.class, "Select", "Select Code", true, true) {
                    @Override
                    public Object getValue() throws IllegalAccessException, InvocationTargetException {
                        return bean.getSelected();
                    }
                    
                    @Override
                    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        Boolean assign = (Boolean)val;
                        
                        if(assign){
                            UtilityPLR.prlIC.add(bean.getCode());
                        }else{
                            UtilityPLR.prlIC.remove(bean.getCode());
                        }
                      
                        
                        test.setValue(val);
                        
                    }
                };
                set.put(isSelected);
            
            }catch(Exception ex){
                NotifyUtil.error("Error", "Error", ex, false);
            }
            
            
            
                    
            

            
            
        }catch(Exception ex){
            Logger.getLogger(NodePayrollCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        basicPCode.put(set);
        
        return basicPCode;
        
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPayrollCode> rslt = (Lookup.Result<NodeRefreshPayrollCode>)ev.getSource();
        for(NodeRefreshPayrollCode e:rslt.allInstances()){
            TblPayrollCode codes = DataAccess.entityManager.find(TblPayrollCode.class, code.getCode().getPayrollCodeID());
            try{
            setDisplayName(codes.getPayrollCodeName());
            }catch(NullPointerException ex){
                
            }
        }    
    }
    
    
    
}
