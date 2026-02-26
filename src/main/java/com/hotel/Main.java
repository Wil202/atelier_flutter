package com.hotel;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Main - Point d'entrée de l'application
 * Gère l'interface console et les interactions utilisateur
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Hotel hotel;

    public static void main(String[] args) {
        afficherBanniere();
        initialiserHotel();
        menuPrincipal();
        scanner.close();
    }

    private static void afficherBanniere() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   🏨 SYSTÈME DE GESTION D'HÔTEL 🏨   	 ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    private static void initialiserHotel() {
        System.out.print("Nom de l'hôtel : ");
        String nom = scanner.nextLine();
        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();

        hotel = new Hotel(nom, adresse);

        // Données de test (optionnel - à commenter si non souhaité)
        ajouterDonneesTest();

        System.out.println("\n✓ Hôtel initialisé avec succès!\n");
        pause();
    }

    private static void ajouterDonneesTest() {
        // Ajouter quelques chambres
        hotel.ajouterChambre(new ChambreSimple(101, 50.0, false, 1));
        hotel.ajouterChambre(new ChambreSimple(102, 50.0, false, 1));
        hotel.ajouterChambre(new ChambreDouble(201, 80.0, false, 2, true));
        hotel.ajouterChambre(new ChambreDouble(202, 80.0, false, 2, false));
        hotel.ajouterChambre(new Suite(301, 150.0, false, 4, true, true));
        hotel.ajouterChambre(new Suite(302, 150.0, false, 4, false, true));

        // Ajouter quelques clients
        hotel.ajouterClient(new Client("Dupont", "Jean", "jean.dupont@email.com", "0601020304"));
        hotel.ajouterClient(new Client("Martin", "Sophie", "sophie.martin@email.com", "0612345678"));

        System.out.println("✓ Données de test ajoutées");
    }

    // ========== MENU PRINCIPAL ==========

    private static void menuPrincipal() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          📋 MENU PRINCIPAL            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. 🛏️  Gestion des Chambres");
            System.out.println("2. 👥 Gestion des Clients");
            System.out.println("3. 📅 Gestion des Réservations");
            System.out.println("4. 🍽️  Gestion des Services");
            System.out.println("5. 📊 Statistiques");
            System.out.println("0. ❌ Quitter");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    menuChambres();
                    break;
                case 2:
                    menuClients();
                    break;
                case 3:
                    menuReservations();
                    break;
                case 4:
                    menuServices();
                    break;
                case 5:
                    menuStatistiques();
                    break;
                case 0:
                    System.out.println("\n👋 Au revoir et à bientôt!");
                    break;
                default:
                    System.out.println("❌ Choix invalide!");
                    pause();
            }
        } while (choix != 0);
    }

    // ========== MENU CHAMBRES ==========

    private static void menuChambres() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       🛏️  GESTION DES CHAMBRES       ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Ajouter une chambre");
            System.out.println("2. Afficher toutes les chambres");
            System.out.println("3. Afficher les chambres disponibles");
            System.out.println("4. Rechercher une chambre par numéro");
            System.out.println("5. Rechercher par type");
            System.out.println("6. Rechercher par prix maximum");
            System.out.println("0. Retour au menu principal");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        ajouterChambre();
                        break;
                    case 2:
                        hotel.afficherToutesLesChambres();
                        pause();
                        break;
                    case 3:
                        hotel.afficherChambresDisponibles();
                        pause();
                        break;
                    case 4:
                        rechercherChambreParNumero();
                        break;
                    case 5:
                        rechercherChambreParType();
                        break;
                    case 6:
                        rechercherChambreParPrix();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("❌ Choix invalide!");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                pause();
            }
        } while (choix != 0);
    }

    private static void ajouterChambre() {
        System.out.println("\n=== Ajouter une chambre ===");
        System.out.println("Type de chambre :");
        System.out.println("1. Chambre Simple (50€/nuit)");
        System.out.println("2. Chambre Double (80€/nuit)");
        System.out.println("3. Suite (150€/nuit)");

        int type = lireEntier("Votre choix : ");
        int numero = lireEntier("Numéro de la chambre : ");

        Chambre chambre = null;

        switch (type) {
            case 1:
                chambre = new ChambreSimple(numero, 50.0, false, 1);
                break;
            case 2:
                boolean litsJumeaux = lireOuiNon("Lits jumeaux ? (o/n) : ");
                chambre = new ChambreDouble(numero, 80.0, false, 2, litsJumeaux);
                break;
            case 3:
                boolean jacuzzi = lireOuiNon("Jacuzzi ? (o/n) : ");
                boolean balcon = lireOuiNon("Balcon ? (o/n) : ");
                chambre = new Suite(numero, 150.0, false, 4, jacuzzi, balcon);
                break;
            default:
                System.out.println("❌ Type invalide!");
                pause();
                return;
        }

        hotel.ajouterChambre(chambre);
        pause();
    }

    private static void rechercherChambreParNumero() {
        int numero = lireEntier("\nNuméro de la chambre : ");
        Chambre c = hotel.rechercherChambre(numero);

        if (c != null) {
            System.out.println("\n✓ Chambre trouvée :");
            System.out.println(c);
        } else {
            System.out.println("❌ Chambre introuvable.");
        }
        pause();
    }

    private static void rechercherChambreParType() {
        scanner.nextLine(); // Vider buffer
        System.out.print("\nType de chambre (Simple/Double/Suite) : ");
        String type = scanner.nextLine();

        ArrayList<Chambre> resultats = hotel.rechercherChambresParType(type);

        System.out.println("\n=== Résultats ===");
        if (resultats.isEmpty()) {
            System.out.println("❌ Aucune chambre disponible de ce type.");
        } else {
            for (Chambre c : resultats) {
                System.out.println(c);
                System.out.println("───────────────────────────");
            }
        }
        pause();
    }

    private static void rechercherChambreParPrix() {
        double prixMax = lireDouble("\nPrix maximum par nuit : ");

        ArrayList<Chambre> resultats = hotel.rechercherChambresParPrix(prixMax);

        System.out.println("\n=== Résultats ===");
        if (resultats.isEmpty()) {
            System.out.println("❌ Aucune chambre disponible dans cette gamme de prix.");
        } else {
            for (Chambre c : resultats) {
                System.out.println(c);
                System.out.println("───────────────────────────");
            }
        }
        pause();
    }

    // ========== MENU CLIENTS ==========

    private static void menuClients() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        👥 GESTION DES CLIENTS         ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Rechercher un client");
            System.out.println("4. Modifier les informations d'un client");
            System.out.println("0. Retour au menu principal");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        ajouterClient();
                        break;
                    case 2:
                        hotel.afficherTousLesClients();
                        pause();
                        break;
                    case 3:
                        rechercherClient();
                        break;
                    case 4:
                        modifierClient();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("❌ Choix invalide!");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                pause();
            }
        } while (choix != 0);
    }

    private static void ajouterClient() {
        scanner.nextLine(); // Vider buffer
        System.out.println("\n=== Ajouter un client ===");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();

        Client client = new Client(nom, prenom, email, telephone);

        // Validation de l'email
        if (client.validerEmail()) {
            hotel.ajouterClient(client);
        } else {
            System.out.println("❌ Email invalide! Client non ajouté.");
        }

        pause();
    }

    private static void rechercherClient() {
        int numero = lireEntier("\nNuméro du client : ");
        Client c = hotel.rechercherClient(numero);

        if (c != null) {
            System.out.println("\n✓ Client trouvé :");
            System.out.println(c);
        } else {
            System.out.println("❌ Client introuvable.");
        }
        pause();
    }

    private static void modifierClient() {
        int numero = lireEntier("\nNuméro du client à modifier : ");
        Client c = hotel.rechercherClient(numero);

        if (c == null) {
            System.out.println("❌ Client introuvable.");
            pause();
            return;
        }

        scanner.nextLine(); // Vider buffer
        System.out.println("\nClient actuel : " + c.getNomComplet());
        System.out.println("Entrez les nouvelles informations :");

        System.out.print("Nouveau nom : ");
        String nom = scanner.nextLine();

        System.out.print("Nouveau prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Nouvel email : ");
        String email = scanner.nextLine();

        System.out.print("Nouveau téléphone : ");
        String telephone = scanner.nextLine();

        hotel.modifierClient(numero, nom, prenom, email, telephone);
        pause();
    }

    // ========== MENU RÉSERVATIONS ==========

    private static void menuReservations() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║      📅 GESTION DES RÉSERVATIONS      ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Créer une réservation");
            System.out.println("2. Afficher toutes les réservations");
            System.out.println("3. Afficher les réservations d'un client");
            System.out.println("4. Rechercher une réservation");
            System.out.println("5. Ajouter des services à une réservation");
            System.out.println("6. Annuler une réservation");
            System.out.println("7. Terminer une réservation (check-out)");
            System.out.println("0. Retour au menu principal");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        creerReservation();
                        break;
                    case 2:
                        hotel.afficherToutesLesReservations();
                        pause();
                        break;
                    case 3:
                        afficherReservationsClient();
                        break;
                    case 4:
                        rechercherReservation();
                        break;
                    case 5:
                        ajouterServicesReservation();
                        break;
                    case 6:
                        annulerReservation();
                        break;
                    case 7:
                        terminerReservation();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("❌ Choix invalide!");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                pause();
            }
        } while (choix != 0);
    }

    private static void creerReservation() {
        System.out.println("\n=== Créer une réservation ===");

        // Sélectionner client
        int numeroClient = lireEntier("Numéro du client : ");
        Client client = hotel.rechercherClient(numeroClient);
        if (client == null) {
            System.out.println("❌ Client introuvable!");
            pause();
            return;
        }

        // Afficher chambres disponibles
        hotel.afficherChambresDisponibles();

        // Sélectionner chambre
        int numeroChambre = lireEntier("\nNuméro de la chambre : ");
        Chambre chambre = hotel.rechercherChambre(numeroChambre);
        if (chambre == null) {
            System.out.println("❌ Chambre introuvable!");
            pause();
            return;
        }

        // Dates
        scanner.nextLine(); // Vider buffer
        System.out.print("Date de début (jj/mm/aaaa) : ");
        String dateDebut = scanner.nextLine();

        System.out.print("Date de fin (jj/mm/aaaa) : ");
        String dateFin = scanner.nextLine();

        // Créer réservation
        Reservation reservation = hotel.creerReservation(client, chambre, dateDebut, dateFin);

        if (reservation != null) {
            System.out.println("\n💰 Prix estimé : " + reservation.calculerPrixTotal() + "€");
        }

        pause();
    }

    private static void afficherReservationsClient() {
        int numeroClient = lireEntier("\nNuméro du client : ");
        Client client = hotel.rechercherClient(numeroClient);

        if (client == null) {
            System.out.println("❌ Client introuvable!");
        } else {
            hotel.afficherReservationsClient(client);
        }
        pause();
    }

    private static void rechercherReservation() {
        int numero = lireEntier("\nNuméro de la réservation : ");
        Reservation r = hotel.rechercherReservation(numero);

        if (r != null) {
            System.out.println("\n✓ Réservation trouvée :");
            System.out.println(r);
        } else {
            System.out.println("❌ Réservation introuvable.");
        }
        pause();
    }

    private static void ajouterServicesReservation() {
        int numero = lireEntier("\nNuméro de la réservation : ");
        Reservation r = hotel.rechercherReservation(numero);

        if (r == null) {
            System.out.println("❌ Réservation introuvable!");
            pause();
            return;
        }

        // Afficher services disponibles
        System.out.println("\n=== Services disponibles ===");
        ArrayList<Service> services = hotel.getServicesDisponibles();
        for (int i = 0; i < services.size(); i++) {
            System.out.println((i + 1) + ". " + services.get(i));
        }

        int choixService = lireEntier("\nNuméro du service à ajouter (0 pour annuler) : ");

        if (choixService > 0 && choixService <= services.size()) {
            Service service = services.get(choixService - 1);
            r.ajouterService(service);
            System.out.println("💰 Nouveau total : " + r.calculerPrixTotal() + "€");
        }

        pause();
    }

    private static void annulerReservation() {
        int numero = lireEntier("\nNuméro de la réservation à annuler : ");

        if (lireOuiNon("Confirmer l'annulation ? (o/n) : ")) {
            hotel.annulerReservation(numero);
        } else {
            System.out.println("Annulation abandonnée.");
        }

        pause();
    }

    private static void terminerReservation() {
        int numero = lireEntier("\nNuméro de la réservation (check-out) : ");
        hotel.terminerReservation(numero);
        pause();
    }

    // ========== MENU SERVICES ==========

    private static void menuServices() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       🍽️  GESTION DES SERVICES       ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Afficher les services disponibles");
            System.out.println("2. Ajouter un nouveau service");
            System.out.println("0. Retour au menu principal");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        afficherServices();
                        break;
                    case 2:
                        ajouterService();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("❌ Choix invalide!");
                        pause();
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                pause();
            }
        } while (choix != 0);
    }

    private static void afficherServices() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("🍽️  SERVICES DISPONIBLES");
        System.out.println("═══════════════════════════════════════");

        for (Service s : hotel.getServicesDisponibles()) {
            System.out.println(s);
            System.out.println("───────────────────────────────────────");
        }
        pause();
    }

    private static void ajouterService() {
        scanner.nextLine(); // Vider buffer
        System.out.println("\n=== Ajouter un service ===");

        System.out.print("Nom du service : ");
        String nom = scanner.nextLine();

        double prix = lireDouble("Prix : ");

        System.out.print("Description : ");
        String description = scanner.nextLine();

        Service service = new Service(nom, prix, description);
        hotel.getServicesDisponibles().add(service);

        System.out.println("✓ Service ajouté avec succès!");
        pause();
    }

    // ========== MENU STATISTIQUES ==========

    private static void menuStatistiques() {
        int choix;
        do {
            clearScreen();
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          📊 STATISTIQUES              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("\n1. Afficher le chiffre d'affaires");
            System.out.println("2. Afficher le taux d'occupation");
            System.out.println("3. Afficher la chambre la plus réservée");
            System.out.println("4. Afficher les statistiques complètes");
            System.out.println("0. Retour au menu principal");
            System.out.println("\n════════════════════════════════════════");

            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    System.out.printf("\n💰 Chiffre d'affaires : %.2f€\n", hotel.calculerChiffreAffaires());
                    pause();
                    break;
                case 2:
                    System.out.printf("\n📊 Taux d'occupation : %.1f%%\n", hotel.calculerTauxOccupation());
                    pause();
                    break;
                case 3:
                    Chambre c = hotel.getChambrePlusReservee();
                    if (c != null) {
                        System.out.println("\n⭐ Chambre la plus réservée :");
                        System.out.println(c);
                    } else {
                        System.out.println("❌ Aucune donnée disponible.");
                    }
                    pause();
                    break;
                case 4:
                    hotel.afficherStatistiques();
                    pause();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Choix invalide!");
                    pause();
            }
        } while (choix != 0);
    }

    // ========== UTILITAIRES ==========

    private static int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide!");
            }
        }
    }

    private static double lireDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide!");
            }
        }
    }

    private static boolean lireOuiNon(String message) {
        while (true) {
            System.out.print(message);
            String reponse = scanner.nextLine().toLowerCase();
            if (reponse.equals("o") || reponse.equals("oui"))
                return true;
            if (reponse.equals("n") || reponse.equals("non"))
                return false;
            System.out.println("❌ Répondez par 'o' ou 'n'!");
        }
    }

    private static void pause() {
        System.out.print("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private static void clearScreen() {
        // Simule un effacement d'écran
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}