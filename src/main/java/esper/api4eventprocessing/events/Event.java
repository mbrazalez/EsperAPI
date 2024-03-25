package esper.api4eventprocessing.events;

public class Event {
    private String  station;
    private long timestamp;
    private float value;

    public Event(String station, long timestamp, float value){
        this.station = station;
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getStation() { return this.station; }
    public long getTimestamp() {
        return this.timestamp;
    }
    public float getValue() {
        return this.value;
    }
}
