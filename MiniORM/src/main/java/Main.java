import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        Connector.createConnection("root", "8705", "soft_uni");
        Connection connection = Connector.getConnection();

        EntityManager<User> userManager = new EntityManager<>(connection);

        User user = new User("First", 35, LocalDate.now());

        userManager.persist(user);
    }
}
