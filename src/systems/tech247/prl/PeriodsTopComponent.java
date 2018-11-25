/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.BorderLayout;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//Periods//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PeriodsTopComponent",
        iconBase = "systems/tech247/util/icons/Calendar.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false /*, roles = {"Payroll"}*/)
@ActionID(category = "Payroll", id = "systems.tech247.prl.PeriodsTopComponent")
@ActionReference(path = "Menu/Payroll" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PeriodsAction",
        preferredID = "PeriodsTopComponent"
)
@Messages({
    "CTL_PeriodsAction=Payroll Periods",
    "CTL_PeriodsTopComponent=Payroll Periods",
    "HINT_PeriodsTopComponent= "
})
public final class PeriodsTopComponent extends TopComponent implements ExplorerManager.Provider {
    
    ExplorerManager em = new ExplorerManager();
   

    
    
   
    public PeriodsTopComponent() {
        initComponents();
        setName(Bundle.CTL_PeriodsTopComponent());
        setToolTipText(Bundle.HINT_PeriodsTopComponent());
        BeanTreeView ov = new BeanTreeView();
        ov.setRootVisible(false);
        setLayout(new BorderLayout());
        add(ov);
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        
        
        
        
        
      

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    @Override
    protected void componentShowing() {
        super.componentShowing(); //To change body of generated methods, choose Tools | Templates.
        RequestProcessor.getDefault().post(new Runnable() {
            @Override
            public void run() {
                em.setRootContext(new AbstractNode(Children.create(new FactoryPayrollPeriod(), true)));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

   
    
    
    
    
  }
