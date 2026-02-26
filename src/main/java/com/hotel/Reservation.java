package com.hotel;

import java.util.ArrayList;

/**
 * Classe Reservation - Gestion des réservations d'hôtel
 * Version corrigée avec objets Client et Chambre
 */
public class Reservation {
    // Attributs
    private int numeroReservation;
    private static int compteurReservation = 1;
    private Client client;
    private Chambre chambre;
    private String dateDebut;
    private String dateFin;
    private ArrayList<Service> services;
    private String statut; // "En cours", "Confirmée", "Annulée", "Terminée"

    // Constructeur
    public Reservation(Client client, Chambre chambre, String dateDebut, String dateFin) {
        this.numeroReservation = compteurReservation++;
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.services = new ArrayList<>();
        this.statut = "En cours";
    }

    // ========== CALCULS ==========

    /**
     * Calcule le nombre de nuits entre dateDebut et dateFin
     * Version simplifiée (format jj/mm/aaaa)
     */
    public int calculerNombreNuits() {
        // Extraction des jours
        String[] debut = dateDebut.split("/");
        String[] fin = dateFin.split("/");

        try {
            int jourDebut = Integer.parseInt(debut[0]);
            int moisDebut = Integer.parseInt(debut[1]);
            int anneeDebut = Integer.parseInt(debut[2]);

            int jourFin = Integer.parseInt(fin[0]);
            int moisFin = Integer.parseInt(fin[1]);
            int anneeFin = Integer.parseInt(fin[2]);

            // Calcul simplifié (approximatif)
            int totalJoursDebut = anneeDebut * 365 + moisDebut * 30 + jourDebut;
            int totalJoursFin = anneeFin * 365 + moisFin * 30 + jourFin;

            int nuits = totalJoursFin - totalJoursDebut;

            return nuits > 0 ? nuits : 1; // Minimum 1 nuit
        } catch (Exception e) {
            System.out.println("Erreur de format de date, 1 nuit par défaut");
            return 1;
        }
    }

    /**
     * Calcule le prix total de la chambre
     */
    public double calculerPrixChambre() {
        int nbNuits = calculerNombreNuits();
        return chambre.CalculerPrix(nbNuits);
    }

    /**
     * Calcule le prix total des services
     */
    public double calculerPrixServices() {
        double total = 0;
        for (Service s : services) {
            total += s.getPrix();
        }
        return total;
    }

    /**
     * Calcule le prix total de la réservation
     */
    public double calculerPrixTotal() {
        return calculerPrixChambre() + calculerPrixServices();
    }

    // ========== GESTION DES SERVICES ==========

    public void ajouterService(Service service) {
        if (service != null) {
            services.add(service);
            System.out.println("✓ Service '" + service.getNom() + "' ajouté à la réservation");
        }
    }

    public void retirerService(Service service) {
        if (services.remove(service)) {
            System.out.println("✓ Service retiré");
        } else {
            System.out.println("❌ Service non trouvé");
        }
    }

    // ========== GESTION DU STATUT ==========

    public void annuler() {
        this.statut = "Annulée";
        System.out.println("⚠ Réservation #" + numeroReservation + " annulée");
    }

    public void confirmer() {
        this.statut = "Confirmée";
        System.out.println("✓ Réservation #" + numeroReservation + " confirmée");
    }

    public void terminer() {
        this.statut = "Terminée";
        System.out.println("✓ Réservation #" + numeroReservation + " terminée");
    }

    // ========== GETTERS & SETTERS ==========

    public int getNumeroReservation() {
        return numeroReservation;
    }

    public Client getClient() {
        return client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public String getStatut() {
        return statut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // ========== AFFICHAGE ==========

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("   📋 RÉSERVATION #").append(numeroReservation).append("\n");
        sb.append("╚════════════════════════════════════════╝\n");
        sb.append("\n👤 Client : ").append(client.getNomComplet());
        sb.append("\n🏠 Chambre : ").append(chambre.getType()).append(" n°").append(chambre.getNumero());
        sb.append("\n📅 Période : ").append(dateDebut).append(" → ").append(dateFin);
        sb.append("\n🌙 Nuits : ").append(calculerNombreNuits());
        sb.append("\n📊 Statut : ").append(statut);

        if (!services.isEmpty()) {
            sb.append("\n\n🍽️  Services commandés :");
            for (Service s : services) {
                sb.append("\n   • ").append(s.getNom()).append(" (").append(s.getPrix()).append("€)");
            }
        }

        sb.append("\n\n💰 TARIF :");
        sb.append("\n   Chambre : ").append(String.format("%.2f", calculerPrixChambre())).append("€");
        sb.append("\n   Services : ").append(String.format("%.2f", calculerPrixServices())).append("€");
        sb.append("\n   ─────────────────");
        sb.append("\n   TOTAL : ").append(String.format("%.2f", calculerPrixTotal())).append("€");

        return sb.toString();
    }
}