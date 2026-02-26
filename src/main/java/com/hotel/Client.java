package com.hotel;

public class Client {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private int numeroClient;
    private static int compteur = 1;

    public Client(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.numeroClient = compteur;
        compteur++;
    }

    // ========== GETTERS & SETTERS ==========

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Client.compteur = compteur;
    }

    // ========== MÉTHODES ==========

    public String toString() {
        return "Client #" + numeroClient + " - " + prenom + " " + nom + " (" + email + ") - Tel: " + telephone;
    }

    public String getNomComplet() {
        return prenom + " " + nom.toUpperCase();
    }

    /**
     * Valide le format de l'email
     * Vérifie : présence de @, présence de . après @, pas d'espaces
     */
    public boolean validerEmail() {
        if (email == null || email.length() == 0) {
            return false;
        }

        // Vérifier la présence du @
        boolean trouveArobase = false;
        int posArobase = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                trouveArobase = true;
                posArobase = i;
                break;
            }
        }

        if (!trouveArobase) {
            return false;
        }

        // Vérifier la présence d'un point après le @
        boolean trouvePoint = false;
        for (int j = posArobase; j < email.length(); j++) {
            if (email.charAt(j) == '.') {
                trouvePoint = true;
                break;
            }
        }

        if (!trouvePoint) {
            return false;
        }

        // Vérifier qu'il n'y a pas d'espaces
        for (int k = 0; k < email.length(); k++) {
            if (email.charAt(k) == ' ') {
                return false;
            }
        }

        return true;
    }
}