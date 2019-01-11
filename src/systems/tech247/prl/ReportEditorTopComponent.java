/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.payrollreports.ReportPayroll;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.WindowManager;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.OrganizationUnits;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//systems.tech247.prl//ReportEditor//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ReportEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "systems.tech247.prl.ReportEditorTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ReportEditorAction",
        preferredID = "ReportEditorTopComponent"
)
@Messages({
    "CTL_ReportEditorAction=Customise Report",
    "CTL_ReportEditorTopComponent=Report Customiser",
    "HINT_ReportEditorTopComponent=Report Customiser"
})
public final class ReportEditorTopComponent extends TopComponent implements LookupListener {
    

   
    
    TblPeriods period;
    Boolean sortByDepartment = false;
    Boolean subTotalsAtGroup = false;
    Boolean subTotalsAtSummary = false;
    
    
    InstanceContent ic = new InstanceContent();
    
    Lookup.Result<TblPayrollCode> txCodeRslt = UtilityPLR.getInstance().getLookup().lookupResult(TblPayrollCode.class);
    List<TblPayrollCode> selectedCodes = new ArrayList<>();
    TopComponent tc = WindowManager.getDefault().findTopComponent("PeriodsTopComponent");
    Lookup.Result<TblPeriods> rslt = tc.getLookup().lookupResult(TblPeriods.class);
    public ReportEditorTopComponent(){
        this (DataAccess.getCurrentMonth());
        
    }
    
    public ReportEditorTopComponent(TblPeriods p) {
        initComponents();
        setName(Bundle.CTL_ReportEditorTopComponent());
        setToolTipText(Bundle.HINT_ReportEditorTopComponent());
        //String sql="SELECT e FROM Employees e WHERE e.isDisengaged =0";
        //empList = DataAccess.searchEmployees(sql);
        this.period = p;
        jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
        
        
        
        
        resultChanged(new LookupEvent(txCodeRslt));
        txCodeRslt.addLookupListener(this);
        
        rslt.addLookupListener(this);
        
        jtPeriod.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(tc, "Select Period"));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        jbPayrollCodeSelector = new javax.swing.JButton();
        jbReportRunner = new javax.swing.JButton();
        jbCodesCounter = new javax.swing.JLabel();
        jcbSortByDept = new javax.swing.JCheckBox();
        jcbSubTotalPerDepartment = new javax.swing.JCheckBox();
        jcbSubTotalAtSummary = new javax.swing.JCheckBox();
        jtPeriod = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jbPayrollCodeSelector, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jbPayrollCodeSelector.text")); // NOI18N
        jbPayrollCodeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPayrollCodeSelectorActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jbReportRunner, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jbReportRunner.text")); // NOI18N
        jbReportRunner.setEnabled(false);
        jbReportRunner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbReportRunnerActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jbCodesCounter, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jbCodesCounter.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbSortByDept, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbSortByDept.text")); // NOI18N
        jcbSortByDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSortByDeptActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbSubTotalPerDepartment, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbSubTotalPerDepartment.text")); // NOI18N
        jcbSubTotalPerDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSubTotalPerDepartmentActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jcbSubTotalAtSummary, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbSubTotalAtSummary.text")); // NOI18N
        jcbSubTotalAtSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSubTotalAtSummaryActionPerformed(evt);
            }
        });

        jtPeriod.setText(org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jtPeriod.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbCodesCounter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbSortByDept)
                            .addComponent(jbReportRunner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbPayrollCodeSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(jcbSubTotalPerDepartment)
                            .addComponent(jcbSubTotalAtSummary)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtPeriod)))
                        .addGap(0, 69, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbPayrollCodeSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbReportRunner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSortByDept)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSubTotalPerDepartment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSubTotalAtSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jbCodesCounter)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbPayrollCodeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPayrollCodeSelectorActionPerformed
        
        selectedCodes.clear();
        DialogDisplayer.getDefault().notify(new DialogDescriptor(new PayrollCodesTopComponent("", Boolean.FALSE,Boolean.TRUE), "Select Transaction Codes"));
        
    }//GEN-LAST:event_jbPayrollCodeSelectorActionPerformed

    private void jbReportRunnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReportRunnerActionPerformed
        try {
            runReport();
        } catch (DRException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jbReportRunnerActionPerformed

    private void jcbSortByDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSortByDeptActionPerformed
        sortByDepartment = jcbSortByDept.isSelected();
        jcbSubTotalPerDepartment.setEnabled(sortByDepartment);
        if(!sortByDepartment){
            jcbSubTotalPerDepartment.setSelected(false);
            
        }
    }//GEN-LAST:event_jcbSortByDeptActionPerformed

    private void jcbSubTotalPerDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSubTotalPerDepartmentActionPerformed
        subTotalsAtGroup = jcbSubTotalPerDepartment.isSelected();
        subTotalsAtSummary =!subTotalsAtGroup;
        jcbSubTotalAtSummary.setEnabled(!subTotalsAtGroup);
    }//GEN-LAST:event_jcbSubTotalPerDepartmentActionPerformed

    private void jcbSubTotalAtSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSubTotalAtSummaryActionPerformed
        subTotalsAtSummary = jcbSubTotalAtSummary.isSelected();
        subTotalsAtGroup = !subTotalsAtGroup;
        jcbSubTotalPerDepartment.setEnabled(!subTotalsAtGroup);
    }//GEN-LAST:event_jcbSubTotalAtSummaryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jbCodesCounter;
    private javax.swing.JButton jbPayrollCodeSelector;
    private javax.swing.JButton jbReportRunner;
    private javax.swing.JCheckBox jcbSortByDept;
    private javax.swing.JCheckBox jcbSubTotalAtSummary;
    private javax.swing.JCheckBox jcbSubTotalPerDepartment;
    private javax.swing.JTextField jtPeriod;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        
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
    public void resultChanged(LookupEvent le) {
        Lookup.Result r = (Lookup.Result)le.getSource();
        selectedCodes.clear();
        for(Object o: r.allInstances()){
            if(o instanceof TblPayrollCode){
                selectedCodes.add((TblPayrollCode)o);
                if(selectedCodes.isEmpty()){
                    jbCodesCounter.setText("Selected Codes");
                    jbReportRunner.setEnabled(false);
                }else{
                    jbReportRunner.setEnabled(true);
                    jbCodesCounter.setText(selectedCodes.size()+" Codes Selected");
                }
            }else if(o instanceof TblPeriods){
                period = (TblPeriods)o;
                jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
            }
        }
        
        
        
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        void runReport() throws DRException{
        

        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("Run Payroll Report For  "+ period.getPeriodMonth()+" "+ period.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            Runnable task = new Runnable() {
                ProgressHandle handle = ProgressHandleFactory.createHandle("Compiling the report");
                StyleBuilder boldStyle = DynamicReports.stl.style().bold();
                StyleBuilder alignLeft = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
                
                
                
                @Override
                public void run() {
                    handle.start();
                    
                    //Set The Columns
                    //Determine the array length
                    int codesLength = selectedCodes.size();
                    ColumnBuilder[] columns = new ColumnBuilder[codesLength+4];
                    String[] columnNames = new String[codesLength+4];
                    handle.progress("Setting up report columns");
                    TextColumnBuilder<Integer> rowNumber = DynamicReports.col.column("No.","no",DynamicReports.type.integerType()).setStyle(alignLeft).setFixedWidth(20);
                    columns[0] = rowNumber;
                    TextColumnBuilder lname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
                    columns[1] = lname;
                    TextColumnBuilder fname = DynamicReports.col.column("Other Name","fname",DynamicReports.type.stringType());
                    columns[2] = fname;
                    TextColumnBuilder dept = DynamicReports.col.column("Dep't","dept",DynamicReports.type.stringType());
                    columns[3] = dept;
                    //Also The SubTotal Columns
                    SubtotalBuilder[] subtotals = new SubtotalBuilder[selectedCodes.size()];
                    AggregationSubtotalBuilder[] subtotalsG= new AggregationSubtotalBuilder[selectedCodes.size()];
                    for(int i=0;i<selectedCodes.size();i++){
                        columns[i+4] = DynamicReports.col.column(selectedCodes.get(i).getReportLabel(),selectedCodes.get(i).getPayrollCodeCode(),DynamicReports.type.bigDecimalType());
                        subtotals[i] = sbt.sum((TextColumnBuilder)columns[i+4]);
                        subtotalsG[i] = sbt.sum((TextColumnBuilder)columns[i+4]);
                    }
                    
                    handle.progress("Creating Data Source");
                    columnNames[0]="no";
                    columnNames[1]="surname";
                    columnNames[2]="fname"; //Filling in the initial fields
                    columnNames[3]="dept";
                    for(int i=0;i<selectedCodes.size();i++){
                        columnNames[i+4] = selectedCodes.get(i).getPayrollCodeCode();
                    }
                    handle.progress("Adding The Data");
                    DRDataSource dataSource = new DRDataSource(columnNames);
                    
                    if(sortByDepartment){
                        int i = 0;
                        List<OrganizationUnits> list = DataAccess.searchDepartments("SELECT o FROM OrganizationUnits o");
                        //Process per OU
                        for(OrganizationUnits u:list){
                            for(Employees e: u.getEmployeesCollection()){
                                if(!e.getIsDisengaged()){
                                    Object[] data = new Object[selectedCodes.size()+4];
                                    data[0] = i+1;
                                    data[1] = e.getSurName();
                                    data[2] = e.getOtherNames();
                                    data[3] = e.getOrganizationUnitID().getOrganizationUnitName();
                                    for(int j=0;j<selectedCodes.size();j++){
                                        data[j+4]=DataAccess.getPayrollAmount(e, selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                                    }
                                    dataSource.add(data);
                                    i++;
                                }
                                
                            }
                        }
                    }else{
                    
                    List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged =0");
                    
                    for(int i=0; i<list.size(); i++){
                        Object[] data = new Object[selectedCodes.size()+4];
                        data[0] = i+1;
                        data[1] = list.get(i).getSurName();
                        data[2] = list.get(i).getOtherNames();
                        data[3] = list.get(i).getOrganizationUnitID().getOrganizationUnitName();
                        for(int j=0;j<selectedCodes.size();j++){
                            data[j+4]=DataAccess.getPayrollAmount(list.get(i), selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                        }
                    
                    dataSource.add(data);
                    }
                    }
                    
                    handle.progress("Finalising");
                    
                    ReportPayroll design = new ReportPayroll(columns, columnNames,subtotals,subtotalsG,dataSource,sortByDepartment,subTotalsAtGroup,subTotalsAtSummary,period);
                    JasperReportBuilder report = design.getReport();
                    try {
                        report.show(false);
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (DRException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    handle.finish();
                }
            };
            
            RequestProcessor.getDefault().post(task);

        }
        
    }
    
    
    
     
    
    
}
