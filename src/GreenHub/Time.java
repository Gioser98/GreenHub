package GreenHub;

import java.io.Serializable;

public class Time implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int hour;
	private int minute;
	
	// Getter&Setter
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	// Constructors
	public Time() {
	    // Costruttore vuoto
	}
	public Time(int hour, int minute) {
	    this.hour = hour;
	    this.minute = minute;
	}
	
	// Methods	
	public String distance(Time time2) {
        int totalMinutes1 = this.hour * 60 + this.minute;
        int totalMinutes2 = time2.getHour() * 60 + time2.getMinute();

        //if(totalMinutes2<totalMinutes1) {
        	//return "L'orario inserito è precedente a quell
        //}
        int hourDifference = Math.abs((totalMinutes1 - totalMinutes2) / 60);
        int minuteDifference = Math.abs((totalMinutes1 - totalMinutes2) % 60);

        return hourDifference + " ore e " + minuteDifference + " minuti";
    }
	
	public String toString() {
		return hour + ":" + minute;
	}
}