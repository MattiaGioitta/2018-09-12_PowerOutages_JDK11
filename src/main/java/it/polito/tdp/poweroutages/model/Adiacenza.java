package it.polito.tdp.poweroutages.model;

public class Adiacenza {

	private Nerc n1;
	private Nerc n2;
	private int peso;
	/**
	 * @param n1
	 * @param n2
	 * @param peso
	 */
	public Adiacenza(Nerc n1, Nerc n2, int peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}
	/**
	 * @return the n1
	 */
	public Nerc getN1() {
		return n1;
	}
	/**
	 * @param n1 the n1 to set
	 */
	public void setN1(Nerc n1) {
		this.n1 = n1;
	}
	/**
	 * @return the n2
	 */
	public Nerc getN2() {
		return n2;
	}
	/**
	 * @param n2 the n2 to set
	 */
	public void setN2(Nerc n2) {
		this.n2 = n2;
	}
	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
