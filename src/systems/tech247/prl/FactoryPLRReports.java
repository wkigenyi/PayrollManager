/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import systems.tech247.util.SetupItem;
import systems.tech247.util.NodeSetupItem;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;
import javax.swing.AbstractAction;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPeriods;
import systems.tech247.payrollreports.ReportTransactionPreview;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author WKigenyi
 */
public class FactoryPLRReports extends ChildFactory<SetupItem> {
    
    @Override
    protected boolean createKeys(List<SetupItem> toPopulate) {

        toPopulate.add(new SetupItem("Payroll Report",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new ReportEditorTopComponent();
                tc.open();
                tc.requestActive();
            }
        },"systems/tech247/util/icons/capex.png"));
        
        toPopulate.add(new SetupItem("Transaction Preview",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProgressHandle ph = ProgressHandle.createHandle("Generating Report");
                RequestProcessor.getDefault().post(new Runnable() {
                    @Override
                    public void run() {
                        ph.start();
                        TblPeriods period = DataAccess.getCurrentMonth();
                        int month = DataAccess.covertMonthsToInt(period.getPeriodMonth());
                        ph.progress("Creating the Query");
                        
                        String sql = ""
                                + "SELECT empCode, SurName,OtherNames,ou.OrganizationUnitName,BasicPay,"
                                + "dbo.prlFnGetOtherPayments(e.EmployeeID,?,?) AS OtherPayment,dbo.prlFnGetDeductions(e.EmployeeID,?,?) AS Deductions FROM Employees e LEFT JOIN OrganizationUnits ou ON e.OrganizationUnitID = ou.OrganizationUnitID WHERE IsDisengaged = 0";
                        
                        
                        Query query = DataAccess.entityManager.createNativeQuery(sql);
                        query.setParameter(1, month);
                        query.setParameter(2, period.getPeriodYear());
                        query.setParameter(3, month);
                        query.setParameter(4, period.getPeriodYear());
                        
                        ColumnBuilder[] columns = new ColumnBuilder[7];
                        String[] columnNames = new String[7];
                        
                        ph.progress("The Columns");
                        
                        TextColumnBuilder<String> empcode = DynamicReports.col.column("PR Code","code",DynamicReports.type.stringType());
                        columns[0] = empcode;
                        columnNames[0] = "code";
                        TextColumnBuilder<String> surname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
                        columns[1] = surname;
                        columnNames[1] = "surname";
                        TextColumnBuilder<String> othername = DynamicReports.col.column("Other-Name","othername",DynamicReports.type.stringType());
                        columns[2] = othername;
                        columnNames[2] = "othername";
                        TextColumnBuilder<String> dept = DynamicReports.col.column("Department","dept",DynamicReports.type.stringType()).setWidth(200);
                        columns[3] = dept;
                        columnNames[3] = "dept";
                        TextColumnBuilder<BigDecimal> basic = DynamicReports.col.column("Basic Pay","basic",DynamicReports.type.bigDecimalType());
                        columns[4] = basic;
                        columnNames[4] = "basic";
                        TextColumnBuilder<Double> otherpay = DynamicReports.col.column("Other Pay","otherpay",DynamicReports.type.doubleType());
                        columns[5] = otherpay;
                        columnNames[5] = "otherpay";
                        TextColumnBuilder<Double> deductions = DynamicReports.col.column("Deductions","deductions",DynamicReports.type.doubleType());
                        columns[6] = deductions;
                        columnNames[6] = "deductions";
                        
                        ph.progress("The DataSource");
                        DRDataSource dataSource = new DRDataSource(columnNames);
                        List data = query.getResultList();
                        
                        for(Object o : data){
                            dataSource.add((Object[])o);
                        }
                        
                        ph.progress("Almost Done");
                        ReportTransactionPreview design = new ReportTransactionPreview(columns, dataSource, period);
                        JasperReportBuilder report = design.getReport();
                        try{
                            report.show(false);
                        }catch(Exception ex){
                            NotifyUtil.error("There was an error", "There was an error", ex, false);
                        }
                        
                        ph.finish();
                        
                    }
                });
            }
        },"systems/tech247/util/icons/capex.png"));
        
        toPopulate.add(new SetupItem("Export To Finacials",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopComponent tc = new ExportToFinancialsTopComponent();
                tc.open();
                tc.requestActive();
            }
        },"systems/tech247/util/icons/capex.png"));
        toPopulate.add(new SetupItem("Payroll Code Variance",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        },"systems/tech247/util/icons/capex.png"));
        
        
        
        return true;
    }
    
    @Override
    protected Node createNodeForKey(SetupItem key) {
        
        Node node =  null;
        try {
            
            node = new NodeSetupItem(key);
            
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }
}
