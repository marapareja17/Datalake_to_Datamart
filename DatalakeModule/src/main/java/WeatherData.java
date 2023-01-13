import org.json.JSONArray;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherData {

    public static void main(String[] args) {
        Timer timer = new Timer();
        WeatherDataTask task = new WeatherDataTask();
        timer.scheduleAtFixedRate(task, 0, 60*60*1000); //ejecutar cada hora
        System.out.println("Los datos han sido actualizados");
    }

    public static class WeatherDataTask extends TimerTask {
        @Override
        public void run() {
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
}
