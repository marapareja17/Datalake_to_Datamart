import java.util.ArrayList;
import java.util.Hashtable;

public class DataProcessor {
    private ArrayList<EventData> data;
    private Hashtable<String, EventData> maxTemperatures;
    private Hashtable<String, EventData> minTemperatures;

    public DataProcessor(ArrayList<EventData> data) {
        this.data = data;
        maxTemperatures = new Hashtable<String, EventData>();
        minTemperatures = new Hashtable<String, EventData>();
    }

    public void processData() {
        for (EventData ed : data) {
            String date = ed.getDate();
            if (!maxTemperatures.containsKey(date) || ed.getTamax() > maxTemperatures.get(date).getTamax()) {
                maxTemperatures.put(date, ed);
            }
            if (!minTemperatures.containsKey(date) || ed.getTamin() < minTemperatures.get(date).getTamin()) {
                minTemperatures.put(date, ed);
            }
        }
    }

    public Hashtable<String, EventData> getMaxTemperatures() {
        return maxTemperatures;
    }

    public Hashtable<String, EventData> getMinTemperatures() {
        return minTemperatures;
    }

    public void printMaxTemperatures() {
        for (EventData td : maxTemperatures.values()) {
            td.printData();
        }
    }

    public void printMinTemperatures() {
        for (EventData td : minTemperatures.values()) {
            td.printData();
        }
    }
}
