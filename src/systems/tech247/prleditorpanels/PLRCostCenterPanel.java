/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prleditorpanels;

import java.awt.BorderLayout;
import javax.persistence.EntityManager;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.prl.UtilityPLR;



/**
 *
 * @author Admin
 */
public class PLRCostCenterPanel extends javax.swing.JPanel implements ExplorerManager.Provider, LookupListener{
    
    ExplorerManager em = UtilityPLR.emPLRCostCenters;
    
    
    DataAccess da = new DataAccess();
    
    Employees e;
    String sqlString;
    String searchString;
    
    EntityManager entityManager = DataAccess.getEntityManager();
    TopComponent employeeTc = WindowManager.getDefault().findTopComponent("EmployeesTopComponent");
    Lookup.Result<Employees> empRslt = employeeTc.getLookup().lookupResult(Employees.class);
    
    TopComponent periodsTc = WindowManager.getDefault().findTopComponent("YearsTopComponent");
    Lookup.Result<Integer> paidperiodsRslt = periodsTc.getLookup().lookupResult(Integer.class);
    
    
    
    /**
     * Creates new form PersonalInfoPanel
     */
    public PLRCostCenterPanel() {
        initComponents();
        //Start transaction
        BeanTreeView btv = new BeanTreeView();
        
        
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(btv);
        
        
        
        
        paidperiodsRslt.addLookupListener(this);
        resultChanged(new LookupEvent(paidperiodsRslt));
        
        
        
        UtilityPLR.loadCostCenters();
        
        
        
        
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPanel = new javax.swing.JPanel();

        viewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables

      
    

    
    

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup.Result result = (Lookup.Result)ev.getSource();
        for(Object obj: result.allInstances()){
            if(obj instanceof Employees){
//            e = (Employees)obj;   
//            search.setText(e.getSurName() + " " + e.getOtherNames());
            
            }
            
            if(obj instanceof Integer){
                
                
               
            }
        }
        
        
    }
    
    
    
    
    
    
    
    
    
        
        
        
        
    

    
}
