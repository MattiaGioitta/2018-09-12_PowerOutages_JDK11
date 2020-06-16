package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {

	public enum EventType{
		INIZIO_INTERRUZIONE,
		FINE_INTERRUZIONE,
		CATASTROFE,
	}
	
	private LocalDateTime time;
	private Interruzione n;
	private Nerc donatore;
	/**
	 * @return the donatore
	 */
	public Nerc getDonatore() {
		return donatore;
	}



	/**
	 * @param donatore the donatore to set
	 */
	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}



	private EventType type;
	
	
	
	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}



	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}



	/**
	 * @return the n
	 */
	public Interruzione getN() {
		return n;
	}



	/**
	 * @param time
	 * @param n
	 * @param type
	 */
	public Event(LocalDateTime time, Interruzione n, EventType type) {
		super();
		this.time = time;
		this.n = n;
		this.type = type;
		this.donatore = null;
	}
	
	public Event(LocalDateTime time, Interruzione n, EventType type,Nerc donatore) {
		super();
		this.time = time;
		this.n = n;
		this.type = type;
		this.donatore = donatore;
	}



	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}
	
	
}
