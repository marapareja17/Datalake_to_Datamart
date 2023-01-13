import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemperatureController {
    private Database db;
    private Gson gson = new Gson();

    public TemperatureController(Database db) {
        this.db = db;
    }

    public String getMaxTemperature(Request req, Response res) throws SQLException {
        String from = req.queryParams("from");
        String to = req.queryParams("to");

        ResultSet rs = db.getMaxTemperature(from, to);

        try {
            res.type("application/json");
            return gson.toJson(toJsonArray(rs));
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "error";
        }
    }

    public String getMinTemperature(Request req, Response res) throws SQLException {
        String from = req.queryParams("from");
        String to = req.queryParams("to");

        ResultSet rs = db.getMinTemperature(from, to);

        try {
            res.type("application/json");
            return gson.toJson(toJsonArray(rs));
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "error";
        }
    }

    private JsonArray toJsonArray(ResultSet rs) throws SQLException {
        JsonArray jsonArray = new JsonArray();
        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("place", rs.getString("place"));
            jsonObject.addProperty("value", rs.getDouble("value"));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}