/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import systems.tech247.payrollreports.ReportPayroll;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.openide.awt.StatusDisplayer;
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
import systems.tech247.hr.Currencies;
import systems.tech247.hr.Employees;
import systems.tech247.hr.OrganizationUnits;
import systems.tech247.hr.TblPayroll;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.NotifyUtil;

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
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//        displayName = "#CTL_ReportEditorAction",
//        preferredID = "ReportEditorTopComponent"
//)
@Messages({
    "CTL_ReportEditorAction=Customise Report",
    "CTL_ReportEditorTopComponent=Report Customiser",
    "HINT_ReportEditorTopComponent=Report Customiser"
})
public final class ReportEditorTopComponent extends TopComponent implements LookupListener {
    

   
    Calendar calFrom = Calendar.getInstance();
    Date from;
    Date to;
    TblPayrollCode selectedcode; 
    TblPeriods period;
    Boolean sortByDepartment = false;
    Boolean subTotalsAtGroup = false;
    Boolean subTotalsAtSummary = false;
    Boolean includeAttendance = false;
    Boolean includePageNumbers = false;
    Boolean repeatColumnHeaders = false;
    
    
    InstanceContent ic = new InstanceContent();
    TopComponent tc1 = WindowManager.getDefault().findTopComponent("PayrollCodesTopComponent");
    
    TopComponent tcp = WindowManager.getDefault().findTopComponent("PayrollsTopComponent");
    Lookup.Result<TblPayrollCode> txCodeRslt = UtilityPLR.getInstance().getLookup().lookupResult(TblPayrollCode.class);
    Lookup.Result<TblPayroll> payrollRslt = tcp.getLookup().lookupResult(TblPayroll.class);
    Lookup.Result<PayrollCodeSelectable> txCodeWithCurrency = tc1.getLookup().lookupResult(PayrollCodeSelectable.class);
    List<TblPayrollCode> selectedCodes = new ArrayList<>();
    TblPayroll selectedPayroll;
    boolean includeCurrency;
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
        int month = DataAccess.covertMonthsToInt(p.getPeriodMonth())-2;
        calFrom.set(Calendar.MONTH,month);
        calFrom.set(Calendar.DATE, 26);
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        
        from = calFrom.getTime();
        jdcFrom.setDate(from);
        calFrom.add(Calendar.MONTH, 1);
        calFrom.add(Calendar.DAY_OF_MONTH, -1);
        to = calFrom.getTime();
        jdcTo.setDate(to);
       
        
        
        
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
        
        jtPeriod.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(tc, "Select Period"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        jtCodewithCurrency.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogDisplayer.getDefault().notify(new DialogDescriptor(tc1, "Select Payroll Code"));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
//        jdcFrom.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if(evt.getSource() == jdcFrom && evt.getPropertyName()=="date"){
//                    calFrom.set(Calendar.DAY_OF_MONTH, jdcFrom.getDate().getDate());
//                    calFrom.set(Calendar.MONTH, jdcFrom.getDate().getMonth());
//                    calFrom.set(Calendar.YEAR, jdcFrom.getDate().getYear());
//                    from = calFrom.getTime();
//                }
//            }
//        });
//        
//        jdcTo.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if(evt.getSource() == jdcTo && evt.getPropertyName()=="date"){
//                    calFrom.set(Calendar.DAY_OF_MONTH, jdcTo.getDate().getDate());
//                    calFrom.set(Calendar.MONTH, jdcTo.getDate().getMonth());
//                    calFrom.set(Calendar.YEAR, jdcTo.getDate().getYear());
//                    to = calFrom.getTime();
//                }
//            }
//        });
        
        
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
        jcbSortByDept = new javax.swing.JCheckBox();
        jcbSubTotalPerDepartment = new javax.swing.JCheckBox();
        jcbSubTotalAtSummary = new javax.swing.JCheckBox();
        jtPeriod = new javax.swing.JTextField();
        jlCodeCounter = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jtCodewithCurrency = new javax.swing.JTextField();
        jcbWithCurrency = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jdcFrom = new com.toedter.calendar.JDateChooser();
        jcbIncludeAttendance = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jdcTo = new com.toedter.calendar.JDateChooser();
        jlCount = new javax.swing.JLabel();
        jcbIncludePageNumbers = new javax.swing.JCheckBox();

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

        org.openide.awt.Mnemonics.setLocalizedText(jlCodeCounter, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jlCodeCounter.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jtCodewithCurrency.setText(org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jtCodewithCurrency.text")); // NOI18N
        jtCodewithCurrency.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jcbWithCurrency, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbWithCurrency.text")); // NOI18N
        jcbWithCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbWithCurrencyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jcbWithCurrency)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jtCodewithCurrency))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbWithCurrency)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtCodewithCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jdcFrom.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jcbIncludeAttendance, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbIncludeAttendance.text")); // NOI18N
        jcbIncludeAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbIncludeAttendanceActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jLabel1.text")); // NOI18N

        jdcTo.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbIncludeAttendance)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcTo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbIncludeAttendance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        org.openide.awt.Mnemonics.setLocalizedText(jlCount, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jlCount.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jcbIncludePageNumbers, org.openide.util.NbBundle.getMessage(ReportEditorTopComponent.class, "ReportEditorTopComponent.jcbIncludePageNumbers.text")); // NOI18N
        jcbIncludePageNumbers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbIncludePageNumbersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlCodeCounter)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jcbSortByDept)
                        .addComponent(jbPayrollCodeSelector, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbSubTotalPerDepartment)
                        .addComponent(jcbSubTotalAtSummary)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jcbIncludePageNumbers))
                    .addComponent(jlCount)
                    .addComponent(jbReportRunner, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCodeCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbPayrollCodeSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSortByDept)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbSubTotalPerDepartment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbSubTotalAtSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbIncludePageNumbers)
                .addGap(18, 18, 18)
                .addComponent(jbReportRunner)
                .addGap(31, 31, 31)
                .addComponent(jlCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbPayrollCodeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPayrollCodeSelectorActionPerformed
        
        selectedCodes.clear();
       
        jlCount.setText("List Of Codes Has Been Cleared");
        
        DialogDisplayer.getDefault().notify(new DialogDescriptor(new PayrollCodesTopComponent("", Boolean.FALSE,Boolean.TRUE), "Select Transaction Codes"));
        
    }//GEN-LAST:event_jbPayrollCodeSelectorActionPerformed

    private void jbReportRunnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReportRunnerActionPerformed
        
        
        
        
        
        
        Object result = DialogDisplayer.getDefault().notify(new DialogDescriptor(tcp, "Select Payroll"));
        if(result == NotifyDescriptor.OK_OPTION){
            if(selectedPayroll == null){
                StatusDisplayer.getDefault().setStatusText("Payroll was not selected");
            }else{
                if(selectedCodes.isEmpty()){
                    NotifyUtil.warn("Empty Code List", "Code List is empty", false);
                }
                
                
                try {
                    runReport(selectedPayroll);
                } catch (DRException ex) {
                    Exceptions.printStackTrace(ex);
                }            
            }
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

    private void jcbIncludeAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbIncludeAttendanceActionPerformed
        includeAttendance = jcbIncludeAttendance.isSelected();
        jdcFrom.setEnabled(includeAttendance);
        jdcTo.setEnabled(includeAttendance);
    }//GEN-LAST:event_jcbIncludeAttendanceActionPerformed

    private void jcbWithCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbWithCurrencyActionPerformed
        includeCurrency = jcbWithCurrency.isSelected();
        jtCodewithCurrency.setEnabled(includeCurrency);
        if(includeCurrency){
            
            DialogDisplayer.getDefault().notify(new DialogDescriptor(tc1, "Select Payroll Code"));
            
        }
    }//GEN-LAST:event_jcbWithCurrencyActionPerformed

    private void jcbIncludePageNumbersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbIncludePageNumbersActionPerformed
        includePageNumbers = jcbIncludePageNumbers.isSelected();
    }//GEN-LAST:event_jcbIncludePageNumbersActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbPayrollCodeSelector;
    private javax.swing.JButton jbReportRunner;
    private javax.swing.JCheckBox jcbIncludeAttendance;
    private javax.swing.JCheckBox jcbIncludePageNumbers;
    private javax.swing.JCheckBox jcbSortByDept;
    private javax.swing.JCheckBox jcbSubTotalAtSummary;
    private javax.swing.JCheckBox jcbSubTotalPerDepartment;
    private javax.swing.JCheckBox jcbWithCurrency;
    private com.toedter.calendar.JDateChooser jdcFrom;
    private com.toedter.calendar.JDateChooser jdcTo;
    private javax.swing.JLabel jlCodeCounter;
    private javax.swing.JLabel jlCount;
    private javax.swing.JTextField jtCodewithCurrency;
    private javax.swing.JTextField jtPeriod;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        payrollRslt.addLookupListener(this);
        txCodeWithCurrency.addLookupListener(this);
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
        //selectedCodes.clear();
        for(Object o: r.allInstances()){
            if(o instanceof TblPayrollCode){
                
                TblPayrollCode code = (TblPayrollCode)o;
                if(!selectedCodes.contains(code)){
                    selectedCodes.add(code);
                }
                
                if(selectedCodes.isEmpty()){
                    jlCount.setText("Selected Codes");
                    jbReportRunner.setEnabled(false);
                }else{
                    jbReportRunner.setEnabled(true);
                    jlCount.setText(selectedCodes.size()+" Codes Selected");
                }
            }else if(o instanceof TblPeriods){
                period = (TblPeriods)o;
                jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
                
                int month = DataAccess.covertMonthsToInt(period.getPeriodMonth())-2;
                calFrom.set(Calendar.YEAR, period.getPeriodYear());
                calFrom.set(Calendar.MONTH,month);
                calFrom.set(Calendar.DATE, 26);
                calFrom.set(Calendar.HOUR_OF_DAY, 0);
                calFrom.set(Calendar.MINUTE, 0);
                calFrom.set(Calendar.SECOND, 0);
        
                from = calFrom.getTime();
                jdcFrom.setDate(from);
                calFrom.add(Calendar.MONTH, 1);
                calFrom.add(Calendar.DAY_OF_MONTH, -1);
                to = calFrom.getTime();
                jdcTo.setDate(to);
            }else if(o instanceof TblPayroll){
                selectedPayroll = (TblPayroll)o;
                StatusDisplayer.getDefault().setStatusText(selectedPayroll.getPayrollName()+" Selected");
            }else if(o instanceof PayrollCodeSelectable){
                selectedcode = ((PayrollCodeSelectable)o).getCode();
                jtCodewithCurrency.setText(selectedcode.getPayrollCodeName());
            }
        }
        
        
        
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        void runReport(TblPayroll prl) throws DRException{
        if(selectedcode == null){
            includeCurrency = false;
            jcbWithCurrency.setSelected(false);
        }
        
        //from = jdcFrom.getDate();
        //to = jdcTo.getDate();

        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("Run Payroll Report For  "+ period.getPeriodMonth()+" "+ period.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            Runnable task = new Runnable() {
                ProgressHandle handle = ProgressHandleFactory.createHandle("Compiling the report");
                StyleBuilder boldStyle = DynamicReports.stl.style().bold();
                StyleBuilder alignLeft = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
                
                List<Currencies> currencyList = DataAccess.getCurrencies();
                
                //get the list of active employees in the 
                int total = prl.getEmployeesCollection().size();
                int counter = 0;
                
                
                @Override
                public void run() {
                    handle.start();
                    handle.progress("Determining the number of columns");
                    if(includeAttendance){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        NotifyUtil.info("Date Range", "From "+ sdf.format(from)+" To "+sdf.format(to), false);
                        //Set The Columns
                        //Determine the array length
                        int codesLength = selectedCodes.size();
                        //There are 7 constant columns: 
                        //1. Row Number
                        //2. Surname
                        //3. Other Name
                        //4. Department
                        //5. Basic Pay
                        //6. Attendance
                        //7. Net Currency (optional)
                        ColumnBuilder[] columns = new ColumnBuilder[codesLength+7];
                        String[] columnNames = new String[codesLength+7];
                        
                        if(includeCurrency){
                            columns = new ColumnBuilder[codesLength+7+currencyList.size()];
                            columnNames = new String[codesLength+7+currencyList.size()];
                        }
                        
                        
                        handle.progress("Setting up report columns");
                        TextColumnBuilder<Integer> rowNumber = DynamicReports.col.column("No.","no",DynamicReports.type.integerType()).setStyle(alignLeft).setFixedWidth(22);
                        columns[0] = rowNumber;
                        TextColumnBuilder pcode = DynamicReports.col.column("PR Code","code",DynamicReports.type.stringType());
                        columns[1] = pcode;
                        TextColumnBuilder lname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
                        columns[2] = lname;
                        TextColumnBuilder fname = DynamicReports.col.column("Other Name","fname",DynamicReports.type.stringType());
                        columns[3] = fname;
                        TextColumnBuilder dept = DynamicReports.col.column("Dep't","dept",DynamicReports.type.stringType());
                        columns[4] = dept;
                        TextColumnBuilder basic = DynamicReports.col.column("Basic","basic",DynamicReports.type.bigDecimalType());
                        columns[5] = basic;
                        TextColumnBuilder att = DynamicReports.col.column("Days","att",DynamicReports.type.integerType());
                        columns[6] = att;
                        
                        
                        
                        //The subtotals for money based columns 
                        SubtotalBuilder[] subtotals = new SubtotalBuilder[selectedCodes.size()+1];
                        AggregationSubtotalBuilder[] subtotalsG = new AggregationSubtotalBuilder[selectedCodes.size()+1];
                        if(includeCurrency){
                            subtotals = new SubtotalBuilder[selectedCodes.size()+1+currencyList.size()];
                            subtotalsG = new AggregationSubtotalBuilder[selectedCodes.size()+1+currencyList.size()];
                        }
                        //subtotal for basic
                        subtotals[0]= sbt.sum((TextColumnBuilder)columns[5]);
                        subtotalsG[0] = sbt.sum((TextColumnBuilder)columns[5]);
                        for(int i=0; i<selectedCodes.size(); i++){
                            columns[i+7] = DynamicReports.col.column(selectedCodes.get(i).getReportLabel(),selectedCodes.get(i).getPayrollCodeCode(),DynamicReports.type.doubleType());
                            subtotals[i+1] = sbt.sum((TextColumnBuilder)columns[i+7]);
                            subtotalsG[i+1] = sbt.sum((TextColumnBuilder)columns[i+7]);
                        }
                        if(includeCurrency){
                            
                            String code = selectedcode.getPayrollCodeName().substring(0, 5);
                            for(int i=1; i<= currencyList.size(); i++){
                                columns[codesLength+6+i] = DynamicReports.col.column(code+"-"+currencyList.get(i-1).getCurrencyCode(),currencyList.get(i-1).getCurrencyCode(),DynamicReports.type.doubleType());
                                subtotals[codesLength+i] = sbt.sum((TextColumnBuilder)columns[codesLength+6+i]);
                                subtotalsG[codesLength+i] = sbt.sum((TextColumnBuilder)columns[codesLength+6+i]);
                                
                            }
                            //NotifyUtil.info("Currency Columns", counter+" Columns", false);
                            
                        }
                    
                    
                        handle.progress("Creating Data Source");
                        columnNames[0]="no";
                        columnNames[1]="code";
                        columnNames[2]="surname";
                        columnNames[3]="fname"; //Filling in the initial fields
                        columnNames[4]="dept";
                        columnNames[5]="basic";
                        columnNames[6]="att";
                        if(includeCurrency){
                            
                            for(int i=1; i<= currencyList.size(); i++){
                                columnNames[codesLength+6+i] = currencyList.get(i-1).getCurrencyCode();
                                
                            }
                            //NotifyUtil.info("Currency Column Names", counter+"", false);
                        }
                        
                        for(int i=0;i<selectedCodes.size();i++){
                            columnNames[i+7] = selectedCodes.get(i).getPayrollCodeCode();
                        }
                        NotifyUtil.info("Columns Added", columnNames.length+ " Columns", false);
                        handle.progress("Adding The Data");
                        DRDataSource dataSource = new DRDataSource(columnNames);
                    
                        if(sortByDepartment){
                            int i = 0;
                            List list = DataAccess.getDepartmentIDInPayrollPeriod(period, prl.getPayrollID());
                            //Process per OU
                            for(Object o:list){
                                OrganizationUnits u = DataAccess.entityManager.find(OrganizationUnits.class, o);
                                List<Employees> liste = DataAccess.getEmployeesPerDepartmentIDInPayrollPeriod(period, prl.getPayrollID(), u.getOrganizationUnitID());
                                for(Employees e: liste){
                                    counter = counter + 1;
                                    
                                    handle.progress(counter+" / "+total+" Data For " + e.getSurName()+" "+e.getOtherNames());
                                    if(true){
                                        
                                        Object[] data = new Object[selectedCodes.size()+7];
                                        if(includeCurrency){
                                            data = new Object[selectedCodes.size()+currencyList.size()+7];
                                        }
                                        data[0] = i+1;
                                        data[1] = e.getEmpCode();
                                        data[2] = e.getSurName();
                                        data[3] = e.getOtherNames();
                                        data[4] = u.getOrganizationUnitName();
                                        data[5] = e.getBasicPay();
                                        handle.progress(counter+ " / "+ total+" Attendance For "+ e.getSurName()+" "+e.getOtherNames());
                                        data[6] = DataAccess.getAttendanceDays(e.getEmployeeID(), from, to);
                                        
                                        
                                        for(int j=0;j<selectedCodes.size();j++){
                                            data[j+7] = DataAccess.getPayrollAmount(e, selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                                        }
                                        
                                        if(includeCurrency){
                                            int counter = 0;
                                            for(int k = 1; k<=currencyList.size(); k++){
                                                data[selectedCodes.size()+k+6] = DataAccess.getAmountInCodeCurrency(e.getEmployeeID(), selectedcode.getPayrollCodeID().intValue(), currencyList.get(k-1).getCurrencyID(), period);
                                                counter = k;
                                            }
                                            // NotifyUtil.info("Currency Data", counter+" Sets Added", false);
                                        }
                                        
                                        
                                        dataSource.add(data);
                                        i++;
                                    }
                                
                                }
                            }
                        }else{
                    
                    //List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0 AND e.");
                            List<Employees> list = new ArrayList<>();
                            list.addAll(prl.getEmployeesCollection());
                    
                            for(int i=0; i<list.size(); i++){
                                Object[] data = new Object[selectedCodes.size()+7];
                                if(includeCurrency){
                                    data = new Object[selectedCodes.size()+7+currencyList.size()];
                                }
                                data[0] = i+1;
                                data[1] = list.get(i).getEmpCode();
                                data[2] = list.get(i).getSurName();
                                data[3] = list.get(i).getOtherNames();
                                data[4] = list.get(i).getOrganizationUnitID().getOrganizationUnitName();
                                data[5] = list.get(i).getBasicPay();
                                data[6] = DataAccess.getAttendanceDays(list.get(i).getEmployeeID(),from,to);
                                
                                if(includeCurrency){
                                    for(int k = 1; k<=currencyList.size(); k++){
                                        data[selectedCodes.size()+k+6] = DataAccess.getAmountInCodeCurrency(list.get(i).getEmployeeID(), selectedcode.getPayrollCodeID().intValue(), currencyList.get(k-1).getCurrencyID(), period);
                                    }
                                }
                                
                                                        
                                for(int j=0;j<selectedCodes.size();j++){
                                    data[j+7]=DataAccess.getPayrollAmount(list.get(i), selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                                }
                    
                            dataSource.add(data);
                        }
                    }
                    
                    handle.progress("Finalising");
                    
                    ReportPayroll design = new ReportPayroll(columns, columnNames,subtotals,subtotalsG,dataSource,sortByDepartment,subTotalsAtGroup,subTotalsAtSummary,period,prl,includePageNumbers);
                    JasperReportBuilder report = design.getReport();
                    try {
                        report.show(false);
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (DRException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    handle.finish();
                        
                    }else{
                        //Set The Columns
                        //Determine the array length
                        int codesLength = selectedCodes.size();
                        //There are 7 constant columns: 
                        //1. Row Number
                        //2. Surname
                        //3. Other Name
                        //4. Department
                        //5. Basic Pay
                        //6. Attendance
                        //7. Net Currency (optional)
                        ColumnBuilder[] columns = new ColumnBuilder[codesLength+6];
                        String[] columnNames = new String[codesLength+6];
                        
                        if(includeCurrency){
                            columns = new ColumnBuilder[codesLength+6+currencyList.size()];
                            columnNames = new String[codesLength+6+currencyList.size()];
                        }
                        
                        
                        handle.progress("Setting up report columns");
                        TextColumnBuilder<Integer> rowNumber = DynamicReports.col.column("No.","no",DynamicReports.type.integerType()).setStyle(alignLeft).setFixedWidth(22);
                        columns[0] = rowNumber;
                        TextColumnBuilder pcode = DynamicReports.col.column("PR Code","code",DynamicReports.type.stringType());
                        columns[1] = pcode;
                        TextColumnBuilder lname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
                        columns[2] = lname;
                        TextColumnBuilder fname = DynamicReports.col.column("Other Name","fname",DynamicReports.type.stringType());
                        columns[3] = fname;
                        TextColumnBuilder dept = DynamicReports.col.column("Dep't","dept",DynamicReports.type.stringType());
                        columns[4] = dept;
                        TextColumnBuilder basic = DynamicReports.col.column("Basic","basic",DynamicReports.type.bigDecimalType());
                        columns[5] = basic;
                        //TextColumnBuilder att = DynamicReports.col.column("Days","att",DynamicReports.type.integerType());
                        //columns[5] = att;
                        
                        
                        
                        //The subtotals for money based columns 
                        SubtotalBuilder[] subtotals = new SubtotalBuilder[selectedCodes.size()+1];
                        AggregationSubtotalBuilder[] subtotalsG = new AggregationSubtotalBuilder[selectedCodes.size()+1];
                        if(includeCurrency){
                            subtotals = new SubtotalBuilder[selectedCodes.size()+1+currencyList.size()];
                            subtotalsG = new AggregationSubtotalBuilder[selectedCodes.size()+1+currencyList.size()];
                        }
                        //subtotal for basic
                        subtotals[0]= sbt.sum((TextColumnBuilder)columns[5]);
                        subtotalsG[0] = sbt.sum((TextColumnBuilder)columns[5]);
                        for(int i=0; i<selectedCodes.size(); i++){
                            columns[i+6] = DynamicReports.col.column(selectedCodes.get(i).getReportLabel(),selectedCodes.get(i).getPayrollCodeCode(),DynamicReports.type.doubleType());
                            subtotals[i+1] = sbt.sum((TextColumnBuilder)columns[i+6]);
                            subtotalsG[i+1] = sbt.sum((TextColumnBuilder)columns[i+6]);
                        }
                        if(includeCurrency){
                            int counter=0;
                            String code = selectedcode.getPayrollCodeName().substring(0, 5);
                            for(int i=1; i<= currencyList.size(); i++){
                                columns[codesLength+5+i] = DynamicReports.col.column(code+"-"+currencyList.get(i-1).getCurrencyCode(),currencyList.get(i-1).getCurrencyCode(),DynamicReports.type.doubleType());
                                subtotals[codesLength+i] = sbt.sum((TextColumnBuilder)columns[codesLength+5+i]);
                                subtotalsG[codesLength+i] = sbt.sum((TextColumnBuilder)columns[codesLength+5+i]);
                                counter = i;
                            }
                            //NotifyUtil.info("Currency Columns", counter+" Columns", false);
                            
                        }
                    
                    
                        handle.progress("Creating Data Source");
                        columnNames[0]="no";
                        columnNames[1]="code";
                        columnNames[2]="surname";
                        columnNames[3]="fname"; //Filling in the initial fields
                        columnNames[4]="dept";
                        columnNames[5]="basic";
                        //columnNames[5]="att";
                        if(includeCurrency){
                            int counter=0;
                            for(int i=1; i<= currencyList.size(); i++){
                                columnNames[codesLength+5+i] = currencyList.get(i-1).getCurrencyCode();
                                counter = i;
                            }
                            NotifyUtil.info("Currency Column Names", counter+"", false);
                        }
                        
                        for(int i=0;i<selectedCodes.size();i++){
                            columnNames[i+6] = selectedCodes.get(i).getPayrollCodeCode();
                        }
                        NotifyUtil.info("Columns Added", columnNames.length+ " Columns", false);
                        handle.progress("Adding The Data");
                        DRDataSource dataSource = new DRDataSource(columnNames);
                    
                        if(sortByDepartment){
                            int i = 0;
                            List<OrganizationUnits> list = DataAccess.searchDepartments("SELECT o FROM OrganizationUnits o");
                            //Process per OU
                            for(OrganizationUnits u:list){
                                List<Employees> liste = DataAccess.searchEmployeesPerDepartmentAndPayroll(u.getOrganizationUnitID(), prl.getPayrollID());
                                for(Employees e: liste){
                                    handle.progress("Data For " + e.getSurName()+" "+e.getOtherNames());
                                    if(!e.getIsDisengaged()){
                                        Object[] data = new Object[selectedCodes.size()+6];
                                        if(includeCurrency){
                                            data = new Object[selectedCodes.size()+currencyList.size()+6];
                                        }
                                        data[0] = i+1;
                                        data[1] = e.getEmpCode();
                                        data[2] = e.getSurName();
                                        data[3] = e.getOtherNames();
                                        data[4] = e.getOrganizationUnitID().getOrganizationUnitName();
                                        data[5] = e.getBasicPay();
                                       // handle.progress("Attendance For "+ e.getSurName()+" "+e.getOtherNames());
                                       // data[4] = DataAccess.getAttendanceDays(e.getEmployeeID(), from, to);
                                        
                                        
                                        for(int j=0;j<selectedCodes.size();j++){
                                            data[j+6] = DataAccess.getPayrollAmount(e, selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                                        }
                                        
                                        if(includeCurrency){
                                            int counter = 0;
                                            for(int k = 1; k<=currencyList.size(); k++){
                                                data[selectedCodes.size()+k+5] = DataAccess.getAmountInCodeCurrency(e.getEmployeeID(), selectedcode.getPayrollCodeID().intValue(), currencyList.get(k-1).getCurrencyID(), period);
                                                counter = k;
                                            }
                                            NotifyUtil.info("Currency Data", counter+" Sets Added", false);
                                        }
                                        
                                        
                                        dataSource.add(data);
                                        i++;
                                    }
                                
                                }
                            }
                        }else{
                    
                    //List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0 AND e.");
                            List<Employees> list = new ArrayList<>();
                            list.addAll(prl.getEmployeesCollection());
                    
                            for(int i=0; i<list.size(); i++){
                                Object[] data = new Object[selectedCodes.size()+6];
                                if(includeCurrency){
                                    data = new Object[selectedCodes.size()+6+currencyList.size()];
                                }
                                data[0] = i+1;
                                data[1] = list.get(i).getEmpCode();
                                data[2] = list.get(i).getSurName();
                                data[3] = list.get(i).getOtherNames();
                                data[4] = list.get(i).getOrganizationUnitID().getOrganizationUnitName();
                                data[5] = list.get(i).getBasicPay();
                                //data[5] = DataAccess.getAttendanceDays(list.get(i).getEmployeeID(),from,to);
                                
                                if(includeCurrency){
                                    for(int k = 1; k<=currencyList.size(); k++){
                                                data[selectedCodes.size()+k+5] = DataAccess.getAmountInCodeCurrency(list.get(i).getEmployeeID(), selectedcode.getPayrollCodeID().intValue(), currencyList.get(k-1).getCurrencyID(), period);
                                                
                                            }
                                }
                                
                                
                                
                                for(int j=0;j<selectedCodes.size();j++){
                                    data[j+6]=DataAccess.getPayrollAmount(list.get(i), selectedCodes.get(j).getPayrollCodeID().intValue(), period);
                                }
                    
                            dataSource.add(data);
                        }
                    }
                    
                    handle.progress("Finalising");
                    
                    ReportPayroll design = new ReportPayroll(columns, columnNames,subtotals,subtotalsG,dataSource,sortByDepartment,subTotalsAtGroup,subTotalsAtSummary,period,prl,includePageNumbers);
                    JasperReportBuilder report = design.getReport();
                    try {
                        report.show(false);
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    } catch (DRException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    handle.finish();
                    }
                    
                }
            };
            
            RequestProcessor.getDefault().post(task);

        }
        
    }
    
    
    
     
    
    
}
