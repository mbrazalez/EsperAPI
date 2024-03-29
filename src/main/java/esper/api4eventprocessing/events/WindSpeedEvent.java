package esper.api4eventprocessing.events;

public class WindSpeedEvent extends Event{
    public float direction;
    public WindSpeedEvent(String station, long timestamp, float value, float direction){
        super(station, timestamp, value);
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "WindSpeedEvent [" + this.getTimestamp() + this.getValue() + this.getStation() +  this.direction + "]";
    }
}
