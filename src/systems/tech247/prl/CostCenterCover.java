/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.hr.TblCostCenter;

/**
 *
 * @author Admin
 */
public class CostCenterCover {
    private TblCostCenter tier;
    private String code;
    private String description;
    private String company;
    private String empNo;    
    private String dept;
    public CostCenterCover(TblCostCenter t){
        tier = t;
        code = tier.getCode();
        description = tier.getDescription();
        company = tier.getCompanyID().getCompanyName();
        //dept = tier.getCstructureID().getOrganizationUnitName();
        empNo = tier.getTblEmployeeCostCenterCollection().size()+"";
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @return the dept
     */
    public String getDept() {
        return dept;
    }

    
    
 
    

    
    
}
