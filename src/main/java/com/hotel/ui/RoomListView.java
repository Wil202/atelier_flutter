package com.hotel.ui;

import com.hotel.Hotel;
import com.hotel.Chambre;
import com.hotel.ChambreDouble;
import com.hotel.ChambreSimple;
import com.hotel.Suite;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class RoomListView extends VBox {

    private Hotel hotel;
    private TableView<Chambre> table;
    private ObservableList<Chambre> roomData;

    public RoomListView(Hotel hotel) {
        this.hotel = hotel;
        setSpacing(20);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f1f3f5;");

        Label title = new Label("Gestion des Chambres");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        roomData = FXCollections.observableArrayList(hotel.getChambres());
        HBox actions = createActionToolbar();
        table = createTable();

        getChildren().addAll(title, actions, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    private HBox createActionToolbar() {
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Button btnAdd = new Button("Nouvelle Chambre");
        btnAdd.setGraphic(new FontIcon(FontAwesomeSolid.PLUS));
        btnAdd.getStyleClass().add("accent");
        btnAdd.setOnAction(e -> showAddRoomDialog());

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher une chambre...");
        searchField.setPrefWidth(250);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ComboBox<String> typeFilter = new ComboBox<>(
                FXCollections.observableArrayList("Tous les types", "Simple", "Double", "Suite"));
        typeFilter.getSelectionModel().selectFirst();

        toolbar.getChildren().addAll(btnAdd, searchField, spacer, typeFilter);
        return toolbar;
    }

    private TableView<Chambre> createTable() {
        TableView<Chambre> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Chambre, Integer> colNum = new TableColumn<>("Numéro");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNum.setPrefWidth(100);

        TableColumn<Chambre, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colType.setPrefWidth(150);

        TableColumn<Chambre, Double> colPrix = new TableColumn<>("Prix / Nuit");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixParNuit"));
        colPrix.setPrefWidth(150);

        TableColumn<Chambre, Integer> colCap = new TableColumn<>("Capacité");
        colCap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        colCap.setPrefWidth(100);

        TableColumn<Chambre, Boolean> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("occupe"));
        colStatut.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean occupe, boolean empty) {
                super.updateItem(occupe, empty);
                if (empty || occupe == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label badge = new Label(occupe ? "Occupé" : "Disponible");
                    badge.setPadding(new Insets(2, 8, 2, 8));
                    if (occupe) {
                        badge.setStyle(
                                "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; -fx-background-radius: 10;");
                    } else {
                        badge.setStyle(
                                "-fx-background-color: #dcfce7; -fx-text-fill: #166534; -fx-background-radius: 10;");
                    }
                    setGraphic(badge);
                }
            }
        });

        tv.getColumns().addAll(colNum, colType, colPrix, colCap, colStatut);
        tv.setItems(roomData);

        return tv;
    }

    private void showAddRoomDialog() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ajouter une chambre");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField numField = new TextField();
        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList("Simple", "Double", "Suite"));
        typeBox.getSelectionModel().selectFirst();
        TextField priceField = new TextField();

        grid.add(new Label("Numéro :"), 0, 0);
        grid.add(numField, 1, 0);
        grid.add(new Label("Type :"), 0, 1);
        grid.add(typeBox, 1, 1);
        grid.add(new Label("Prix/Nuit :"), 0, 2);
        grid.add(priceField, 1, 2);

        Button btnSave = new Button("Enregistrer");
        btnSave.getStyleClass().add("success");
        btnSave.setOnAction(e -> {
            try {
                int num = Integer.parseInt(numField.getText());
                double price = Double.parseDouble(priceField.getText());
                String type = typeBox.getValue();

                Chambre c;
                if (type.equals("Simple"))
                    c = new ChambreSimple(num, price);
                else if (type.equals("Double"))
                    c = new ChambreDouble(num, price);
                else
                    c = new Suite(num, price, false);

                hotel.ajouterChambre(c);
                roomData.setAll(hotel.getChambres());
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Données invalides !");
                alert.show();
            }
        });

        layout.getChildren().addAll(grid, btnSave);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
