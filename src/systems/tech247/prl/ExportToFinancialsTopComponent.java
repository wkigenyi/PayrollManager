/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
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
import systems.tech247.hr.OrganizationUnits;
import systems.tech247.hr.TblPayroll;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;
import systems.tech247.payrollreports.ReportDepartmentTotal;
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
    "CTL_ExportToFinancialsAction=Export To Financials",
    "CTL_ExportToFinancialsTopComponent=Export To Financials",
    "HINT_ExportToFinancialsTopComponent=Export To Financials"
})
public final class ExportToFinancialsTopComponent extends TopComponent implements LookupListener {
    

   
    Calendar calFrom = Calendar.getInstance();
    Date from;
    Date to;
    TblPayrollCode selectedcode; 
    TblPeriods period;
    Boolean sortByDepartment = false;
    Boolean subTotalsAtGroup = false;
    Boolean subTotalsAtSummary = false;
    Boolean includeAttendance = false;
    
    
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
    public ExportToFinancialsTopComponent(){
        this (DataAccess.getCurrentMonth());
        
    }
    
    public ExportToFinancialsTopComponent(TblPeriods p) {
        initComponents();
        setName(Bundle.CTL_ExportToFinancialsTopComponent());
        setToolTipText(Bundle.HINT_ExportToFinancialsTopComponent());
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

        jtPeriod = new javax.swing.JTextField();
        jlCodeCounter = new javax.swing.JLabel();
        jlCount = new javax.swing.JLabel();
        jbDataExport = new javax.swing.JButton();

        jtPeriod.setText(org.openide.util.NbBundle.getMessage(ExportToFinancialsTopComponent.class, "ExportToFinancialsTopComponent.jtPeriod.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jlCodeCounter, org.openide.util.NbBundle.getMessage(ExportToFinancialsTopComponent.class, "ExportToFinancialsTopComponent.jlCodeCounter.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jlCount, org.openide.util.NbBundle.getMessage(ExportToFinancialsTopComponent.class, "ExportToFinancialsTopComponent.jlCount.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jbDataExport, org.openide.util.NbBundle.getMessage(ExportToFinancialsTopComponent.class, "ExportToFinancialsTopComponent.jbDataExport.text")); // NOI18N
        jbDataExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDataExportActionPerformed(evt);
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
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlCodeCounter)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jbDataExport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jlCount))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCodeCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbDataExport)
                .addGap(18, 18, 18)
                .addComponent(jlCount)
                .addContainerGap(345, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbDataExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDataExportActionPerformed
    
        ProgressHandle ph = ProgressHandleFactory.createHandle("Creating Export File");
        RequestProcessor.getDefault().post(new Runnable() {
            @Override
            public void run() {
                ph.start();
                
                
                ph.progress("Determining last day of the period");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, period.getPeriodYear());
                cal.set(Calendar.MONTH, DataAccess.covertMonthsToInt(period.getPeriodMonth())-1);
                int month = cal.get(Calendar.MONTH);
                while(cal.get(Calendar.MONTH)==month){
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                cal.add(Calendar.DATE, -1);
                String date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
                
                FileWriter fileWriter = null;
                String DELIMETER = "|";
                String NEW_LINE_SEPARATOR = "\r\n";
                String filename = "C:/PayrollExports/PRL"+period.getPeriodYear()+period.getPeriodMonth()+".SUN";
                int rowCounter = 0;
                int fieldCounter  = 0;
                try{
                    fileWriter = new FileWriter(filename);
                    ph.progress("Querying Database..");
                    List data = DataAccess.getDepartmentTotalMonthlyData(period);
                    
                    for(int i=0; i<data.size();i++){
                        rowCounter = i+1;
                        ph.progress("Working on entry "+rowCounter+" / "+data.size());
                        Object[] entry = (Object[])data.get(i);
                        entry[2] = date;
                        for(int j=0;j<entry.length;j++){
                            fieldCounter = j+1;
                            
                            ph.progress("Working on entry "+rowCounter+" / "+data.size()+" field "+fieldCounter+"/"+entry.length);
                            try{
                                fileWriter.append(entry[j].toString());
                            }catch(NullPointerException ex){
                                fileWriter.append("0");
                            }
                            fileWriter.append(DELIMETER);
                        }
                        fileWriter.append(NEW_LINE_SEPARATOR);
                    }
                    ph.progress("Done");
                    ph.finish();
                    StatusDisplayer.getDefault().setStatusText("Export File Generated "+filename);
                    
                    
                }catch(Exception ex){
                    NotifyUtil.error("An Error Occured", "There was an error", ex, false);
                }finally{
                    try{
                        fileWriter.flush();
                        fileWriter.close();
                    }catch(IOException ex){
                        
                    }
                }
            }
        });
        
    }//GEN-LAST:event_jbDataExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbDataExport;
    private javax.swing.JLabel jlCodeCounter;
    private javax.swing.JLabel jlCount;
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
                
                
            }else if(o instanceof TblPeriods){
                period = (TblPeriods)o;
                jtPeriod.setText(period.getPeriodYear()+" "+period.getPeriodMonth());
                
            }
        }
        
        
        
        
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        void runReport(TblPayroll prl) throws DRException{
        
        
        //from = jdcFrom.getDate();
        //to = jdcTo.getDate();

        NotifyDescriptor nd = new NotifyDescriptor.Confirmation("Run Department Totals For  "+ period.getPeriodMonth()+" "+ period.getPeriodYear());
        if(DialogDisplayer.getDefault().notify(nd)==NotifyDescriptor.YES_OPTION){
            Runnable task = new Runnable() {
                ProgressHandle handle = ProgressHandleFactory.createHandle("Compiling the report");
                StyleBuilder boldStyle = DynamicReports.stl.style().bold();
                StyleBuilder alignLeft = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
                
                //get the list of active employees in the 
                           
                
                @Override
                public void run() {
                    handle.start();
                    handle.progress("Determining the number of columns");
                    
                        //Set The Columns
                        //Determine the array length
                        
                        ColumnBuilder[] columns = new ColumnBuilder[7];
                        String[] columnNames = new String[7];
                                                                                                
                        handle.progress("Setting up report columns");
                        TextColumnBuilder dept = DynamicReports.col.column("Department","dept",DynamicReports.type.stringType());
                        columns[0] = dept;
                        TextColumnBuilder deptcode = DynamicReports.col.column("Dept Code","deptcode",DynamicReports.type.stringType());
                        columns[1] = deptcode;
                        TextColumnBuilder year = DynamicReports.col.column("Year","year",DynamicReports.type.stringType());
                        columns[2] = year;
                        TextColumnBuilder month = DynamicReports.col.column("Month","month",DynamicReports.type.integerType());
                        columns[3] = month;
                        TextColumnBuilder pcode = DynamicReports.col.column("Payroll Code","pcode",DynamicReports.type.stringType());
                        columns[4] = pcode;
                        TextColumnBuilder acc = DynamicReports.col.column("Account","acc",DynamicReports.type.stringType());
                        columns[5] = acc;
                        TextColumnBuilder amount = DynamicReports.col.column("Amount","amount",DynamicReports.type.stringType());
                        columns[6] = amount;
                        
                        
                        
                        
                            
                        
                    
                    
                        handle.progress("Creating Data Source");
                        columnNames[0]="dept";
                        columnNames[1]="deptcode";
                        columnNames[2]="year"; //Filling in the initial fields
                        columnNames[3]="month";
                        columnNames[4]="pcode";
                        columnNames[5]="acc";
                        columnNames[6]="amount";
                        //columnNames[5]="att";
                             
                        
                        //NotifyUtil.info("Columns Added", columnNames.length+ " Columns", false);
                        handle.progress("Adding The Data");
                        DRDataSource dataSource = new DRDataSource(columnNames);
                    
                                    
                        //List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged = 0 AND e.");
                            List<OrganizationUnits> list = new ArrayList<>();
                            list.addAll(DataAccess.searchDepartments("SELECT p from OrganizationUnits p"));
                    
                            for(int i=0; i<list.size(); i++){
                                for(TblPayrollCode p: selectedCodes){
                                    Object[] data = DataAccess.getDepartmentTotalData(list.get(i).getOrganizationUnitID(),p.getPayrollCodeID().intValue(), period);
                                    //StatusDisplayer.getDefault().setStatusText("Account is: "+ (String)data[5]);
                                    if(null != data){
                                        //Object[] datatoAdd = new Object[7];
                                        //datatoAdd[0] = (String)data[0];
                                        
                                        dataSource.add(data);
                                    }
                                    
                                }
                                
                            }
                    
                    
                    handle.progress("Finalising");
                    
                    ReportDepartmentTotal design = new ReportDepartmentTotal(columns, columnNames,dataSource);
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
