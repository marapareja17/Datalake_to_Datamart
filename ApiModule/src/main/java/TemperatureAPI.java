import java.sql.SQLException;

import static spark.Spark.*;

public class TemperatureAPI {
    public static void main(String[] args) throws SQLException {
        // Crear instancia de la clase Database
        Database db = new Database();

        // Crear instancia de la clase TemperatureController
        TemperatureController controller = new TemperatureController(db);

        // Crear ruta GET para temperatura máxima
        get("/v1/places/with-max-temperature", controller::getMaxTemperature);

        // Crear ruta GET para temperatura mínima
        get("/v1/places/with-min-temperature", controller::getMinTemperature);
    }
}