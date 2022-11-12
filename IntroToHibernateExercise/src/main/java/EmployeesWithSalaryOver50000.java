import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeesWithSalaryOver50000 {

    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("soft-uni");

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.createQuery("SELECT e.firstName FROM Employee  e WHERE e.salary > 50000", String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
