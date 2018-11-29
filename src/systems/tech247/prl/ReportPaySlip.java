/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.sf.dynamicreports.adhoc.AdhocManager;
import net.sf.dynamicreports.adhoc.configuration.AdhocConfiguration;
import net.sf.dynamicreports.adhoc.configuration.AdhocReport;
import net.sf.dynamicreports.adhoc.report.DefaultAdhocReportCustomizer;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.openide.awt.StatusDisplayer;
import systems.tech247.dbaccess.DataAccess;
import systems.tech247.hr.Employees;
import systems.tech247.hr.TblPeriods;

/**
 *
 * @author Admin
 */
public class ReportPaySlip {
    Employees emp;
    
    
    JasperReportBuilder reportBuilder;
    JRDataSource payslipData;
    TblPeriods period;
    public ReportPaySlip(Employees emp,JRDataSource data,TblPeriods period){
        this.emp = emp;
        this.period = period;
        this.payslipData = data;
        build();
    }
    
    
    private void build() {
                
                AdhocConfiguration configuration = new AdhocConfiguration();
                          
		//Start;
		AdhocReport report = new AdhocReport();
		configuration.setReport(report);
                
		
                
                
                try{
                    reportBuilder = AdhocManager.createReport(configuration.getReport(),new ReportCustomizer());
                    reportBuilder.setTemplate(ReportTemplate.reportTemplate)
                            
                            .setPageFormat(PageType.A5)
//                            .addDetail(DynamicReports.cmp.verticalList(
//                                    DynamicReports.cmp.text("Name : "+emp.getSurName()+" "+emp.getOtherNames()),
//                                    DynamicReports.cmp.text("PR No: "+emp.getEmpCode()),
//                                    DynamicReports.cmp.text("Dept : "+emp.getOrganizationUnitID().getOrganizationUnitName())
//                            ))
                            
           
                            .columns(
                                    DynamicReports.col.column("name",DynamicReports.type.stringType()),
                                    DynamicReports.col.column("amount",DynamicReports.type.bigDecimalType())
                                    )
                            .setDataSource(payslipData)
                            .toPdf(new FileOutputStream("C:/Payslips/PaySlip.pdf"));
                            
                            
                            
                            
                    
                }catch(DRException | FileNotFoundException ex){
                    StatusDisplayer.getDefault().setStatusText(ex.getMessage());
                }
	}
        
        
        
        
        
        private class ReportCustomizer extends DefaultAdhocReportCustomizer {

		/**
		 * If you want to add some fixed content to a report that is not needed to store in the xml file.
		 * For example you can add default page header, footer, default fonts,...
		 */
            
                 VerticalListBuilder employeeDetails = DynamicReports.cmp.verticalList(
                         DynamicReports.cmp.text("NAME       : "+emp.getSurName()+" "+emp.getOtherNames()),
                         DynamicReports.cmp.text("PAYROLL NO : "+ emp.getEmpCode()),
                         DynamicReports.cmp.text("DEPARTMENT : "+emp.getOrganizationUnitID().getOrganizationUnitName())
                         );
            
		@Override
		public void customize(ReportBuilder<?> report, AdhocReport adhocReport) throws DRException {
			super.customize(report, adhocReport);
			// default report values
			report.setTemplate(ReportTemplate.reportTemplate);
                        try{
                            report.title(
                                    ReportTemplate.createTitleComponent(DataAccess.getDefaultCompany(),period,"Payslip"),
                                    employeeDetails
                            );
                        }catch(IOException ex){
                            
                        }
			// a fixed page footer that user cannot change, this customization is not stored in the xml file
			report.pageFooter(ReportTemplate.footerComponent);
		}

		

		

	}
        
        public JasperReportBuilder getReport(){
            return reportBuilder;
        }
}
