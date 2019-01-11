/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.payrollreports;

import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPeriods;

/**
 *
 * @author Admin
 */
public class ReportLearning {
    
    TblPeriods period;
    
    public ReportLearning(TblPeriods p){
        this.period = p;
        
    }
    
    public JasperReportBuilder build(){
        JasperReportBuilder report = DynamicReports.report();
        
        StyleBuilder boldStyle = DynamicReports.stl.style().bold();
        StyleBuilder alignLeft = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<Integer> rowNumber = DynamicReports.col.column("No.","no",DynamicReports.type.integerType()).setStyle(alignLeft).setFixedWidth(20);
        TextColumnBuilder lname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
        TextColumnBuilder fname = DynamicReports.col.column("Other Name","fname",DynamicReports.type.stringType());
        TextColumnBuilder basic = DynamicReports.col.column("Basic Pay","basic",DynamicReports.type.bigDecimalType());
        TextColumnBuilder housing = DynamicReports.col.column("Housing","housing",DynamicReports.type.bigDecimalType());
        TextColumnBuilder scharge = DynamicReports.col.column("Other Earnings","scharge",DynamicReports.type.bigDecimalType());
        
        TextColumnBuilder gross = DynamicReports.col.column("Gross Pay","gross",DynamicReports.type.bigDecimalType());
        TextColumnBuilder nssf5 = DynamicReports.col.column("5% NSSF","nssf5",DynamicReports.type.bigDecimalType());
        TextColumnBuilder nssf10 = DynamicReports.col.column("10% NSSF","nssf10",DynamicReports.type.bigDecimalType());
        TextColumnBuilder paye = DynamicReports.col.column("PAYE","paye",DynamicReports.type.bigDecimalType());
        TextColumnBuilder tst = DynamicReports.col.column("Deductions(S)","tst",DynamicReports.type.bigDecimalType());
        TextColumnBuilder deductions = DynamicReports.col.column("Deductions(O)","ddn",DynamicReports.type.bigDecimalType());
        
        
        TextColumnBuilder netpay = DynamicReports.col.column("Net Pay","netpay",DynamicReports.type.bigDecimalType());
            report //create a design
                    //add some style
                    .highlightDetailEvenRows()
                    .columns( //add columns
                            rowNumber,//0
                            lname,//1
                            fname,//2
                            basic,//3
                            housing,//4
                            scharge,//5
                            gross,//6
                            nssf5,//7
                            nssf10,//8
                            paye,//9
                            tst,//10
                            deductions,//11
                            netpay//12
                    )
                    .title(DynamicReports.cmp.text("Payroll Report").setStyle(boldStyle))
                    .pageFooter(DynamicReports.cmp.pageXofY())
                    .setDataSource(createDataSource())
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
                    
            
        
        return report;
    }
    
    private JRDataSource createDataSource(){
        List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged =0");
        DRDataSource dataSource = new DRDataSource(
                "no",//0
                "surname",//1
                "fname",//2
                "basic",//3
                "housing",//4
                "scharge",//5
                "gross",//6
                "nssf5",//7
                "nssf10",//8
                "paye",//9
                "tst",//10
                "ddn",//11
                "netpay"//12
        );
        for(int i=0; i<list.size(); i++){
            dataSource.add(
                    i+1,//0
                    list.get(i).getSurName(),//1
                    list.get(i).getOtherNames(),//2
                    DataAccess.getPayrollAmount(list.get(i), 2, period),//3
                    DataAccess.getPayrollAmount(list.get(i), 3, period),//4
                    DataAccess.getPayrollAmount(list.get(i), 21, period),//5
                    DataAccess.getPayrollAmount(list.get(i), 1, period),//6
                    
                    DataAccess.getPayrollAmount(list.get(i), 9, period),//7
                    DataAccess.getPayrollAmount(list.get(i), 42, period),//8
                    DataAccess.getPayrollAmount(list.get(i), 7, period),//9
                    DataAccess.getPayrollAmount(list.get(i), 4, period),//10
                    DataAccess.getPayrollAmount(list.get(i), 41, period),//11
                    DataAccess.getPayrollAmount(list.get(i), 10, period)//12
                    
                    );
        }
        return dataSource;
    }
    
    
}
