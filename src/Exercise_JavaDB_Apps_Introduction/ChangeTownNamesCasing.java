package Exercise_JavaDB_Apps_Introduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private static final String UPDATE_TOWNS_SQL = "update towns set name = UPPER(name) where country = ?";
    private static final String SELECT_TOWNS_SQL = "select name from towns where country = ?";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = Utils.getSQLConnection();

        String inputCountry = scanner.nextLine();

        PreparedStatement preparedInsertStatement = connection.prepareStatement(UPDATE_TOWNS_SQL);

        preparedInsertStatement.setString(1, inputCountry);

        preparedInsertStatement.executeUpdate();

        PreparedStatement preparedSelectStatement = connection.prepareStatement(SELECT_TOWNS_SQL);

        preparedSelectStatement.setString(1, inputCountry);

        ResultSet resultTowns = preparedSelectStatement.executeQuery();

        ArrayList<String> townsList = new ArrayList<>();

        while (resultTowns.next()) {
            String db_town_name = resultTowns.getString("name");

            townsList.add(db_town_name);
        }

        if (townsList.isEmpty()) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.%n", townsList.size());
            System.out.println(townsList);
        }
        connection.close();

    }
}
