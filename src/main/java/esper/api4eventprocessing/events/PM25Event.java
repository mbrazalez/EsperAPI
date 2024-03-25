package esper.api4eventprocessing.events;

public class PM25Event extends Event {
    public PM25Event(String station, long timestamp, float value){
        super(station, timestamp, value);
    }

    @Override
    public String toString() {
        return "PM25Event [" + this.getTimestamp() + this.getValue() + this.getStation() + "]";
    }
}
