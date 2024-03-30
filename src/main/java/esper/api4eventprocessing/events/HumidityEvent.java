package esper.api4eventprocessing.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HumidityEvent {
    public long timestamp;
    public float value;
    public String  station;

    public HumidityEvent (long timestamp, float value, String station){
        this.timestamp = timestamp;
        this.value = value;
        this.station = station;
    }

    public String getStation() { return this.station; }
    public long getTimestamp() {
        return this.timestamp;
    }
    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "HumidityEvent [" + this.getTimestamp() + " " + this.getValue() + " " + this.getStation() + "]";
    }
}
