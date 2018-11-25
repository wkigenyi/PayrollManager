/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import systems.tech247.hr.Employees;


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


    
    
    
}
