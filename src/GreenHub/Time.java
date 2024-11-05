package GreenHub;

import java.io.Serializable;

public class Time implements Serializable {

	private static final long serialVersionUID = 1L;
	private int hour;   // Deve essere tra 0 e 23
	private int minute; // Deve essere tra 0 e 59

	// Getter & Setter
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		if (hour < 0 || hour >= 24) {
			throw new IllegalArgumentException("L'ora deve essere compresa tra 0 e 23");
		}
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if (minute < 0 || minute >= 60) {
			throw new IllegalArgumentException("I minuti devono essere compresi tra 0 e 59");
		}
		this.minute = minute;
	}

	// Costruttori
	public Time() {
		// Costruttore vuoto, inizializza a 00:00
		this.hour = 0;
		this.minute = 0;
	}

	public Time(int hour, int minute) {
		setHour(hour);   // Usa il setter per la validazione
		setMinute(minute); // Usa il setter per la validazione
	}

	public Time addDuration(double durationInHours) {
		// Calcola le ore e i minuti da aggiungere
		int hoursToAdd = (int) durationInHours; // Parte intera delle ore
		int minutesToAdd = (int) ((durationInHours * 60) % 60); // Parte frazionaria trasformata in minuti

		// Calcola i nuovi valori di ora e minuti
		int newHour = this.hour + hoursToAdd;
		int newMinute = this.minute + minutesToAdd;

		// Gestisci il caso in cui i minuti superano 59
		if (newMinute >= 60) {
			newHour += newMinute / 60; // Aggiungi le ore che derivano dai minuti
			newMinute = newMinute % 60; // Rimanente minuti
		}

		// Gestisci il caso in cui le ore superano 23
		if (newHour >= 24) {
			// Se le ore superano 23, puoi gestire qui il passaggio al giorno successivo
			// Puoi semplicemente lasciare che newHour sia valido per il modulo 24
			newHour = newHour % 24; // Questo riporta l'ora tra 0 e 23
		}

		// Restituisci il nuovo oggetto Time
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
