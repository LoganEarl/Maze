import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class TestDatabaseConnectivity {
    private static final String DATA_DIRECTORY = System.getProperty("user.dir").replace("\\", "/") + "/data/";
    private static final String TEST_DB_FILE = DATA_DIRECTORY + "test.db";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeEach
    void setup(){
        try {
            File f = new File(TEST_DB_FILE);
            f.getParentFile().mkdirs();
            f.createNewFile();
        }catch(Exception ignored){}
    }

    @Test
    void testDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            fail("Was now able to find JDBC Driver");
        }
    }

    @Test
    void testEstablishConnection(){
        String url = "jdbc:sqlite:" + TEST_DB_FILE;
        try {
            Connection c = DriverManager.getConnection(url);
            Assertions.assertNotNull(c);
            c.close();
        } catch (SQLException e) {
            fail("could not connect to test database");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @AfterEach
    void tearDown(){
        File f = new File(TEST_DB_FILE);
        try {
            f.delete();
        }catch(Exception ignored){}
    }
}
