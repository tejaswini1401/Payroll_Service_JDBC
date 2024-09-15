package com.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jdbc.mysql.PayrollServiceJDBC.EmployeePayrollDBService;
import com.jdbc.mysql.PayrollServiceJDBC.EmployeePayrollData;
import com.jdbc.mysql.PayrollServiceJDBC.EmployeePayrollException;

class PayrollServiceTest {
    
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieve_ShouldMatchRetrieveCount() {
        EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readData();    
        assertEquals(3, employeePayrollData.size());  
    }
    
    @Test
    public void givenNewSalary_WhenUpdated_ShouldSyncWithDB() throws EmployeePayrollException {
        EmployeePayrollDBService payrollService = new EmployeePayrollDBService();
        payrollService.updateEmployeeSalary("Sami", 2000000.00);
        double salaryInDB = payrollService.getEmployeeSalaryFromDB("Teja");
        assertEquals(3000000.00, salaryInDB);
    }
}
