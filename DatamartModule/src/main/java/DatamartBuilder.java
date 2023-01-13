import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;

public class DatamartBuilder {
    public static void main(String[] args) throws SQLException, IOException {
        Timer timer = new Timer();
        DatamartBuilderTask task = new DatamartBuilderTask();
        timer.scheduleAtFixedRate(task, 0, 24*60*60*1000); //ejecutar cada día
    }
}
