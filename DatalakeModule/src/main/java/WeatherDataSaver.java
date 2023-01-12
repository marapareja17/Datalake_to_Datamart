import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WeatherDataSaver {

    private static final String DATALAKE_FOLDER = "datalake";

    public void save(JSONArray weatherData) throws IOException {
        // Crea la carpeta "datalake" si no existe
        new File(DATALAKE_FOLDER).mkdir();
        HashMap<String, FileWriter> dateFileWriterMap = new HashMap<>();

        for (int i = 0; i < weatherData.length(); i++) {
            JSONObject station = weatherData.getJSONObject(i);
            if (-16 < station.getDouble("lon") && station.getDouble("lon") < -15 && 27.5 < station.getDouble("lat") && station.getDouble("lat") < 28.4) {
                System.out.println(station);
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
                File temporal;
                if (!dateFileWriterMap.containsKey(date)) {
                    temporal = new File(DATALAKE_FOLDER + "/" + date + ".events.tmp");
                    temporal.createNewFile();
                    FileWriter fileWriter = new FileWriter(temporal);
                    dateFileWriterMap.put(date, fileWriter);
                }
                FileWriter fileWriter = dateFileWriterMap.get(date);

                // Escribir los eventos en formato json en el fichero creado
                JSONObject event = new JSONObject();
                event.put("fint", fint);
                event.put("ubi", ubi);
                event.put("idema", idema);
                event.put("tamax", tamax2);
                event.put("tamin", tamin2);
                fileWriter.write(event.toString());
                fileWriter.write("\n");
            }
        }
        for (String date : dateFileWriterMap.keySet()) {
            FileWriter fileWriter = dateFileWriterMap.get(date);
            fileWriter.close();
            File temporal = new File(DATALAKE_FOLDER + "/" + date + ".events.tmp");
            File original = new File(DATALAKE_FOLDER + "/" + date + ".events");
            if (FileUtils.contentEquals(temporal, original)) {
                temporal.delete();
            } else {
                original.delete();
                temporal.renameTo(original);
            }
        }
    }
}