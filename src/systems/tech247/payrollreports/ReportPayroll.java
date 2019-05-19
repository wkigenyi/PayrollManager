/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.payrollreports;

import java.io.IOException;
import java.util.List;
import net.sf.dynamicreports.adhoc.AdhocManager;
import net.sf.dynamicreports.adhoc.configuration.AdhocConfiguration;
import net.sf.dynamicreports.adhoc.configuration.AdhocReport;
import net.sf.dynamicreports.adhoc.report.DefaultAdhocReportCustomizer;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.TblPayroll;
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;

/**
 *
 * @author Wilfred
 */
public class ReportPayroll {
    
    TblPayroll payroll;
    TblPeriods period;
    List<TblPayrollCode> codes;
    ColumnBuilder[] columns;
    String[] columnNames;
    DRDataSource data;
    TextColumnBuilder groupBy;
    Boolean sort;
    SubtotalBuilder[] subTotals;
    Boolean subTotalsAtGroup;
    Boolean subTotalsAtSummary;
    AggregationSubtotalBuilder[] subtotalsG;
    JasperReportBuilder reportBuilder;
    boolean includePageNumbers;

    public ReportPayroll(ColumnBuilder[] columns,String[] columnNames,SubtotalBuilder[] subtotals,AggregationSubtotalBuilder[] subtotalsG,DRDataSource data,Boolean sort,Boolean subTotalsAtGroup, Boolean subTotalsAtSummary,TblPeriods p, TblPayroll payroll,boolean includePageNumbers) {
        this.columnNames = columnNames;
        this.columns = columns;
        this.data = data;
        this.sort= sort;
        this.groupBy = (TextColumnBuilder)columns[4];
        this.subTotals = subtotals;
        this.subtotalsG = subtotalsG;
        this.subTotalsAtGroup = subTotalsAtGroup;
        this.subTotalsAtSummary = subTotalsAtSummary;
        this.period = p;
        this.payroll = payroll;
        this.includePageNumbers = includePageNumbers;
        build();
    }
    
    private void build(){
        AdhocConfiguration configuration = new AdhocConfiguration();
                          
	//Start;
	AdhocReport report = new AdhocReport();
	configuration.setReport(report);
            try{
                reportBuilder = AdhocManager.createReport(configuration.getReport(),new ReportCustomizer());
                reportBuilder.setTemplate(ReportTemplate.reportTemplate)
                    .columns(columns)
                    .setDataSource(data)
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
                if(sort){
                    reportBuilder.groupBy(groupBy);
                }
                
            
            
                if(subTotalsAtGroup){
                    reportBuilder.subtotalsAtFirstGroupFooter(subTotals);
                }
                
                if(true){
                    reportBuilder.subtotalsAtSummary(subtotalsG);
                }
                if(columns.length >= 13){
                    reportBuilder.setPageFormat(PageType.A3,PageOrientation.LANDSCAPE);
                }
            
            }catch(DRException ex){
                
            }
        
        
    }
    
            private class ReportCustomizer extends DefaultAdhocReportCustomizer {

		/**
		 * If you want to add some fixed content to a report that is not needed to store in the xml file.
		 * For example you can add default page header, footer, default fonts,...
		 */
		@Override
		public void customize(ReportBuilder<?> report, AdhocReport adhocReport) throws DRException {
			super.customize(report, adhocReport);
			// default report values
                        try{
                            report.title(ReportTemplate.createTitleComponent(DataAccess.getDefaultCompany(), period,"Payroll Report",payroll.getPayrollName()));
                        }catch(IOException ex){
                        }
			report.setTemplate(ReportTemplate.reportTemplate);
                        if(includePageNumbers){
                            report.pageFooter(ReportTemplate.footerComponent);
                        }    
		}

		

		

	}
            
        public JasperReportBuilder getReport(){
            return reportBuilder;
        }
    
}
