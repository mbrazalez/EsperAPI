package esper.api4eventprocessing.events;

import java.time.LocalTime;

public class WindSpeedEvent extends Event{
    private float direction;
    public WindSpeedEvent(String station, long timestamp, float value, float direction){
        super(station, timestamp, value);
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "WindSpeedEvent [" + this.getTimestamp() + this.getValue() + this.getStation() +  this.direction + "]";
    }
}
