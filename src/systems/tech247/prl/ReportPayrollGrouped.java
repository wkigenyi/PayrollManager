/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

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
import systems.tech247.hr.TblPayrollCode;
import systems.tech247.hr.TblPeriods;
import systems.tech247.util.NotifyUtil;

/**
 *
 * @author Admin
 */
public class ReportPayrollGrouped {
    
    TblPeriods period;
    
    List<TblPayrollCode> codes;
    
    public ReportPayrollGrouped(TblPeriods p){
        this.period = p;
        
    }
    
    public ReportPayrollGrouped(List pCodes,TblPeriods p){
        this.period = p;
        this.codes= pCodes;
    }
    
    public JasperReportBuilder build(){
        JasperReportBuilder report = DynamicReports.report();
        
        StyleBuilder boldStyle = DynamicReports.stl.style().bold();
        StyleBuilder alignLeft = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        TextColumnBuilder<Integer> rowNumber = DynamicReports.col.column("No.","no",DynamicReports.type.integerType()).setStyle(alignLeft).setFixedWidth(20);
        TextColumnBuilder lname = DynamicReports.col.column("Sur-Name","surname",DynamicReports.type.stringType());
        TextColumnBuilder fname = DynamicReports.col.column("Other Name","fname",DynamicReports.type.stringType());
        TextColumnBuilder dept = DynamicReports.col.column("Department","dept",DynamicReports.type.stringType());
            report //create a design
                    //add some style
                    .highlightDetailEvenRows()
                    .columns( //add columns
                            rowNumber,//0
                            lname,//1
                            fname,//2
                            dept
  
                    )
                    .groupBy(dept)
                    .title(DynamicReports.cmp.text("Payroll Report").setStyle(boldStyle))
                    .pageFooter(DynamicReports.cmp.pageXofY())
                    .setDataSource(createDataSource())
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
                    
            for(TblPayrollCode code: codes){
                report.columns().addColumn(DynamicReports.col.column(code.getPayrollCodeName(),code.getPayrollCodeCode(),DynamicReports.type.bigDecimalType()));
            }
                    
                    
            
                    
            
        
        return report;
    }
    
    private JRDataSource createDataSource(){
        int width = codes.size();
        //Add the initial fields Row Number,Last Name,First Name
        String[] columns = new String[width+4]; //The 3 to take care of the initial fields
        columns[0]="no";
        columns[1]="surname";
        columns[2]="fname"; //Filling in the initial fields
        columns[3]="dept"; //Filling in the initial fields
        for(int i=0;i<width;i++){
            columns[i+4] = codes.get(i).getPayrollCodeCode();
        }
        
        List<Employees> list = DataAccess.searchEmployees("SELECT e FROM Employees e WHERE e.isDisengaged =0");
  
        
        DRDataSource dataSource = new DRDataSource(columns);
      
        
        
        for(int i=0; i<list.size(); i++){
            Object[] data = new Object[width+4];
            data[0] = i+1;
            data[1] = list.get(i).getSurName();
            data[2] = list.get(i).getOtherNames();
            data[3] = list.get(i).getOrganizationUnitID().getOrganizationUnitName();
               
            
            for(int j=0;j<codes.size();j++){
                data[j+4]=DataAccess.getPayrollAmount(list.get(i), codes.get(j).getPayrollCodeID().intValue(), period);
            }
                    
            dataSource.add(data);
                    
        }
        return dataSource;
    }
    
    
}
