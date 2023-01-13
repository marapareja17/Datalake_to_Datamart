public class EventData {
    private String date;
    private String time;
    private String place;
    private String station;
    private float tamax;
    private float tamin;

    public EventData(String date, String time, String place, String station, float tamax, float tamin) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.station = station;
        this.tamax = tamax;
        this.tamin = tamin;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getStation() {
        return station;
    }

    public float getTamax() {
        return tamax;
    }

    public float getTamin() {
        return tamin;
    }

    public void printData() {
        System.out.println("Date: " + date + ", Time: " + time + ", Place: " + place + ", Station: " +
                station + ", Tamax: " + tamax + ", Tamin: " + tamin);
    }
}
