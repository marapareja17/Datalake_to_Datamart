import java.sql.*;

public class Database {
    private Connection conn;

    public Database() throws SQLException {
        // Establecer conexión a la base de datos SQLite
        String url = "jdbc:sqlite:datamart.db";
        conn = DriverManager.getConnection(url);
    }

    public ResultSet getMaxTemperature(String from, String to) throws SQLException {
        String query = "SELECT place, MIN(value) as value FROM maxtemperatures WHERE date >= ? AND date <= ? GROUP BY date ORDER BY value ASC";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, from);
        stmt.setString(2, to);
        return stmt.executeQuery();
    }

    public ResultSet getMinTemperature(String from, String to) throws SQLException {
        String query = "SELECT place, MIN(value) as value FROM mintemperatures WHERE date >= ? AND date <= ? GROUP BY date ORDER BY value ASC";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, from);
        stmt.setString(2, to);
        return stmt.executeQuery();
    }
}