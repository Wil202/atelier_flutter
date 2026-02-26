# Gestion Hôtel 🏨

Une application moderne de gestion hôtelière développée par **Arnaud KEUTCHANKEU NJAMTA**.

Ce projet propose une interface utilisateur premium pour la gestion complète d'un complexe hôtelier, allant du suivi des réservations à l'analyse des performances financières.

## ✨ Fonctionnalités Principales

- **Tableau de Bord Dynamique** : Visualisation en temps réel du taux d'occupation, du chiffre d'affaires et de l'état des chambres.
- **Gestion des Chambres** : Système complet de gestion des types de chambres (Simple, Double, Suite) avec suivi de disponibilité.
- **Gestion des Clients** : Répertoire centralisé pour la gestion des informations clients.
- **Flux de Réservation** : Processus simplifié pour l'enregistrement (Check-in) et le départ (Check-out).
- **Statistiques Avancées** : Visualisation graphique des tendances et des revenus.
- **Interface Premium** : Design moderne utilisant le thème AtlantaFX avec support des modes clair et sombre.

## 🛠️ Technologies Utilisées

- **Langage** : Java 17
- **Framework UI** : JavaFX
- **Design System** : [AtlantaFX](https://github.com/mkpaz/atlantafx) (Primer Dark/Light)
- **Gestionnaire de Dépendances** : Maven
- **Icônes** : Ikonli (FontAwesome)

## 🚀 Installation et Lancement

### Prérequis
- Java JDK 17 ou supérieur.
- Maven installé sur votre système.

### Lancement
Pour exécuter le programme, utilisez la commande suivante à la racine du projet :

```bash
mvn javafx:run
```

## 🏗️ Architecture du Projet

Le projet suit une architecture modulaire pour séparer la logique métier de l'interface graphique :

- `com.hotel` : Logique métier et modèles de données (`Chambre`, `Reservation`, `Client`).
- `com.hotel.ui` : Composants et vues JavaFX (Dashboard, Listes, Statistiques).

---
© 2026 Arnaud KEUTCHANKEU NJAMTA
