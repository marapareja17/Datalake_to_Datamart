import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataLoader {
    private ArrayList<EventData> data;

    public DataLoader() {
        data = new ArrayList<>();
    }

    public void processFiles() throws IOException, SQLException {
        String directory = WeatherDataSaver.DATALAKE_FOLDER;
        Files.list(Paths.get(directory))
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().endsWith(".events"))
                .forEach(file -> {
                    loadData(file);
                });
    }
    public void loadData(Path fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsear la línea para obtener los datos de temperatura
                String[] fields = line.split(",");
                String date = fields[0].substring(9, 19);

                String time = fields[0].substring(20, 28);

                String place = fields[1].substring(7).replace("\"", "")
                        .replace("}", "");

                String station = fields[2].substring(9, 14);

                float tamax = Float.parseFloat(fields[3].substring(8));

                float tamin = Float.parseFloat(fields[4].substring(8).replace("}", ""));

                // Añadir los datos a la estructura temporal
                data.add(new EventData(date, time, place, station, tamax, tamin));
            }
        } catch (Exception e) {
            System.err.println("Error al leer el fichero " + fileName + ": " + e.getMessage());
        }
    }

    public ArrayList<EventData> getData() {
        return data;
    }

    public void printData() {
        for (EventData ed : data) {
            ed.printData();
        }
    }
}
