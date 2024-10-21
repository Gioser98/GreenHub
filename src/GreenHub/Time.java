package GreenHub;

import java.io.Serializable;

public class Time implements Serializable {

    private static final long serialVersionUID = 1L;
    private int hour;
    private int minute;

    // Getter & Setter
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

    // Metodo per aggiungere una durata in ore e minuti all'ora corrente
    public Time addDuration(double durationInHours) {
        int hoursToAdd = (int) durationInHours; // Parte intera delle ore
        int minutesToAdd = (int) ((durationInHours * 60) % 60); // Parte frazionaria trasformata in minuti

        int newHour = this.hour + hoursToAdd;
        int newMinute = this.minute + minutesToAdd;

        // Gestisci il caso in cui i minuti superano 59
        if (newMinute >= 60) {
            newHour += newMinute / 60;
            newMinute = newMinute % 60;
        }

        // Gestisci il caso in cui le ore superano 23 (facoltativo, se vuoi considerare il passaggio al giorno successivo)
        newHour = newHour % 24;

        return new Time(newHour, newMinute);
    }

    // Metodo per calcolare la distanza tra due orari
    public String distance(Time time2) {
        int totalMinutes1 = this.hour * 60 + this.minute;
        int totalMinutes2 = time2.getHour() * 60 + time2.getMinute();

        int hourDifference = Math.abs((totalMinutes1 - totalMinutes2) / 60);
        int minuteDifference = Math.abs((totalMinutes1 - totalMinutes2) % 60);

        return hourDifference + " ore e " + minuteDifference + " minuti";
    }

    @Override
    public String toString() {
        return hour + ":" + (minute < 10 ? "0" + minute : minute); // Aggiungi uno 0 ai minuti se necessario
    }
}
