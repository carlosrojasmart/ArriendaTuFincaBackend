import static org.junit.jupiter.api.Assertions.fail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.arriendatufinca.arriendatufinca.Conections.SshTunnelStarter;

/*
* Esta clase se encarga de probar la conexión a la base de datos a través de un tunel ssh y verifica que se puedan realizar las operaciones básicas CRUD
 */
public class ConnectionTest {

    /*
     * Este método se encarga de probar la conexión a la base de datos y verifica que se puedan realizar las operaciones básicas CRUD
     */
    @Test
    public void testConnection() {

        SshTunnelStarter sshTunnelStarter = new SshTunnelStarter();

        //Se inicializa el tunel ssh
        try {
            sshTunnelStarter.init();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        //Se obtienen las credenciales de la base de datos
        ResourceBundle bundle = ResourceBundle.getBundle("application");
		String username = bundle.getString("spring.datasource.username");
		String password = bundle.getString("spring.datasource.password");

        //Se realiza la conexión a la base de datos, se crea una tabla, se inserta un registro y se elimina la tabla
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/test", username, password);
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS test ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "PRIMARY KEY (id))";

            statement.execute(createTableSQL);

            String insertSQL = "INSERT INTO test (name) VALUES ('John')";
            statement.execute(insertSQL);

            
            String dropTableSQL = "DROP TABLE test";
            statement.execute(dropTableSQL);
                       

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

}
