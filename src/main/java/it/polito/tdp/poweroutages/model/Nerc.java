package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nerc implements Comparable<Nerc>{
	private int id;
	private String value;
	private int bonus;
	private Map<Nerc,LocalDateTime> riceventi;
	private Nerc ultimoDonatore;

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
		this.bonus = 0;
		this.riceventi = new HashMap<>();
		this.ultimoDonatore = null;
	}
	
	

	/**
	 * @return the ultimoDonatore
	 */
	public Nerc getUltimoDonatore() {
		return ultimoDonatore;
	}



	/**
	 * @param ultimoDonatore the ultimoDonatore to set
	 */
	public void setUltimoDonatore(Nerc ultimoDonatore) {
		this.ultimoDonatore = ultimoDonatore;
	}



	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}



	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	@Override
	public int compareTo(Nerc o) {
		// TODO Auto-generated method stub
		return this.value.compareTo(o.getValue());
	}
	
	public void aggiungiDonatore(Nerc ricevente,LocalDateTime time ) {
		this.riceventi.put(ricevente, time);
	}
	public List<Nerc> donatoreUltimiMesi(int k, LocalDateTime tempo) {
		List<Nerc> l = new ArrayList<>();
		for(Nerc ne : this.riceventi.keySet()) {
			int mesi = (int) (Duration.between(tempo, this.riceventi.get(ne)).toDays()/30);
			if(mesi<=k)
				l.add(ne);
		}
		return l;
	}
}
