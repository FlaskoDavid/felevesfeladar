package logic;

public class Resource {
    private int id;
    private long[] TransportTime;
    private long[][] SetTime;
    private int NumberOfCal;
    private TimeWindow[] Cal;

    public Resource(int id, long[] transportTime, long[][] setTime, int numberOfCal, TimeWindow[] cal) {
        this.id = id;
        TransportTime = transportTime;
        SetTime = setTime;
        NumberOfCal = numberOfCal;
        Cal = cal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long[] getTransportTime() {
        return TransportTime;
    }

    public void setTransportTime(long[] transportTime) {
        TransportTime = transportTime;
    }

    public long[][] getSetTime() {
        return SetTime;
    }

    public void setSetTime(long[][] setTime) {
        SetTime = setTime;
    }

    public int getNumberOfCal() {
        return NumberOfCal;
    }

    public void setNumberOfCal(int numberOfCal) {
        NumberOfCal = numberOfCal;
    }

    public TimeWindow[] getCal() {
        return Cal;
    }

    public void setCal(TimeWindow[] cal) {
        Cal = cal;
    }
}
