package com.hotel.ui;

import com.hotel.Chambre;
import com.hotel.Client;
import com.hotel.Hotel;
import com.hotel.Reservation;
import com.hotel.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationListView extends VBox {

    private Hotel hotel;
    private TableView<Reservation> table;
    private ObservableList<Reservation> reservationData;

    public ReservationListView(Hotel hotel) {
        this.hotel = hotel;
        setSpacing(20);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f1f3f5;");

        Label title = new Label("Gestion des Réservations");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        reservationData = FXCollections.observableArrayList(hotel.getReservations());
        HBox actions = createActionToolbar();
        table = createTable();

        getChildren().addAll(title, actions, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    private HBox createActionToolbar() {
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Button btnAdd = new Button("Nouvelle Réservation");
        btnAdd.setGraphic(new FontIcon(FontAwesomeSolid.PLUS));
        btnAdd.getStyleClass().add("success");
        btnAdd.setOnAction(e -> showAddReservationDialog());

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher par client...");
        searchField.setPrefWidth(250);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        toolbar.getChildren().addAll(btnAdd, searchField, spacer);
        return toolbar;
    }

    private TableView<Reservation> createTable() {
        TableView<Reservation> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Reservation, Integer> colNum = new TableColumn<>("#");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numeroReservation"));
        colNum.setPrefWidth(60);

        TableColumn<Reservation, String> colClient = new TableColumn<>("Client");
        colClient.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getClient().getNomComplet()));
        colClient.setPrefWidth(200);

        TableColumn<Reservation, String> colChambre = new TableColumn<>("Chambre");
        colChambre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                "N°" + cellData.getValue().getChambre().getNumero()));
        colChambre.setPrefWidth(100);

        TableColumn<Reservation, String> colDebut = new TableColumn<>("Arrivée");
        colDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDebut.setPrefWidth(120);

        TableColumn<Reservation, String> colFin = new TableColumn<>("Départ");
        colFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colFin.setPrefWidth(120);

        TableColumn<Reservation, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colStatut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String statut, boolean empty) {
                super.updateItem(statut, empty);
                if (empty || statut == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label badge = new Label(statut);
                    badge.setPadding(new Insets(2, 8, 2, 8));
                    String color = "#6c757d"; // default
                    String bg = "#e9ecef";

                    if (statut.equalsIgnoreCase("Confirmée")) {
                        color = "#166534";
                        bg = "#dcfce7";
                    } else if (statut.equalsIgnoreCase("Annulée")) {
                        color = "#991b1b";
                        bg = "#fee2e2";
                    } else if (statut.equalsIgnoreCase("En attente")) {
                        color = "#854d0e";
                        bg = "#fef9c3";
                    }

                    badge.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: " + color
                            + "; -fx-background-radius: 10;");
                    setGraphic(badge);
                }
            }
        });

        tv.getColumns().addAll(colNum, colClient, colChambre, colDebut, colFin, colStatut);

        // Actions Column
        TableColumn<Reservation, Void> colActions = new TableColumn<>("Actions");
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button btnCheckout = new Button("Check-out");
            private final Button btnServices = new Button("Services");

            {
                btnCheckout.getStyleClass().add("button-outlined");
                btnCheckout.setStyle("-fx-font-size: 11px;");
                btnCheckout.setOnAction(event -> {
                    Reservation r = getTableView().getItems().get(getIndex());
                    if ("Terminée".equals(r.getStatut())) {
                        new Alert(Alert.AlertType.INFORMATION, "Cette réservation est déjà terminée.").show();
                        return;
                    }
                    hotel.terminerReservation(r.getNumeroReservation());
                    reservationData.setAll(hotel.getReservations());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Check-out réussi !\nMontant total : " + r.calculerPrixTotal() + "€");
                    alert.show();
                });

                btnServices.getStyleClass().add("button-outlined");
                btnServices.setStyle("-fx-font-size: 11px;");
                btnServices.setOnAction(event -> {
                    Reservation r = getTableView().getItems().get(getIndex());
                    if ("Terminée".equals(r.getStatut())) {
                        new Alert(Alert.AlertType.INFORMATION,
                                "Impossible d'ajouter des services à une réservation terminée.").show();
                        return;
                    }
                    showAddServiceDialog(r);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(5, btnServices, btnCheckout);
                    box.setAlignment(Pos.CENTER);
                    setGraphic(box);
                }
            }
        });
        tv.getColumns().add(colActions);

        tv.setItems(reservationData);

        return tv;
    }

    private void showAddReservationDialog() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Nouvelle Réservation");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ComboBox<Client> clientBox = new ComboBox<>(FXCollections.observableArrayList(hotel.getClients()));
        clientBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return client == null ? "" : client.getNomComplet();
            }

            @Override
            public Client fromString(String string) {
                return null;
            }
        });

        // Only show available rooms
        ComboBox<Chambre> roomBox = new ComboBox<>(FXCollections.observableArrayList(
                hotel.getChambres().stream().filter(c -> !c.isOccupe()).toList()));
        roomBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Chambre chambre) {
                return chambre == null ? "" : "Chambre " + chambre.getNumero() + " (" + chambre.getType() + ")";
            }

            @Override
            public Chambre fromString(String string) {
                return null;
            }
        });

        DatePicker dateStart = new DatePicker(LocalDate.now());
        DatePicker dateEnd = new DatePicker(LocalDate.now().plusDays(1));

        grid.add(new Label("Client :"), 0, 0);
        grid.add(clientBox, 1, 0);
        grid.add(new Label("Chambre :"), 0, 1);
        grid.add(roomBox, 1, 1);
        grid.add(new Label("Date Arrivée :"), 0, 2);
        grid.add(dateStart, 1, 2);
        grid.add(new Label("Date Départ :"), 0, 3);
        grid.add(dateEnd, 1, 3);

        Button btnSave = new Button("Confirmer la réservation");
        btnSave.getStyleClass().add("success");
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnSave.setOnAction(e -> {
            if (clientBox.getValue() == null || roomBox.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un client et une chambre.").show();
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String start = dateStart.getValue().format(formatter);
            String end = dateEnd.getValue().format(formatter);

            hotel.creerReservation(clientBox.getValue(), roomBox.getValue(), start, end);
            reservationData.setAll(hotel.getReservations());
            stage.close();
        });

        layout.getChildren().addAll(grid, btnSave);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddServiceDialog(Reservation r) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ajouter un service - Réservation #" + r.getNumeroReservation());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label info = new Label("Client : " + r.getClient().getNomComplet());
        ComboBox<Service> serviceBox = new ComboBox<>(
                FXCollections.observableArrayList(hotel.getServicesDisponibles()));
        serviceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Service s) {
                return s == null ? "" : s.getNom() + " (" + s.getPrix() + "€)";
            }

            @Override
            public Service fromString(String string) {
                return null;
            }
        });

        Button btnAdd = new Button("Ajouter à la facture");
        btnAdd.getStyleClass().add("success");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.setOnAction(e -> {
            if (serviceBox.getValue() != null) {
                r.ajouterService(serviceBox.getValue());
                new Alert(Alert.AlertType.INFORMATION, "Service ajouté avec succès !").show();
                stage.close();
            }
        });

        layout.getChildren().addAll(info, new Label("Sélectionnez un service :"), serviceBox, btnAdd);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
