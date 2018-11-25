/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayrollCodeGroups;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;

/**
 *
 * @author Admin
 */
public class PayrollCodeGroupNode extends  AbstractNode implements LookupListener{
    
    
    TblPayrollCodeGroups p;
    Boolean edit;
    Boolean showKids;
    private final InstanceContent instanceContent;
    Lookup.Result<NodeRefreshPCodeGroup> rslt = UtilityPLR.getInstance().getLookup().lookupResult(NodeRefreshPCodeGroup.class);
    
    public PayrollCodeGroupNode(TblPayrollCodeGroups p) throws IntrospectionException {
        this(p,new InstanceContent());
    }
    
    private PayrollCodeGroupNode(final TblPayrollCodeGroups p, InstanceContent ic) throws IntrospectionException{
        super(Children.create(new FactoryPayrollCodes(p), true), new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(p);
        this.p = p;
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
    
    
    
}
