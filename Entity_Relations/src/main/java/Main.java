import entities.Bike;
import entities.Car;
import entities.Plane;
import entities.Vehicle;
import hasEntities.Article;
import hasEntities.PlateNumber;
import hasEntities.Truck;
import hasEntities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("relations").createEntityManager();

        entityManager.getTransaction().begin();

//        Vehicle car = new Car("Audi", "Petrol", 5);
//        Vehicle bike = new Bike();
//        Vehicle plane = new Plane("Airbus", "Petrol", 200);
//
//        entityManager.persist(car);
//        entityManager.persist(bike);
//        entityManager.persist(plane);
//
//        PlateNumber number = new PlateNumber("123");
//        Truck truck1 = new Truck(number);
//        Truck truck2 = new Truck(number);
//
//        entityManager.persist(number);
//        entityManager.persist(truck1);
//        entityManager.persist(truck2);

        Article article = new Article("asdasdsad");
        User user = new User("Martin");
        article.setAuthor(user);
        user.addArticle(article);

        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

