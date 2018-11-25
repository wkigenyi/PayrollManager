/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import net.sf.dynamicreports.adhoc.AdhocManager;
import net.sf.dynamicreports.adhoc.configuration.AdhocConfiguration;
import net.sf.dynamicreports.adhoc.configuration.AdhocReport;
import net.sf.dynamicreports.adhoc.report.DefaultAdhocReportCustomizer;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.ReportBuilder;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
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
    
    TblPeriods period;
    JasperReportBuilder reportBuilder;
    
    public ReportPaySlip(TblPeriods period,Employees employee){
        this.emp = employee;
        this.period=  period;
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
                            .columns(
                                    DynamicReports.col.column("field",DynamicReports.type.stringType()),
                                    DynamicReports.col.column("value",DynamicReports.type.stringType())
                                    )
                            .setDataSource(paySlipData())
                            .toPdf(new FileOutputStream("C:/Payslips/PaySlip.pdf"));
                            
                            
                            
                            
                    
                }catch(DRException | FileNotFoundException ex){
                    StatusDisplayer.getDefault().setStatusText(ex.getMessage());
                }
	}
        
        
        
        private JRDataSource paySlipData() {
		/*DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");
		for (int i = 0; i < 15; i++) {
			dataSource.add("Book", new Date(), (int) (Math.random() * 10) + 1, new BigDecimal(Math.random() * 100 + 1));
		}
		for (int i = 0; i < 20; i++) {
			dataSource.add("PDA", new Date(), (int) (Math.random() * 10) + 1, new BigDecimal(Math.random() * 100 + 1));
		}*/
                NumberFormat nf = new DecimalFormat("#,##0.00");
                DRDataSource dataSource =  new DRDataSource("empName","empNumber","deptName","basicPay","grossPay","totalEarnings","paye","nssf","netPay");
                dataSource.add(
                        emp.getSurName()+" "+emp.getOtherNames() , //Employee Name
                        emp.getEmpCode(),//Employee Number
                        emp.getOrganizationUnitID().getOrganizationUnitName(),//Department
                        nf.format(emp.getBasicPay()),//Basic Pay
                        
                        nf.format(0),//Gross Pay
                        nf.format(0),//Total Earnings
                        nf.format(0),//Paye
                        nf.format(0),//nssf
                        nf.format(0)//Net Pay
                );
                
                DRDataSource payslipData = new DRDataSource("field","value");
                payslipData.add("EMPLOYEE NUMBER:",emp.getEmpCode());
                payslipData.add("EMPLOYEE NAME:",emp.getSurName()+" "+emp.getOtherNames());
                payslipData.add("DEPARTMENT:",emp.getOrganizationUnitID().getOrganizationUnitName());
                payslipData.add("EARNINGS");
                payslipData.add("BASIC PAY:",nf.format(emp.getBasicPay()));
                payslipData.add("SERVICE CHARGE:",nf.format(0));
                payslipData.add("GROSS PAY:",nf.format(0));
                payslipData.add("TOTAL EARNINGS:",nf.format(0));
                payslipData.add("DEDUCTIONS");
                payslipData.add("NSSF:",nf.format(0));
                payslipData.add("PAYE:",nf.format(0));
                payslipData.add("TOTAL DEDUCTIONS:",nf.format(0));
                payslipData.add("NET PAY:",nf.format(0));
		return payslipData;
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
			report.setTemplate(ReportTemplate.reportTemplate);
                        try{
                            report.title(ReportTemplate.createTitleComponent(DataAccess.getDefaultCompany(),period,"Payslip"));
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
