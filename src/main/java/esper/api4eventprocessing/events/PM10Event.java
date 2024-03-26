package esper.api4eventprocessing.events;

public class PM10Event extends Event {
    public PM10Event(String station, long timestamp, float value){
        super(station, timestamp, value);
    }
    public String toString() {
        return "PM10Event [" + this.getTimestamp() + this.getValue() + this.getStation() + "]";
    }
}
