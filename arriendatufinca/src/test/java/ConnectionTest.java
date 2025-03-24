

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.arriendatufinca.arriendatufinca.Conections.SshTunnelStarter;

public class ConnectionTest {

    @Test
    public void testConnection() {

        SshTunnelStarter sshTunnelStarter = new SshTunnelStarter();

        try {
            sshTunnelStarter.init();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        ResourceBundle bundle = ResourceBundle.getBundle("application");
		String username = bundle.getString("spring.datasource.username");
		String password = bundle.getString("spring.datasource.password");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/test", username, password);
             Statement statement = connection.createStatement()) {

            System.out.println("Database connection established successfully.");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS test ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "PRIMARY KEY (id))";

            statement.execute(createTableSQL);
            System.out.println("Table created successfully.");

            String insertSQL = "INSERT INTO test (name) VALUES ('John')";
            statement.execute(insertSQL);

            
            String dropTableSQL = "DROP TABLE test";
            statement.execute(dropTableSQL);
                       

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

}
