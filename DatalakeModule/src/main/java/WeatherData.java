import org.json.JSONArray;
import java.io.IOException;

public class WeatherData {

    public static void main(String[] args) {
        try {
            WeatherDataDownloader downloader = new WeatherDataDownloader();
            JSONArray weatherData = downloader.download();
            WeatherDataSaver saver = new WeatherDataSaver();
            saver.save(weatherData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
