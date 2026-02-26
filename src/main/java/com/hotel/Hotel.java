package com.hotel;

import java.util.ArrayList;

/**
 * Classe Hotel - Gestion complète d'un hôtel
 * Version intégrée avec les classes des autres membres
 */
public class Hotel {
    // Attributs
    private String nom;
    private String adresse;
    private ArrayList<Chambre> chambres;
    private ArrayList<Client> clients;
    private ArrayList<Reservation> reservations;
    private ArrayList<Service> servicesDisponibles;

    // Constructeur
    public Hotel(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.chambres = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.servicesDisponibles = new ArrayList<>();
        initialiserServices();
    }

    // Initialisation des services disponibles
    private void initialiserServices() {
        servicesDisponibles.add(new Service("Petit-déjeuner", 15.0, "Buffet continental"));
        servicesDisponibles.add(new Service("Dîner au restaurant", 35.0, "Menu gastronomique"));
        servicesDisponibles.add(new Service("Spa (1h)", 50.0, "Massage relaxant"));
        servicesDisponibles.add(new Service("Parking", 10.0, "Parking sécurisé par jour"));
        servicesDisponibles.add(new Service("Wifi Premium", 5.0, "Internet haut débit"));
    }

    // ========== GESTION DES CHAMBRES ==========

    public void ajouterChambre(Chambre c) {
        if (c != null) {
            chambres.add(c);
            System.out.println("✓ Chambre " + c.getNumero() + " ajoutée avec succès!");
        }
    }

    public void afficherToutesLesChambres() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("📋 LISTE DE TOUTES LES CHAMBRES");
        System.out.println("═══════════════════════════════════════");

        if (chambres.isEmpty()) {
            System.out.println("❌ Aucune chambre enregistrée.");
            return;
        }

        for (Chambre c : chambres) {
            System.out.println(c);
            System.out.println("───────────────────────────────────────");
        }
        System.out.println("Total : " + chambres.size() + " chambre(s)");
    }

    public void afficherChambresDisponibles() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("🟢 CHAMBRES DISPONIBLES");
        System.out.println("═══════════════════════════════════════");

        int count = 0;
        for (Chambre c : chambres) {
            if (!c.isOccupe()) {
                System.out.println(c);
                System.out.println("───────────────────────────────────────");
                count++;
            }
        }

        if (count == 0) {
            System.out.println("❌ Aucune chambre disponible actuellement.");
        } else {
            System.out.println("Total : " + count + " chambre(s) disponible(s)");
        }
    }

    public Chambre rechercherChambre(int numero) {
        for (Chambre c : chambres) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Chambre> rechercherChambresParType(String type) {
        ArrayList<Chambre> resultats = new ArrayList<>();
        for (Chambre c : chambres) {
            if (c.getType().equalsIgnoreCase(type) && !c.isOccupe()) {
                resultats.add(c);
            }
        }
        return resultats;
    }

    public ArrayList<Chambre> rechercherChambresParPrix(double prixMax) {
        ArrayList<Chambre> resultats = new ArrayList<>();
        for (Chambre c : chambres) {
            if (c.getPrixParNuit() <= prixMax && !c.isOccupe()) {
                resultats.add(c);
            }
        }
        return resultats;
    }

    // ========== GESTION DES CLIENTS ==========

    public void ajouterClient(Client c) {
        if (c != null) {
            clients.add(c);
            System.out.println("✓ Client " + c.getNomComplet() + " ajouté avec succès!");
        }
    }

    public void afficherTousLesClients() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("👥 LISTE DE TOUS LES CLIENTS");
        System.out.println("═══════════════════════════════════════");

        if (clients.isEmpty()) {
            System.out.println("❌ Aucun client enregistré.");
            return;
        }

        for (Client c : clients) {
            System.out.println(c);
            System.out.println("───────────────────────────────────────");
        }
        System.out.println("Total : " + clients.size() + " client(s)");
    }

    public Client rechercherClient(int numero) {
        for (Client c : clients) {
            if (c.getNumeroClient() == numero) {
                return c;
            }
        }
        return null;
    }

    public Client rechercherClientParEmail(String email) {
        for (Client c : clients) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    public void modifierClient(int numero, String nom, String prenom, String email, String telephone) {
        Client c = rechercherClient(numero);

        if (c == null) {
            System.out.println("❌ Client introuvable.");
            return;
        }

        c.setNom(nom);
        c.setPrenom(prenom);
        c.setEmail(email);
        c.setTelephone(telephone);

        System.out.println("✓ Informations du client mises à jour!");
    }

    // ========== GESTION DES RÉSERVATIONS ==========

    public Reservation creerReservation(Client client, Chambre chambre, String dateDebut, String dateFin) {
        if (chambre.isOccupe()) {
            System.out.println("❌ Cette chambre est déjà occupée!");
            return null;
        }

        Reservation reservation = new Reservation(client, chambre, dateDebut, dateFin);
        reservations.add(reservation);
        chambre.setOccupe(true);

        System.out.println("✓ Réservation créée avec succès!");
        System.out.println("Numéro de réservation : " + reservation.getNumeroReservation());

        return reservation;
    }

    public void afficherToutesLesReservations() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("📅 TOUTES LES RÉSERVATIONS");
        System.out.println("═══════════════════════════════════════");

        if (reservations.isEmpty()) {
            System.out.println("❌ Aucune réservation enregistrée.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
            System.out.println("═══════════════════════════════════════");
        }
        System.out.println("Total : " + reservations.size() + " réservation(s)");
    }

    public void afficherReservationsClient(Client client) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("📅 RÉSERVATIONS DE " + client.getNomComplet().toUpperCase());
        System.out.println("═══════════════════════════════════════");

        int count = 0;
        for (Reservation r : reservations) {
            if (r.getClient().getNumeroClient() == client.getNumeroClient()) {
                System.out.println(r);
                System.out.println("───────────────────────────────────────");
                count++;
            }
        }

        if (count == 0) {
            System.out.println("❌ Aucune réservation pour ce client.");
        } else {
            System.out.println("Total : " + count + " réservation(s)");
        }
    }

    public Reservation rechercherReservation(int numero) {
        for (Reservation r : reservations) {
            if (r.getNumeroReservation() == numero) {
                return r;
            }
        }
        return null;
    }

    public void annulerReservation(int numero) {
        Reservation r = rechercherReservation(numero);
        if (r != null) {
            r.annuler();
            r.getChambre().setOccupe(false);
            System.out.println("✓ Réservation #" + numero + " annulée avec succès!");
        } else {
            System.out.println("❌ Réservation introuvable.");
        }
    }

    public void terminerReservation(int numero) {
        Reservation r = rechercherReservation(numero);
        if (r != null) {
            r.setStatut("Terminée");
            r.getChambre().setOccupe(false);
            System.out.println("✓ Check-out effectué! Réservation #" + numero + " terminée.");
            System.out.println("💰 Montant total : " + r.calculerPrixTotal() + "€");
        } else {
            System.out.println("❌ Réservation introuvable.");
        }
    }

    // ========== STATISTIQUES ==========

    public double calculerChiffreAffaires() {
        double total = 0;
        for (Reservation r : reservations) {
            if (!r.getStatut().equals("Annulée")) {
                total += r.calculerPrixTotal();
            }
        }
        return total;
    }

    public double calculerTauxOccupation() {
        if (chambres.isEmpty())
            return 0;

        int chambresOccupees = 0;
        for (Chambre c : chambres) {
            if (c.isOccupe()) {
                chambresOccupees++;
            }
        }

        return (chambresOccupees * 100.0) / chambres.size();
    }

    public Chambre getChambrePlusReservee() {
        if (chambres.isEmpty() || reservations.isEmpty())
            return null;

        Chambre plusReservee = null;
        int maxReservations = 0;

        for (Chambre c : chambres) {
            int count = 0;
            for (Reservation r : reservations) {
                if (r.getChambre().getNumero() == c.getNumero()) {
                    count++;
                }
            }
            if (count > maxReservations) {
                maxReservations = count;
                plusReservee = c;
            }
        }

        return plusReservee;
    }

    public void afficherStatistiques() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       📊 STATISTIQUES HÔTEL          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();

        System.out.println("🏨 Hôtel : " + nom);
        System.out.println("📍 Adresse : " + adresse);
        System.out.println();

        System.out.println("📈 Chiffres clés :");
        System.out.println("   • Chambres totales : " + chambres.size());
        System.out.println("   • Clients enregistrés : " + clients.size());
        System.out.println("   • Réservations totales : " + reservations.size());
        System.out.println();

        System.out.printf("💰 Chiffre d'affaires : %.2f€\n", calculerChiffreAffaires());
        System.out.printf("📊 Taux d'occupation : %.1f%%\n", calculerTauxOccupation());
        System.out.println();

        Chambre plusReservee = getChambrePlusReservee();
        if (plusReservee != null) {
            System.out.println("⭐ Chambre la plus réservée :");
            System.out.println("   " + plusReservee.getType() + " n°" + plusReservee.getNumero());
        }

        System.out.println("\n═══════════════════════════════════════");
    }

    // ========== GETTERS ==========

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public ArrayList<Chambre> getChambres() {
        return chambres;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public ArrayList<Service> getServicesDisponibles() {
        return servicesDisponibles;
    }
}