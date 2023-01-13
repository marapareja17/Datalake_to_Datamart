import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;


import static com.amazonaws.regions.ServiceAbbreviations.Directory;

public class DatamartBuilder {
    public static void main(String[] args) throws SQLException, IOException {
        // Cargar los datos de los ficheros .events
        DataLoader loader = new DataLoader();
        loader.processFiles();

        // Procesar los datos para buscar las temperaturas máximas y mínimas de cada día
        DataProcessor processor = new DataProcessor(loader.getData());
        processor.processData();

        // Escribir las temperaturas máximas y mínimas en la base de datos
        DataWriter writer = new DataWriter();
        try {
            writer.writeMaxTemperatures(processor.getMaxTemperatures());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.writeMinTemperatures(processor.getMinTemperatures());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        writer.close();
    }
}
