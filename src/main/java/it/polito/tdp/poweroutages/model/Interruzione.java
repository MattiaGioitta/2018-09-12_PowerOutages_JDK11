package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Interruzione {
	
	private Nerc n;
	private LocalDateTime timeInizio;
	private LocalDateTime timeFine;
	/**
	 * @param n
	 * @param timeInizio
	 * @param timeFine
	 */
	public Interruzione(Nerc n, LocalDateTime timeInizio, LocalDateTime timeFine) {
		super();
		this.n = n;
		this.timeInizio = timeInizio;
		this.timeFine = timeFine;
	}
	/**
	 * @return the n
	 */
	public Nerc getN() {
		return n;
	}
	/**
	 * @return the timeInizio
	 */
	public LocalDateTime getTimeInizio() {
		return timeInizio;
	}
	/**
	 * @return the timeFine
	 */
	public LocalDateTime getTimeFine() {
		return timeFine;
	}
	
	

}
