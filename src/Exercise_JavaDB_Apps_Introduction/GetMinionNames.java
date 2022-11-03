package Exercise_JavaDB_Apps_Introduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    final private static String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID = "select m.name, m.age" +
            " from minions as m" +
            " join minions_villains mv on m.id = mv.minion_id" +
            " where mv.villain_id = ?;";

    final private static String GET_VILLAIN_NAME_BY_ID =
            "select v.name from villains as v" +
            " where v.id = ?;";
    final private static String COLUMN_LABEL_AGE = "age";
    final private static String NO_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";
    final private static String VILLAIN_FORMAT = "Villain: %s%n";
    final private static String MINION_FORMAT = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        final int villainId = new Scanner(System.in).nextInt();

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        villainStatement.setInt(1, villainId);

        final ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.printf(NO_VILLAIN_FORMAT, villainId);
            connection.close();
            return;
        }

        final String villainName = villainSet.getString(Constants.COLUMN_LABEL_NAME);
        System.out.printf(VILLAIN_FORMAT, villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);
        minionsStatement.setInt(1, villainId);

        final ResultSet minionsSet = minionsStatement.executeQuery();
        for (int index = 1; minionsSet.next(); index++) {
            final String minionName = minionsSet.getString(Constants.COLUMN_LABEL_NAME);
            final int minionAge = minionsSet.getInt(COLUMN_LABEL_AGE);

            System.out.printf(MINION_FORMAT, index, minionName, minionAge);
        }
        connection.close();
    }
}

