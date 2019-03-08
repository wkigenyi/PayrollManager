/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.swing.AbstractAction;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.PrlMonthlyPAYETable;
import systems.tech247.hr.PrlMonthlyPAYETableTiers;
import systems.tech247.util.CetusUTL;
import systems.tech247.util.MessageType;
import systems.tech247.util.NotifyUtil;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//PAYE//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PAYETopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.PAYETopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_PAYEAction",
//        preferredID = "PAYETopComponent"
//)
@Messages({
    "CTL_PAYEAction=PAYE",
    "CTL_PAYETopComponent=PAYE Tiers",
    "HINT_PAYETopComponent=PAYE Tiers"
})
public final class PAYETopComponent extends TopComponent implements ExplorerManager.Provider{
    
    Calendar calendar = Calendar.getInstance();
    JSpinner spinner;
    
    
    ExplorerManager em = new ExplorerManager();
    
    public PAYETopComponent() {
        initComponents();
        setName(Bundle.CTL_PAYETopComponent());
        setToolTipText(Bundle.HINT_PAYETopComponent());
        jpPayeTiers.setLayout(new BorderLayout());
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        OutlineView ov = new OutlineView("Tiers");
        ov.addPropertyColumn("lower", "Lower Boundary");
        ov.addPropertyColumn("upper", "Upper Boundary");
        ov.addPropertyColumn("rate", "Rate(%)");
        ov.addPropertyColumn("standing", "Standing Amount");
        ov.addPropertyColumn("additional", "Additional Rate");
        ov.getOutline().setRootVisible(false);
        jpPayeTiers.add(ov);


        
        
        
        
        
        
        
        
        
        jpYearHolder.setLayout(new BorderLayout());
        //Add the month/year spinner
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -5);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 10);
        Date latestDate =  calendar.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
        spinner = CetusUTL.addLabeledSpinner(jpYearHolder, dateModel);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy"));
        
        
        spinner.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                
                SpinnerDateModel model = (SpinnerDateModel)spinner.getModel();
                
                final Calendar cal = Calendar.getInstance();
                
                Date date = model.getDate();
                
                if(evt.getSource()==spinner){
                    Date newDate = model.getDate();
                    if(date!=newDate){
                        cal.setTime(date);
                        makeBusy(true);
                        
                        QueryPAYETiers query =  new QueryPAYETiers(cal.get(Calendar.YEAR));
                        
                        if(DataAccess.searchPayeTiers(cal.get(Calendar.YEAR)).isEmpty()){
                            NotifyUtil.show("NO Tiers Found For "+cal.get(Calendar.YEAR), "Duplicate FROM Previous Years?", MessageType.QUESTION, new AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int tableID = DataAccess.getLastestPAYETable();
                                    final List<PrlMonthlyPAYETableTiers> tiers = new ArrayList<>();
                                    if(tableID==0){
                                        NotifyUtil.show("No Previous PAYE Entries Found", "Add New Entries", MessageType.INFO, false);
                                    }else{
                                        
                                        final PrlMonthlyPAYETable table= DataAccess.entityManager.find(PrlMonthlyPAYETable.class, tableID);
                                        tiers.addAll(table.getPrlMonthlyPAYETableTiersCollection());
                                        NotifyUtil.info(table.getPrlMonthlyPAYETableTiersCollection().size()+ " Entries Found For "+table.getPeriodYear(), "These will duplicated", false);
                                        Runnable task = new Runnable() {
                                            int lastYear = cal.get(Calendar.YEAR);
                                            int currentYear = table.getPeriodYear()+1;
                                            
                                            
                                            
                                            @Override
                                            public void run() {
                                                ProgressHandle ph = ProgressHandleFactory.createHandle("Duplicating PAYE Entries");
                                                ph.start();
                                                
                                                while(currentYear<=lastYear){
                                                    //Insert the year in years Table
                                                    ph.progress("Inserting "+currentYear);
                                                    String insertYear = "INSERT INTO [dbo].[prlMonthlyPAYETable]\n" +
"           ([PeriodYear]\n" +
"           ,[MonthlyRelief])\n" +
"     VALUES\n" +
"           ("+currentYear+"" +
"           ,0)";
                                                    DataAccess.entityManager.getTransaction().begin();
                                                    DataAccess.entityManager.createNativeQuery(insertYear).executeUpdate();
                                                    
                                                    DataAccess.entityManager.getTransaction().commit();
                                                    //The year has been inserted, now enter its entries
                                                    //Get the Table id of current year
                                                    String thetableID = "SELECT  MonthlyPAYETableID FROM prlMonthlyPAYETable WHERE PeriodYear="+currentYear+"";
                                                    int id = (int)DataAccess.entityManager.createNativeQuery(thetableID).getSingleResult();
                                                    String insertTiers="INSERT INTO [dbo].[prlMonthlyPAYETableTiers]\n" +
"           ([MonthlyPAYETableID]\n" +
"           ,[TierLevel]\n" +
"           ,[UpperBoundary]\n" +
"           ,[TierRate]\n" +
"           ,[LowerBoundry]\n" +
"           ,[StandingAmount]\n" +
"           ,[AdditionalRate])\n" +
"     VALUES\n" +
"           (?,?,?,?,?,?,?)";
                                                    Query query =DataAccess.entityManager.createNativeQuery(insertTiers);
                                                    for(PrlMonthlyPAYETableTiers tier : tiers){//Inserting each tier in this Year
                                                        query.setParameter(1, id); //the table ID
                                                        query.setParameter(2, tier.getTierLevel());  //tier Level
                                                        query.setParameter(3, tier.getUpperBoundary());  //upper
                                                        query.setParameter(4, tier.getTierRate());  //tier amount
                                                        query.setParameter(5, tier.getLowerBoundry());//Lower Boundary
                                                        query.setParameter(6, tier.getStandingAmount());//Standing Amount
                                                        query.setParameter(7, tier.getAdditionalRate());//Additional Rate
                                                        DataAccess.entityManager.getTransaction().begin();
                                                        query.executeUpdate();
                                                        DataAccess.entityManager.getTransaction().commit();
                                                    }
                                                    currentYear+=1;//go to next year
                                                    //Refresh The View
                                                    UtilityPLR.prlIC.set(Arrays.asList(new NodeRefreshPAYE()), null);
                                                }
                                                ph.finish();
                                            }
                                            
                                        };
                                        RequestProcessor.getDefault().post(task);
                                    }
                                }
                            }, false);
                        }
                        
                        em.setRootContext(new AbstractNode(Children.create(new FactoryPAYETiers(query), true)));
                        
                        makeBusy(false);
                    }
                    
                    
                }
                
                
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPayeTiers = new javax.swing.JPanel();
        jpYearHolder = new javax.swing.JPanel();

        jpPayeTiers.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpPayeTiersLayout = new javax.swing.GroupLayout(jpPayeTiers);
        jpPayeTiers.setLayout(jpPayeTiersLayout);
        jpPayeTiersLayout.setHorizontalGroup(
            jpPayeTiersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        jpPayeTiersLayout.setVerticalGroup(
            jpPayeTiersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 285, Short.MAX_VALUE)
        );

        jpYearHolder.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpYearHolderLayout = new javax.swing.GroupLayout(jpYearHolder);
        jpYearHolder.setLayout(jpYearHolderLayout);
        jpYearHolderLayout.setHorizontalGroup(
            jpYearHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 152, Short.MAX_VALUE)
        );
        jpYearHolderLayout.setVerticalGroup(
            jpYearHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpPayeTiers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jpYearHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpYearHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPayeTiers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jpPayeTiers;
    private javax.swing.JPanel jpYearHolder;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
