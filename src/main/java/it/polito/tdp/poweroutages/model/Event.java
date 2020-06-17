package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {

	public enum EventType{
		INIZIO_INTERRUZIONE,
		FINE_INTERRUZIONE,
	}
	
	private LocalDateTime time;
	private Interruzione n;
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
		
	}
	
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.time);
	}
	
	
}
