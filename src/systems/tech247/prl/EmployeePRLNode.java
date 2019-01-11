/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPeriods;


/**
 *
 * @author Admin
 */
public class EmployeePRLNode extends AbstractNode{
    
    Employees emp;
    public EmployeePRLNode(Employees emp){
        super(Children.create(new FactoryEmployeePRLDetails(emp), true));
        this.emp = emp;
        setIconBaseWithExtension("systems/tech247/util/icons/person.png");
            setDisplayName(emp.getSurName()+" "+emp.getOtherNames());
    }

    @Override
    public Action[] getActions(boolean context) {
        final List<Employees> list =  new ArrayList<>();
        list.add(emp);
        
        Action[] actions = new Action[]{
            new AbstractAction("(Re)-Calculate Payroll") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UtilityPLR.postTx(list);
                }
            }
        };
        return actions; //To change body of generated methods, choose Tools | Templates.
    }
    
    


    
    
    
}
