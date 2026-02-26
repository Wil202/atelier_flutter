
package com.hotel;

public class Service {
	String nom;
	double prix;
	String description;

	public Service(String nom, double prix, String description) {
		this.nom = nom;
		this.prix = prix;
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Le service souscrit : " + nom + ", a pour prix :" + prix + "$ , description :" + description + "";
	}

}
