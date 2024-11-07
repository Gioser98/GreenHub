package GreenHub;

// Classe interna per rappresentare lo stato di ciascun intervallo di tempo
public class TimeSlotStatus {
    private final int slotIndex;
    private final String startTime;
    private final String endTime;
    private final String status;

    public TimeSlotStatus(int slotIndex, String startTime, String endTime, String status) {
        this.slotIndex = slotIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getSlotIndex() {
        return slotIndex;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getStatus() {
        return status;
    }
}