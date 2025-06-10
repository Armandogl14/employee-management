package edu.pucmm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.pucmm.exception.InvalidSalaryException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.DuplicateEmployeeException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024 - 00:47
 */

public class EmployeeManagerTest {

   private EmployeeManager employeeManager;
   private Position juniorDeveloper;
   private Position seniorDeveloper;
   private Employee employee1;
   private Employee employee2;

   @BeforeEach
   public void setUp() {
      employeeManager = new EmployeeManager();
      juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
      seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
      employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
      employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
      employeeManager.addEmployee(employee1);
   }

   @Test
   public void testAddEmployee() {
      // TODO: Agregar employee2 al employeeManager y verificar que se agregó
      // correctamente.
      // - Verificar que el número total de empleados ahora es 2.
      // - Verificar que employee2 está en la lista de empleados.
      employeeManager.addEmployee(employee2);
      assertTrue(employeeManager.getEmployees().size() == 2 && employeeManager.getEmployees().contains(employee2));
   }

   @Test
   public void testRemoveEmployee() {
      // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó
      // correctamente.
      // - Agregar employee2 al employeeManager.
      // - Eliminar employee1 del employeeManager.
      // - Verificar que el número total de empleados ahora es 1.
      // - Verificar que employee1 ya no está en la lista de empleados.
      employeeManager.addEmployee(employee2);
      employeeManager.removeEmployee(employee1);
      assertTrue(employeeManager.getEmployees().size() == 1 && !employeeManager.getEmployees().contains(employee1));
   }

   @Test
   public void testCalculateTotalSalary() {
      // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario
      // total.
      // - Agregar employee2 al employeeManager.
      // - Verificar que el salario total es la suma de los salarios de employee1 y
      // employee2.
      employeeManager.addEmployee(employee2);
      assertTrue(employeeManager.calculateTotalSalary() == employee1.getSalary() + employee2.getSalary());
   }

   @Test
   public void testUpdateEmployeeSalaryValid() {
      // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la
      // actualización.
      // - Actualizar el salario de employee1 a 45000.
      // - Verificar que el salario de employee1 ahora es 45000.
      employeeManager.updateEmployeeSalary(employee1, 45000);
      assertTrue(employee1.getSalary() == 45000);
   }

   @Test
   public void testUpdateEmployeeSalaryInvalid() {
      // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y
      // verificar la excepción.
      // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del
      // rango para Junior Developer).
      // - Verificar que se lanza una InvalidSalaryException.
      try {
         employeeManager.updateEmployeeSalary(employee1, 60000);
         fail("Should have thrown InvalidSalaryException");
      } catch (InvalidSalaryException e) {
         assertTrue(e.getMessage().contains("Salary is not within the range for the position"));
         assertTrue(employee1.getSalary() == 40000); // Verifica que el salario o fe cambiado
      }
   }

   @Test
   public void testUpdateEmployeeSalaryEmployeeNotFound() {
      // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y
      // verificar la excepción.
      // - Intentar actualizar el salario de employee2 a 70000.
      // - Verificar que se lanza una EmployeeNotFoundException.
      try {
         employeeManager.updateEmployeeSalary(employee2, 70000);
         fail("Should have thrown EmployeeNotFoundException");
      } catch (EmployeeNotFoundException e) {
         assertTrue(e.getMessage().contains("Employee not found"));
      }
   }

   @Test
   public void testUpdateEmployeePositionValid() {
      // TODO: Actualizar la posición de employee2 a una posición válida y verificar
      // la actualización.
      // - Agregar employee2 al employeeManager.
      // - Actualizar la posición de employee2 a seniorDeveloper.
      // - Verificar que la posición de employee2 ahora es seniorDeveloper.
      employeeManager.addEmployee(employee2);
      employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
      assertTrue(employee2.getPosition().getName().equals("Senior Developer"));
   }

   @Test
   public void testUpdateEmployeePositionInvalidDueToSalary() {
      // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y
      // verificar la excepción.
      // - Intentar actualizar la posición de employee1 a seniorDeveloper.
      // - Verificar que se lanza una InvalidSalaryException porque el salario de
      // employee1 no está dentro del rango para Senior Developer.
      try {
         employeeManager.updateEmployeePosition(employee1, seniorDeveloper);
         fail("Should have thrown InvalidSalaryException");
      } catch (InvalidSalaryException e) {
         assertTrue(e.getMessage().contains("Current salary is not within the range for the new position"));
      }
   }

   @Test
   public void testUpdateEmployeePositionEmployeeNotFound() {
      // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y
      // verificar la excepción.
      // - Intentar actualizar la posición de employee2 a juniorDeveloper.
      // - Verificar que se lanza una EmployeeNotFoundException.
      try {
         employeeManager.updateEmployeePosition(employee2, juniorDeveloper);
         fail("Should have thrown EmployeeNotFoundException");
      } catch (EmployeeNotFoundException e) {
         assertTrue(e.getMessage().contains("Employee not found"));
      }
   }

   @Test
   public void testIsSalaryValidForPosition() {
      // TODO: Verificar la lógica de validación de salario para diferentes
      // posiciones.
      // - Verificar que un salario de 40000 es válido para juniorDeveloper.
      // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
      // - Verificar que un salario de 70000 es válido para seniorDeveloper.
      // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
      assertTrue(employeeManager.isSalaryValidForPosition(juniorDeveloper, 40000)
            && !employeeManager.isSalaryValidForPosition(juniorDeveloper, 60000)
            && employeeManager.isSalaryValidForPosition(seniorDeveloper, 70000)
            && !employeeManager.isSalaryValidForPosition(seniorDeveloper, 50000));
   }

   @Test
   public void testAddEmployeeWithInvalidSalary() {
      // TODO: Intentar agregar empleados con salarios inválidos y verificar las
      // excepciones.
      // - Crear un empleado con un salario de 60000 para juniorDeveloper.
      // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
      // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
      // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
      Employee invalidJunior = new Employee("3", "Invalid Junior", juniorDeveloper, 60000);
      Employee invalidSenior = new Employee("4", "Invalid Senior", seniorDeveloper, 40000);
      String eMessage1 = null;
      String eMessage2 = null;

      try {
         employeeManager.addEmployee(invalidJunior);
         fail("Should have thrown InvalidSalaryException for junior developer");
      } catch (InvalidSalaryException e) {
         eMessage1 = e.getMessage();
      }

      try {
         employeeManager.addEmployee(invalidSenior);
         fail("Should have thrown InvalidSalaryException for senior developer");
      } catch (InvalidSalaryException e) {
         eMessage2 = e.getMessage();
      }
      assertTrue(eMessage1.contains("Invalid salary for position")
            && eMessage2.contains("Invalid salary for position"));
   }

   @Test
   public void testRemoveExistentEmployee() {
      // TODO: Eliminar un empleado existente y verificar que no se lanza una
      // excepción.
      // - Eliminar employee1 del employeeManager.
      // - Verificar que no se lanza ninguna excepción.
      try {
         employeeManager.removeEmployee(employee1);
         assertTrue(!employeeManager.getEmployees().contains(employee1));
      } catch (Exception e) {
         fail("Should not have thrown any exception");
      }
   }

   @Test
   public void testRemoveNonExistentEmployee() {
      // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
      // - Intentar eliminar employee2 (no agregado al manager).
      // - Verificar que se lanza una EmployeeNotFoundException.
      try {
         employeeManager.removeEmployee(employee2);
         fail("Should have thrown EmployeeNotFoundException");
      } catch (EmployeeNotFoundException e) {
         assertTrue(e.getMessage().contains("Employee not found"));
      }
   }

   @Test
   public void testAddDuplicateEmployee() {
      // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
      // - Intentar agregar employee1 nuevamente al employeeManager.
      // - Verificar que se lanza una DuplicateEmployeeException.
      try {
         employeeManager.addEmployee(employee1);
         fail("Should have thrown DuplicateEmployeeException");
      } catch (DuplicateEmployeeException e) {
         assertTrue(e.getMessage().contains("Duplicate employee"));
      }
   }
}
