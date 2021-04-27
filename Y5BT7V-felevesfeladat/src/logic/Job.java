package logic;

public class Job {
    private int id;
    private long[] ProcedureTime;
    private long[] StartTime;
    private long[] EndTime;
    private long d;

    public Job(int id, long[] procedureTime, long[] startTime, long[] endTime, long d) {
        this.id = id;
        ProcedureTime = procedureTime;
        StartTime = startTime;
        EndTime = endTime;
        this.d = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long[] getProcedureTime() {
        return ProcedureTime;
    }

    public void setProcedureTime(long[] procedureTime) {
        ProcedureTime = procedureTime;
    }

    public long[] getStartTime() {
        return StartTime;
    }

    public void setStartTime(long[] startTime) {
        StartTime = startTime;
    }

    public long[] getEndTime() {
        return EndTime;
    }

    public void setEndTime(long[] endTime) {
        EndTime = endTime;
    }

    public long getD() {
        return d;
    }

    public void setD(long d) {
        this.d = d;
    }
}
