/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import systems.tech247.util.CapDeletable;
import systems.tech247.util.CapEditable;
import systems.tech247.util.CetusUTL;

/**
 *
 * @author Admin
 */
public class EmployeeTransactionNode extends  AbstractNode implements LookupListener{
    
    private final InstanceContent instanceContent;
    
    public EmployeeTransactionNode(EmployeeTransactionCover trans) {
        this(trans,new InstanceContent());
    }
    
    private EmployeeTransactionNode(final EmployeeTransactionCover trans, InstanceContent ic){
        super(Children.LEAF, new AbstractLookup(ic));
        
        instanceContent = ic;
        instanceContent.add(trans);
        instanceContent.add(new CapEditable() {
            @Override
            public void edit() {
                TopComponent tc = new EmployeeTransactionEditorTopComponent(null,trans.getTransaction());
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
        int padRight = 30-trans.getName().length();
        int padLeft = 30-trans.getAmount().length();
        setDisplayName(CetusUTL.padRight(trans.getName(), padRight)+" ** "+trans.getAmount());
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
