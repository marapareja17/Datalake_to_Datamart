import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;
import scala.util.parsing.combinator.testing.Str;

import java.io.IOException;

public class WeatherDataDownloader {

    private static final String AEMET_URL = "https://opendata.aemet.es/opendata/api/observacion/convencional/todas";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJhcGFyZWphMTdAZ21haWwuY29tIiwianRpIjoiZG" +
            "U1MzgzNWItNjUxMy00YjQ1LTg4MmYtYTE2N2JkNWFlYzVjIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE2NzM1MDYwOTMsInVzZXJJZCI6ImR" +
            "lNTM4MzViLTY1MTMtNGI0NS04ODJmLWExNjdiZDVhZWM1YyIsInJvbGUiOiIifQ.smjZ1Hdu4h6EG2Joqk26V-rsIP-gvrHJmWVwI3fjVUA";

    public JSONArray download() throws IOException {
        // Realiza una petición al webservice de la AEMET para obtener los datos de las estaciones meteorológicas
        String response = Jsoup.connect(AEMET_URL)
                .validateTLSCertificates(false)
                .timeout(6000)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", API_KEY)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();
        JSONObject json = new JSONObject(response);
        String url = json.get("datos").toString();

        String urlResponse = Jsoup.connect(url)
                .validateTLSCertificates(false)
                .timeout(600000000)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", API_KEY)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();
        JSONArray json2 = new JSONArray(urlResponse);
        return json2;
    }
}