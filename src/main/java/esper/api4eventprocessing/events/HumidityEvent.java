package esper.api4eventprocessing.events;

public class HumidityEvent extends Event {
    public HumidityEvent(String station, long timestamp, float value){
       super(station, timestamp, value);
    }
    public String toString() {
        return "HumidityEvent [" + this.getTimestamp() + this.getValue() + this.getStation() + "]";
    }
}
