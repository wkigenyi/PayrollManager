/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import systems.tech247.hr.PrlMonthlyPAYETableTiers;

/**
 *
 * @author Admin
 */
public class PAYETierCover {
    private PrlMonthlyPAYETableTiers tier;
    private String lower;
    NumberFormat nf = new DecimalFormat("#,###.00");
    private String rate;
    private String name;
    private String upper;
    private String additional;
    private String standing;
    
    public PAYETierCover(PrlMonthlyPAYETableTiers t){
        tier = t;
        lower = nf.format(tier.getLowerBoundry());
        upper = nf.format(tier.getUpperBoundary());
        standing = nf.format(tier.getStandingAmount());
        rate = nf.format(tier.getTierRate())+"%";
        additional = nf.format(tier.getAdditionalRate())+"%";
        name = "Tier Level "+tier.getTierLevel();
       
    }

    public String getStanding() {
        return standing;
    }

    public String getLower() {
        return lower;
    }

    public String getUpper() {
        return upper;
    }

    public String getAdditional() {
        return additional;
    }

    
    
 
    

   

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    
    
}
