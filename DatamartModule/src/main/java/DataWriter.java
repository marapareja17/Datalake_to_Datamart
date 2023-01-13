import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

public class DataWriter {
    private Connection connection;
    public DataWriter() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:datamart.db");
            dropTables();
            createTables();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }


    private void dropTables() throws SQLException {
        connection.createStatement().execute("DROP TABLE IF EXISTS maxtemperatures");
        connection.createStatement().execute("DROP TABLE IF EXISTS mintemperatures");
    }
    private void createTables() throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS maxtemperatures (date text PRIMARY KEY, time text, place text," +
                " station text, value real)");
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS mintemperatures (date text PRIMARY KEY, time text, place text," +
                " station text, value real)");
    }

    public void writeMaxTemperatures(Hashtable<String, EventData> maxTemperatures) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO maxtemperatures VALUES" +
                " (?, ?, ?, ?, ?) ON CONFLICT (date) DO NOTHING");
        for (EventData td : maxTemperatures.values()) {
            statement.setString(1, td.getDate());
            statement.setString(2, td.getTime());
            statement.setString(3, td.getPlace());
            statement.setString(4, td.getStation());
            statement.setFloat(5, td.getTamax());;
            statement.execute();
        }
    }

    public void writeMinTemperatures(Hashtable<String, EventData> minTemperatures) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO mintemperatures VALUES" +
                " (?, ?, ?, ?, ?) ON CONFLICT (date) DO NOTHING");
        for (EventData td : minTemperatures.values()) {
            statement.setString(1, td.getDate());
            statement.setString(2, td.getTime());
            statement.setString(3, td.getPlace());
            statement.setString(4, td.getStation());
            statement.setFloat(5, td.getTamin());
            statement.execute();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}