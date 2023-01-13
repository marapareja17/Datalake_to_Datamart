import java.io.IOException;
import java.sql.SQLException;
import java.util.TimerTask;

public class DatamartBuilderTask extends TimerTask {

    @Override
    public void run() {
        try {
            DataLoader loader = new DataLoader();
            loader.processFiles();
            DataProcessor processor = new DataProcessor(loader.getData());
            processor.processData();
            DataWriter writer = new DataWriter();
            writer.writeMaxTemperatures(processor.getMaxTemperatures());
            writer.writeMinTemperatures(processor.getMinTemperatures());
            writer.close();
            System.out.println("Los datos han sido actualizados");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
