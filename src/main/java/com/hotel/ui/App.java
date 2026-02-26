package com.hotel.ui;

import atlantafx.base.theme.PrimerLight;
import com.hotel.Hotel;
import com.hotel.Chambre;
import com.hotel.ChambreSimple;
import com.hotel.ChambreDouble;
import com.hotel.Suite;
import com.hotel.Client;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class App extends Application {

    private Hotel hotel;
    private BorderPane mainLayout;
    private Button activeButton;

    @Override
    public void start(Stage primaryStage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        initialiserDonnees();

        mainLayout = new BorderPane();
        mainLayout.setLeft(createSidebar());
        mainLayout.setCenter(createDashboardView());
        mainLayout.setTop(createTopBar());

        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setTitle("Hôtel Management System - Modern UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialiserDonnees() {
        hotel = new Hotel("Grand Hôtel Moderne", "123 Avenue du Design, Paris");
        // Ajouter quelques données de test
        hotel.ajouterChambre(new ChambreSimple(101, 85.0));
        hotel.ajouterChambre(new ChambreSimple(102, 85.0));
        hotel.ajouterChambre(new ChambreDouble(201, 120.0));
        hotel.ajouterChambre(new ChambreDouble(202, 120.0));
        hotel.ajouterChambre(new Suite(301, 250.0, true));

        Client c1 = new Client("Durand", "Jean", "jean.durand@email.com", "0123456789");
        Client c2 = new Client("Lefebvre", "Marie", "marie.l@email.com", "0987654321");
        hotel.ajouterClient(c1);
        hotel.ajouterClient(c2);

        // Ajouter une réservation test
        hotel.creerReservation(c1, hotel.getChambres().get(0), "2026-03-01", "2026-03-05");
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(240);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 1 0 0;");

        Label brandLabel = new Label("HOTEL MANAGER");
        brandLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #135bec;");
        brandLabel.setPadding(new Insets(0, 0, 20, 0));

        Button btnDashboard = createSidebarButton("Tableau de bord", FontAwesomeSolid.CHART_PIE);
        btnDashboard.setOnAction(e -> navigate(btnDashboard, createDashboardView()));

        Button btnReservations = createSidebarButton("Réservations", FontAwesomeSolid.CALENDAR_ALT);
        btnReservations.setOnAction(e -> navigate(btnReservations, new ReservationListView(hotel)));

        Button btnChambres = createSidebarButton("Chambres", FontAwesomeSolid.BED);
        btnChambres.setOnAction(e -> navigate(btnChambres, new RoomListView(hotel)));

        Button btnClients = createSidebarButton("Clients", FontAwesomeSolid.USERS);
        btnClients.setOnAction(e -> navigate(btnClients, new ClientListView(hotel)));

        Button btnStats = createSidebarButton("Statistiques", FontAwesomeSolid.CHART_LINE);
        btnStats.setOnAction(e -> navigate(btnStats, new StatisticsView(hotel)));

        Button btnSettings = createSidebarButton("Paramètres", FontAwesomeSolid.COG);
        btnSettings.setOnAction(e -> {
            Label placeholder = new Label("Paramètres - Bientôt disponible");
            placeholder.setStyle("-fx-font-size: 20px;");
            navigate(btnSettings, new StackPane(placeholder));
        });

        // Set initial active state
        setActiveButton(btnDashboard);

        sidebar.getChildren().addAll(brandLabel, btnDashboard, btnReservations, btnChambres, btnClients, btnStats,
                new Region(), btnSettings);

        VBox.setVgrow(sidebar.getChildren().get(6), Priority.ALWAYS);

        return sidebar;
    }

    private void Vgrow(javafx.scene.Node node, Priority priority) {
        VBox.setVgrow(node, priority);
    }

    private void navigate(Button btn, javafx.scene.Node view) {
        setActiveButton(btn);
        mainLayout.setCenter(view);
    }

    private void setActiveButton(Button btn) {
        if (activeButton != null) {
            activeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #495057;");
        }
        activeButton = btn;
        activeButton.setStyle("-fx-background-color: #e9ecef; -fx-text-fill: #135bec; -fx-font-weight: bold;");
    }

    private Button createSidebarButton(String text, FontAwesomeSolid icon) {
        Button btn = new Button(text);
        btn.setGraphic(new FontIcon(icon));
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(10, 15, 10, 15));
        btn.getStyleClass().add("sidebar-button");
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-text-fill: #495057;");

        btn.setOnMouseEntered(e -> {
            if (btn != activeButton) {
                btn.setStyle("-fx-background-color: #f1f3f5; -fx-cursor: hand; -fx-text-fill: #495057;");
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeButton) {
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-text-fill: #495057;");
            } else {
                setActiveButton(btn); // Restore active style
            }
        });

        return btn;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15, 25, 15, 25));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-width: 0 0 1 0;");

        CustomTextField searchField = new CustomTextField();
        searchField.setPromptText("Rechercher...");
        searchField.setPrefWidth(300);
        searchField.setLeft(new FontIcon(FontAwesomeSolid.SEARCH));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        FontIcon bellIcon = new FontIcon(FontAwesomeSolid.BELL);
        bellIcon.setIconSize(18);

        HBox userProfile = new HBox(10);
        userProfile.setAlignment(Pos.CENTER_LEFT);
        Label userName = new Label("Manager");
        userName.setStyle("-fx-font-weight: bold;");
        FontIcon userIcon = new FontIcon(FontAwesomeSolid.USER_CIRCLE);
        userIcon.setIconSize(24);
        userProfile.getChildren().addAll(userName, userIcon);

        topBar.getChildren().addAll(searchField, spacer, bellIcon, userProfile);
        return topBar;
    }

    private ScrollPane createDashboardView() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #f1f3f5;");

        Label title = new Label("Tableau de bord");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Calculate real stats
        double occupation = hotel.calculerTauxOccupation();
        double revenue = hotel.calculerChiffreAffaires();
        long pendingCheckins = hotel.getReservations().stream()
                .filter(r -> "En attente".equalsIgnoreCase(r.getStatut())).count();
        long reservationsToday = hotel.getReservations().size(); // Simplified for now

        // Stats Cards
        HBox statsBox = new HBox(20);
        statsBox.getChildren().addAll(
                createStatCard("Taux d'occupation", String.format("%.1f%%", occupation), FontAwesomeSolid.PERCENT,
                        "#135bec"),
                createStatCard("Réservations (Total)", String.valueOf(reservationsToday),
                        FontAwesomeSolid.CALENDAR_CHECK, "#10b981"),
                createStatCard("Chiffre d'affaires", String.format("%.2f€", revenue), FontAwesomeSolid.EURO_SIGN,
                        "#f59e0b"),
                createStatCard("Check-ins en attente", String.valueOf(pendingCheckins), FontAwesomeSolid.CLOCK,
                        "#ef4444"));

        // Room Status Grid
        Label sectionTitle = new Label("Statut des Chambres");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        FlowPane roomGrid = new FlowPane(15, 15);
        for (Chambre c : hotel.getChambres()) {
            roomGrid.getChildren().add(createRoomTile(c));
        }

        content.getChildren().addAll(title, statsBox, sectionTitle, roomGrid);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #f1f3f5;");
        return scroll;
    }

    private VBox createStatCard(String title, String value, FontAwesomeSolid icon, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(240);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

        HBox header = new HBox();
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 14px;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        FontIcon iconNode = new FontIcon(icon);
        iconNode.setIconSize(20);
        iconNode.setIconColor(Color.web(color));
        header.getChildren().addAll(lblTitle, spacer, iconNode);

        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        card.getChildren().addAll(header, lblValue);
        return card;
    }

    private VBox createRoomTile(Chambre c) {
        VBox tile = new VBox(5);
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(100, 100);

        boolean occupies = c.isOccupe();
        String color = occupies ? "#ef4444" : "#10b981";

        tile.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: " + color
                + "; -fx-border-width: 0 0 4 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 5, 0, 0, 2);");

        Label lblNum = new Label("N°" + c.getNumero());
        lblNum.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label lblType = new Label(c.getType());
        lblType.setStyle("-fx-font-size: 10px; -fx-text-fill: #9CA3AF;");
        Label lblStat = new Label(occupies ? "Occupé" : "Libre");
        lblStat.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d;");

        tile.getChildren().addAll(lblNum, lblType, lblStat);
        return tile;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
