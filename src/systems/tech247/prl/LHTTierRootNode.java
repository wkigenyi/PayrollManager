/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.util.CapCreatable;

/**
 *
 * @author Admin
 */
public class LHTTierRootNode extends AbstractNode {
    
    private final InstanceContent instanceContent;
    
    public LHTTierRootNode(QueryLHTTiers query){
        this(new InstanceContent(),query);
    }
    
    private LHTTierRootNode(InstanceContent ic, QueryLHTTiers query) {
        super(Children.create(new FactoryLHTTiers(query), true),new AbstractLookup(ic));
        instanceContent = ic;
        instanceContent.add(new CapCreatable() {
            @Override
            public void create() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        setIconBaseWithExtension("systems/tech247/util/icons/settings.png");
        setDisplayName("Add LHT Tiers Here");
    }
    
}
