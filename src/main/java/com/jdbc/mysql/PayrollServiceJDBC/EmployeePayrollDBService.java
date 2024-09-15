package com.jdbc.mysql.PayrollServiceJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	
	private Connection getConnection()throws SQLException{
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "Tejaswini@1415";
		Connection connection;
		System.out.println("Connection to database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful !"+connection);
		return connection;
	}
	public List<EmployeePayrollData> readData(){
		String sql = "SELECT * FROM Employee;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	public int updateEmployeeSalary(String name, double salary) throws EmployeePayrollException {
        String sql = "UPDATE Employee SET salary = ? WHERE name = ?";
        try (Connection connection = this.getConnection();
   			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

   			preparedStatement.setDouble(1, salary);
   			preparedStatement.setString(2, name);

   			int rowsAffected = preparedStatement.executeUpdate();
   			if (rowsAffected == 0) {
   				throw new EmployeePayrollException("Employee " + name + " not found.");
   			}
   			return rowsAffected;
   		} catch (SQLException e) {
   			throw new EmployeePayrollException("Unable to update salary for " + name);
   		}
    }
	
	public double getEmployeeSalaryFromDB(String name) throws EmployeePayrollException {
        String sql = "SELECT salary FROM Employee WHERE name = ?";
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("salary");
            } else {
                throw new EmployeePayrollException("Employee " + name + " not found.");
            }
        } catch (SQLException e) {
            throw new EmployeePayrollException("Unable to retrieve salary for " + name);
        }
    }

	public static void main(String[] args) throws EmployeePayrollException {
		EmployeePayrollDBService obj = new EmployeePayrollDBService();
		List<EmployeePayrollData> data = obj.readData();
		System.out.println("Before update: " + data + "\n");

		int rowsAffected = obj.updateEmployeeSalary("Teja", 3000000.00);
		System.out.println("Rows affected: " + rowsAffected);

		List<EmployeePayrollData> updatedData = obj.readData();
		System.out.println("After update: " + updatedData + "\n");

	}
}
