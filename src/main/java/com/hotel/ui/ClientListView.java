package com.hotel.ui;

import com.hotel.Hotel;
import com.hotel.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class ClientListView extends VBox {

    private Hotel hotel;
    private TableView<Client> table;
    private ObservableList<Client> clientData;

    public ClientListView(Hotel hotel) {
        this.hotel = hotel;
        setSpacing(20);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f1f3f5;");

        Label title = new Label("Gestion des Clients");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        clientData = FXCollections.observableArrayList(hotel.getClients());
        HBox actions = createActionToolbar();
        table = createTable();

        getChildren().addAll(title, actions, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    private HBox createActionToolbar() {
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Button btnAdd = new Button("Nouveau Client");
        btnAdd.setGraphic(new FontIcon(FontAwesomeSolid.USER_PLUS));
        btnAdd.getStyleClass().add("accent");
        btnAdd.setOnAction(e -> showAddClientDialog());

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher un client (Nom, Email)...");
        searchField.setPrefWidth(300);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnExport = new Button("Exporter");
        btnExport.setGraphic(new FontIcon(FontAwesomeSolid.FILE_EXPORT));

        toolbar.getChildren().addAll(btnAdd, searchField, spacer, btnExport);
        return toolbar;
    }

    private TableView<Client> createTable() {
        TableView<Client> tv = new TableView<>();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Client, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("numeroClient"));
        colId.setPrefWidth(80);

        TableColumn<Client, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);

        TableColumn<Client, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colPrenom.setPrefWidth(150);

        TableColumn<Client, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(250);

        TableColumn<Client, String> colTel = new TableColumn<>("Téléphone");
        colTel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colTel.setPrefWidth(150);

        tv.getColumns().addAll(colId, colNom, colPrenom, colEmail, colTel);
        tv.setItems(clientData);

        return tv;
    }

    private void showAddClientDialog() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Nouveau Client");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField emailField = new TextField();
        TextField telField = new TextField();

        grid.add(new Label("Nom :"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom :"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Email :"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Téléphone :"), 0, 3);
        grid.add(telField, 1, 3);

        Button btnSave = new Button("Enregistrer");
        btnSave.getStyleClass().add("success");
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnSave.setOnAction(e -> {
            if (nomField.getText().isEmpty() || emailField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Veuillez remplir les champs obligatoires.").show();
                return;
            }
            Client c = new Client(nomField.getText(), prenomField.getText(), emailField.getText(), telField.getText());
            hotel.ajouterClient(c);
            clientData.setAll(hotel.getClients());
            stage.close();
        });

        layout.getChildren().addAll(grid, btnSave);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
