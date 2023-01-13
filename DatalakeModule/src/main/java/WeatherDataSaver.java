import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class WeatherDataSaver {

    public static final String DATALAKE_FOLDER = "datalake";
    static Set<JsonObject> eventosAgregados = new HashSet<JsonObject>();
    static final Gson gson = new Gson();

    public void save(JSONArray weatherData) throws IOException {

        // Crea la carpeta "datalake" si no existe
        new File(DATALAKE_FOLDER).mkdir();
        loadEvents();

        for (int i = 0; i < weatherData.length(); i++) {
            JSONObject station = weatherData.getJSONObject(i);
            if (-16 < station.getDouble("lon") && station.getDouble("lon") < -15 && 27.5 < station.getDouble("lat") && station.getDouble("lat") < 28.4) {
                String fint = station.getString("fint");
                String ubi = station.getString("ubi");
                String idema = station.getString("idema");
                double tamax = station.optDouble("tamax", Double.NaN);
                double tamin = station.optDouble("tamin", Double.NaN);

                double tamax2 = 0.0;
                if (!Double.isNaN(tamax)) {
                    tamax2 = station.getDouble("tamax");
                }
                double tamin2 = 0.0;
                if (!Double.isNaN(tamin)) {
                    tamin2 = station.getDouble("tamin");
                }

                // Crea un fichero con el nombre en formato YYYYMMDD.events en la carpeta "datalake"
                String date = fint.substring(0, 4) + fint.substring(5, 7) + fint.substring(8, 10);

                Path file = Paths.get(DATALAKE_FOLDER + "/" + date + ".events");
                if (!Files.exists(file)) {
                    Files.createFile(file);
                }

                // Escribir los eventos en formato json en el fichero creado
                JsonObject event = new JsonObject();
                event.addProperty("fint", fint);
                event.addProperty("ubi", ubi);
                event.addProperty("idema", idema);
                event.addProperty("tamax", tamax2);
                event.addProperty("tamin", tamin2);

                if (!eventosAgregados.contains(event)) {
                    eventosAgregados.add(event);
                    String evento = event.toString() + System.lineSeparator();
                    Files.write(file, evento.getBytes(), StandardOpenOption.APPEND);
                    saveEvents();
                }
            }
        }
    }
    private static void loadEvents () throws IOException {
        Path file = Paths.get(DATALAKE_FOLDER, "events.json");
        if (!Files.exists(file)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            Type type = new TypeToken<Set<JsonObject>>() {
            }.getType();
            eventosAgregados = gson.fromJson(reader, type);
        }
    }

    private static void saveEvents () throws IOException {
        Path file = Paths.get(DATALAKE_FOLDER, "events.json");
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            gson.toJson(eventosAgregados, writer);
        }
    }
}