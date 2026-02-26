
package com.hotel;

public class ChambreSimple extends Chambre {

	public ChambreSimple(int numero, double prixParNuit) {
		super(numero, prixParNuit, false, 1);
	}

	public ChambreSimple(int numero, double prixParNuit, boolean occupe, int capacite) {
		super(numero, prixParNuit, occupe, capacite);
	}

	public String getType() {
		return "Simple";

	}

}
