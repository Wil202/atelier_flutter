
package com.hotel;

public class ChambreDouble extends Chambre {

	private boolean LitJumeaux;

	public ChambreDouble(int numero, double prixParNuit) {
		super(numero, prixParNuit, false, 2);
		this.LitJumeaux = false;
	}

	public ChambreDouble(int numero, double prixParNuit, boolean occupe, int capacite, boolean LitJumeaux) {
		super(numero, prixParNuit, occupe, capacite);
		this.LitJumeaux = LitJumeaux;
	}

	public String getType() {
		return "Double";

	}

	public boolean isLitJumeaux() {
		return LitJumeaux;
	}

	public void setLitJumeaux(boolean litJumeaux) {
		LitJumeaux = litJumeaux;
	}

	@Override
	public String toString() {
		return super.toString() + "Cette ChambreDouble a un LitJumeaux :" + (LitJumeaux ? "oui" : "non");
	}

}
