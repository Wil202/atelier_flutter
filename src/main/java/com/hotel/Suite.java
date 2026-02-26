
package com.hotel;

public class Suite extends Chambre {

	private boolean jacuzzi;
	private boolean balcon;

	public Suite(int numero, double prixParNuit, boolean jacuzzi) {
		super(numero, prixParNuit, false, 4);
		this.jacuzzi = jacuzzi;
		this.balcon = jacuzzi;
	}

	public Suite(int numero, double prixParNuit, boolean occupe, int capacite, boolean jacuzzi, boolean balcon) {
		super(numero, prixParNuit, occupe, capacite);
		this.jacuzzi = jacuzzi;
		this.balcon = balcon;
	}

	@Override
	public String getType() {
		return "Suite";// herite de Chambre
	}

	public boolean isJacuzzi() {
		return jacuzzi;
	}

	public void setJacuzzi(boolean jacuzzi) {
		this.jacuzzi = jacuzzi;
	}

	public boolean isBalcon() {
		return balcon;
	}

	public void setBalcon(boolean balcon) {
		this.balcon = balcon;
	}

	public double PrixTarif() {
		if (jacuzzi) {
			return CalculerPrix(getCapacite()) + 30;
		} else if (balcon) {
			return CalculerPrix(getCapacite()) + 20;
		} else if (jacuzzi && balcon) {
			return CalculerPrix(getCapacite()) + 30 + 20;
		} else {
			return CalculerPrix(getCapacite());
		}

	}

	@Override
	public String toString() {
		return super.toString() + "Votre ChambreSuits comporte un : " + (jacuzzi ? "oui" : "non") + "un balcon="
				+ (balcon ? "oui" : "non");
	}
}
