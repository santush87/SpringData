import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ChangeCasing {
    private static final String DATABASE_NAME = "soft-uni";
//    private static final String UPDATE_ALL_TOWNS_WITH_LENGTH_MORE_THAN_5
//            = "UPDATE Town t SET t.name = UPPER(t.name) WHERE LENGTH(t.name) > 5";

    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE_NAME);

        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Query towns = entityManager.createQuery("SELECT t FROM Town t", Town.class);

        List<Town> resultList = towns.getResultList();

        for (Town town : resultList) {
            if (town.getName().length() <= 5) {
                town.setName(town.getName().toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
    }
}