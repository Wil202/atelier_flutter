
package com.hotel;

public abstract class Chambre { // abstract pour dire que les classes filles devront utiliser le getType()
	private int numero;
	private double prixParNuit;
	private boolean occupe;
	private int capacite;

	public Chambre(int numero, double prixParNuit, boolean occupe, int capacite) {
		super();
		this.numero = numero;
		this.prixParNuit = prixParNuit;
		this.occupe = occupe;
		this.capacite = capacite;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getPrixParNuit() {
		return prixParNuit;
	}

	public void setPrixParNuit(double prixParNuit) {
		this.prixParNuit = prixParNuit;
	}

	public boolean isOccupe() {
		return occupe;
	}

	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public abstract String getType();

	public double CalculerPrix(int nbNuits) {
		return prixParNuit * nbNuits;
	}

	@Override
	public String toString() {
		return "Votre chambre est le numero :" + numero + ", le prix par nuit est de:" + prixParNuit
				+ "$ , le statut de la chambre est :" + getType() + ", il peut contenir :"
				+ capacite + "personne";
	}

}
