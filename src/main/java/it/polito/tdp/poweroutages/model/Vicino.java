package it.polito.tdp.poweroutages.model;

public class Vicino implements Comparable<Vicino> {
	
	private Nerc n;
	private double peso;
	/**
	 * @param n
	 * @param peso
	 */
	public Vicino(Nerc n, double peso) {
		super();
		this.n = n;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  n.toString() + " peso=" + peso;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return (int) (o.peso-this.peso);
	}
	
	

}
