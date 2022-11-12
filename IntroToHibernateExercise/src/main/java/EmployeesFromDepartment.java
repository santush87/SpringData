import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EmployeesFromDepartment {

    private static final String PRINT_EMPL_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {

        final EntityManager entityManager = Persistence
                .createEntityManagerFactory("soft-uni")
                .createEntityManager();

        final String department = "Research and Development";

        entityManager.createQuery("SELECT e FROM Employee e WHERE " +
                "e.department.name = :dp " +
                "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("dp", department)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_EMPL_FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment(),
                        e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}

