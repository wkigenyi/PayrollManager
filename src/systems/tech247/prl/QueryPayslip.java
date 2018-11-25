/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;


import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblEmployeePeriodDetails;

import systems.tech247.api.ReloadableQueryCapability;

/**
 *
 * @author Wilfred
 */
public class QueryPayslip implements Lookup.Provider {
    
    InstanceContent ic;
    Lookup lookup;
    private String sqlString;
    private List<TblEmployeePeriodDetails> list;
    
    
    
    public QueryPayslip(final Employees emp){
        
        list = new ArrayList<>();
        // create an InstanceContent to hold capabilities
        ic = new InstanceContent();
        // create an abstract look to expose the contents of the instance content
        lookup = new AbstractLookup(ic);
        //Add a reloadable capabiliry to the instance content
        ic.add(new ReloadableQueryCapability() {
            @Override
            public void reload() throws Exception {
                
                getList().removeAll(list);
                ProgressHandle ph = ProgressHandleFactory.createHandle("Loading Payslips..");
                ph.start();
                DataAccess da = new DataAccess();
                List<TblEmployeePeriodDetails> list = da.searchPayslips(emp);
                        for (TblEmployeePeriodDetails e: list){
                    
                            getList().add(e);
                    
                        }
                ph.finish();
            }
        });
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    /**
     * @return the sqlString
     */
    public String getSqlString() {
        return sqlString;
    }

    /**
     * @param sqlString the sqlString to set
     */
    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    /**
     * @return the list
     */
    public List<TblEmployeePeriodDetails> getList() {
        return list;
    }
    
}
