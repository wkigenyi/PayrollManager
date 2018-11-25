/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.tech247.prl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Property;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.lookup.Lookups;


/**
 *
 * @author WKigenyi
 */
public class MonthPayslipNode  extends BeanNode<PaidPeriod>{
    
   
    
    public MonthPayslipNode(PaidPeriod bean) throws IntrospectionException{
        super(bean,Children.LEAF,Lookups.singleton(bean));
        String month = new DateFormatSymbols().getMonths()[bean.getMonth().getMonth()-1];
        setDisplayName(bean.getYr()+" "+month);
        setIconBaseWithExtension("systems/tech247/util/icons/Calendar.png");
    }
    
    @Override
    protected Sheet createSheet(){
        final PaidPeriod em = getLookup().lookup(PaidPeriod.class);
        Sheet s = super.createSheet();
        Sheet.Set basic = Sheet.createExpertSet();
        basic.setDisplayName("Earnings");
        Sheet.Set deductions = Sheet.createExpertSet();
        deductions.setName("Deductions");
        deductions.setDisplayName("Deductions");
        
        Sheet.Set taxCalculations = Sheet.createExpertSet();
        taxCalculations.setName("Tax Calculations");
        taxCalculations.setDisplayName("Tax Calculations");
        
        Sheet.Set summary = Sheet.createExpertSet();
        summary.setName("Summary");
        summary.setDisplayName("Summary");
        
        
        Sheet.Set taxes = Sheet.createExpertSet();
        taxes.setName("Taxes");
        taxes.setDisplayName("Taxes");
        try {
            Property sBasicProperty;
            sBasicProperty = new PropertySupport.Reflection(em,String.class,"getBasicPay",null);
            sBasicProperty.setDisplayName("Basic Salary");
            
            Property sNameProperty;
            sNameProperty = new PropertySupport.Reflection(em,String.class,"gethAllowance",null);
            sNameProperty.setDisplayName("Housing Allowance");
            
            Property sChargeProperty;
            sChargeProperty = new PropertySupport.Reflection(em,String.class,"getServiceCharge",null);
            sChargeProperty.setDisplayName("Service Charge");
            
            Property aTransportProperty;
            aTransportProperty = new PropertySupport.Reflection(em,String.class,"getTransportAllowance",null);
            aTransportProperty.setDisplayName("Transport Allowance");
            
            Property oosProperty = new PropertySupport.ReadOnly<String>(
                    "Out Of Station",
                    String.class,
                    "Out Of Station",
                    "OOS"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getOutOfStation();
                }
                
            };
            
            Property advanceProperty = new PropertySupport.ReadOnly<String>(
                    "Advance",
                    String.class,
                    "Advance",
                    "Advance"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getAdvance();
                }
                
            };
            
            
            
            
            
            Property aCitylegerProperty;
            aCitylegerProperty = new PropertySupport.Reflection(em,String.class,"getCityLedger",null);
            aCitylegerProperty.setDisplayName("City Ledger");
            
            
            
            
            
            Property tPAYE = new PropertySupport.ReadOnly<String>(
                    "PAYE",
                    String.class,
                    "P.A.Y.E",
                    "Pay As You Earn"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getPAYE();
                }
                
            };
            
            Property tNSSF = new PropertySupport.ReadOnly<String>(
                    "NSSF",
                    String.class,
                    "N.S.S.F",
                    "National Social Security Fund"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getNSSF();
                }
                
            };
            
            Property dRambi = new PropertySupport.ReadOnly<String>(
                    "Rambi Rambi",
                    String.class,
                    "Rambi Rambi",
                    "Munno Mukabi"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getRambiRambi();
                }
                
            };
            
            Property dSacco = new PropertySupport.ReadOnly<String>(
                    "LVSR SACCO",
                    String.class,
                    "LVSR SACCO",
                    "SACCO"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getLvsrSACCO();
                }
                
            };
            
            
            
            Property taxableProperty = new PropertySupport.ReadOnly<String>(
                    "Taxable Amount",
                    String.class,
                    "Taxable Amount",
                    "Taxable"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getTaxablePay();
                }
                
            };
            
            Property taxedProperty = new PropertySupport.ReadOnly<String>(
                    "Taxed Amount",
                    String.class,
                    "Taxed Amount",
                    "Taxed"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getTaxCharged();
                }
                
            };
            
            Property grossProperty = new PropertySupport.ReadOnly<String>(
                    "Gross Pay",
                    String.class,
                    "Gross Pay",
                    "Gross"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getGrossPay();
                }
                
            };
            
            Property netProperty = new PropertySupport.ReadOnly<String>(
                    "Net Pay",
                    String.class,
                    "Net Pay",
                    "Net"
            ){
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return em.getNetPay();
                }
                
            };
            
            
            
            basic.put(sBasicProperty);
            basic.put(sNameProperty);
            basic.put(sChargeProperty);
            basic.put(aTransportProperty);
            basic.put(oosProperty);
            
            deductions.put(aCitylegerProperty);
            deductions.put(tNSSF);
            deductions.put(advanceProperty);
            deductions.put(dRambi);
            deductions.put(dSacco);
            
            taxCalculations.put(taxableProperty);
            taxCalculations.put(taxedProperty);
            
            taxes.put(tPAYE);
            
            summary.put(grossProperty);
            summary.put(netProperty);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MonthPayslipNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        s.put(basic);
        s.put(deductions);
        s.put(taxCalculations);
        s.put(taxes);
        s.put(summary);
        return s;
    }
    
}
