/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.util.List;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;

/**
 *
 * @author Admin
 */
public class ReportPayroll2 {
    
    TblPeriods period;
    
    List<TblPayrollCode> codes;
    
    ColumnBuilder[] columns;
    String[] columnNames;
    DRDataSource data;
    TextColumnBuilder groupBy;
    Boolean sort;
    SubtotalBuilder[] subTotals;
    SubtotalBuilder[] subMTotals;
    Boolean subTotalsAtGroup;
    Boolean subTotalsAtSummary;

    

    
    public ReportPayroll2(ColumnBuilder[] columns,String[] columnNames,SubtotalBuilder[] subtotals,DRDataSource data,Boolean sort,Boolean subTotalsAtGroup, Boolean subTotalsAtSummary,TblPeriods p){
        this.columnNames = columnNames;
        this.columns = columns;
        this.data = data;
        this.sort= sort;
        this.groupBy = (TextColumnBuilder)columns[3];
        this.subTotals = subtotals;
        this.subTotalsAtGroup = subTotalsAtGroup;
        this.subMTotals = subtotals;
        this.subTotalsAtSummary = subTotalsAtSummary;
        this.period = p;
    }
    
    public JasperReportBuilder build(){
        JasperReportBuilder report = DynamicReports.report();
        
        StyleBuilder boldStyle = DynamicReports.stl.style().bold();
            report //create a design
                    //add some style
                    .highlightDetailEvenRows()
                    .columns(columns) 
                    .setDataSource(data)
                    .setSubtotalStyle(boldStyle)
                    
                    .title(DynamicReports.cmp.text("Payroll Report: "+period.getPeriodMonth()+" "+period.getPeriodYear()).setStyle(boldStyle))
                    .pageFooter(DynamicReports.cmp.pageXofY())
                    //.setDataSource(data)
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
            if(sort){
                report.groupBy(groupBy);
            }
            if(subTotalsAtSummary){
                report.subtotalsAtColumnFooter(subMTotals);
            }
            
            
            if(subTotalsAtGroup){
                report.subtotalsAtFirstGroupFooter(subTotals);
            }
            
                    
            
                    
                    
            
                    
            
       return report;
    }
    
    
    
    
}
