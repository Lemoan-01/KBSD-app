import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class saveMonitoring {
//    Ip database: 192.168.1.103


    public saveMonitoring(double uptime, String bijzonderheden) {
        String url = "jdbc:mysql://192.168.1.103:3306/nerdygadgets";
        String username = "webuser3";
        String password = "password";
        String datum = String.valueOf(java.time.LocalDate.now());

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Verbonden met de database!");

            // Voer hier je query's uit en haal gegevens op of sla ze op
            Statement statement = connection.createStatement();
            String query = "INSERT INTO `saveMonitoring` (`Datum`, `Uptime24h`, `Bijzonderheden`) " +
                    "VALUES ('"+ datum +"', "+ uptime +", '"+ bijzonderheden +"');";

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0){
                System.out.println("succesvol YAAA");
            } else {
                System.out.println("deez");
            }

//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()){
//                String fullName = resultSet.getString("FullName");
//
//                System.out.println("PersonID: "+fullName);
//            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Kon geen verbinding maken met de database!");
            e.printStackTrace();
        }
    }
}