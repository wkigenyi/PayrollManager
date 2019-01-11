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
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPayrollCodeGroups;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class NodePayrollCodeGroup extends  AbstractNode implements LookupListener{
    
    
    TblPayrollCodeGroups p;
    Boolean edit;
    Boolean showKids;
    List<TblPayrollCode> list = new ArrayList();
    private final InstanceContent instanceContent;
    Lookup.Result<NodeRefreshPCodeGroup> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPCodeGroup.class);
    
    public NodePayrollCodeGroup(TblPayrollCodeGroups p) throws IntrospectionException {
        this(p,new InstanceContent());
    }
    
    private NodePayrollCodeGroup(final TblPayrollCodeGroups p, InstanceContent ic) throws IntrospectionException{
        super(Children.LEAF, new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(p);
        this.p = p;
        list.addAll(p.getTblPayrollCodeCollection());
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new PayrollCodeGroupEditorTopComponent(p);
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
        rslt.addLookupListener(this);
        setDisplayName(p.getPCodeGroupName());
        setIconBaseWithExtension("systems/tech247/util/icons/document.png");
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result<NodeRefreshPCodeGroup> rslt= (Lookup.Result<NodeRefreshPCodeGroup>)ev.getSource();
        for(NodeRefreshPCodeGroup nrp: rslt.allInstances()){
            TblPayrollCodeGroups group = DataAccess.entityManager.find(TblPayrollCodeGroups.class, p.getPCodeGroupID());
            setDisplayName(group.getPCodeGroupName());
        }
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] actions = new Action[]{
            
            new AbstractAction("Assign Code Group To Employees") {
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
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Set set = Sheet.createPropertiesSet();
        final TblPayrollCodeGroups group = getLookup().lookup(TblPayrollCodeGroups.class);
        
        Property number = new PropertySupport("number", String.class, "Number", "Number", true, false) {
            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return group.getTblPayrollCodeCollection().size();
            }
            
            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        set.put(number);
        sheet.put(set);
        return sheet; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
}
