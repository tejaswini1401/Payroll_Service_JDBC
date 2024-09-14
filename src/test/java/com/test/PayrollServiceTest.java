package com.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jdbc.mysql.PayrollServiceJDBC.EmployeePayrollDBService;
import com.jdbc.mysql.PayrollServiceJDBC.EmployeePayrollData;

class PayrollServiceTest {
    
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieve_ShouldMatchRetrieveCount() {
        EmployeePayrollDBService employeePayrollService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readData();
        
        assertEquals(3, employeePayrollData.size());  
    }
}
