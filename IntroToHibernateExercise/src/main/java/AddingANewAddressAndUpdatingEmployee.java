import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingEmployee {

    public static void main(String[] args) {

        final EntityManager entityManager = Persistence.createEntityManagerFactory("soft-uni")
                .createEntityManager();

        final String lastName =new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        entityManager.persist(newAddress);

        int count = entityManager.createQuery("UPDATE Employee e SET e.address = :newAddress " +
                        "WHERE e.lastName = :ln")
                .setParameter("newAddress", newAddress)
                .setParameter("ln", lastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

        System.out.println(count);
    }
}
