package com.hotel.ui;

import com.hotel.Chambre;
import com.hotel.Hotel;
import com.hotel.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class StatisticsView extends ScrollPane {

    private Hotel hotel;

    public StatisticsView(Hotel hotel) {
        this.hotel = hotel;
        setFitToWidth(true);
        setStyle("-fx-background-color: transparent; -fx-background: #f1f3f5;");

        VBox content = new VBox(30);
        content.setPadding(new Insets(30));

        Label title = new Label("Analyse des Performances");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Key Metrics Row
        HBox metricsRow = createMetricsRow();

        // Charts Row
        HBox chartsRow = new HBox(30);
        chartsRow.getChildren().addAll(createOccupancyChart(), createRevenueChart());

        // Top Performers
        VBox performersSection = createPerformersSection();

        content.getChildren().addAll(title, metricsRow, chartsRow, performersSection);
        setContent(content);
    }

    private HBox createMetricsRow() {
        HBox row = new HBox(20);
        row.getChildren().addAll(
                createMiniCard("Revenu Global", String.format("%.2f€", hotel.calculerChiffreAffaires()),
                        FontAwesomeSolid.MONEY_BILL_WAVE, "#10b981"),
                createMiniCard("Taux d'Occupation", String.format("%.1f%%", hotel.calculerTauxOccupation()),
                        FontAwesomeSolid.CHART_PIE, "#135bec"),
                createMiniCard("Total Réservations", String.valueOf(hotel.getReservations().size()),
                        FontAwesomeSolid.CALENDAR_ALT, "#f59e0b"));
        return row;
    }

    private VBox createMiniCard(String title, String value, FontAwesomeSolid icon, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(250);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        HBox top = new HBox();
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
        Region s = new Region();
        HBox.setHgrow(s, Priority.ALWAYS);
        FontIcon i = new FontIcon(icon);
        i.setIconColor(javafx.scene.paint.Color.web(color));
        top.getChildren().addAll(lblTitle, s, i);

        Label lblVal = new Label(value);
        lblVal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        card.getChildren().addAll(top, lblVal);
        return card;
    }

    private VBox createOccupancyChart() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        HBox.setHgrow(box, Priority.ALWAYS);

        Label lbl = new Label("Répartition des Chambres");
        lbl.setStyle("-fx-font-weight: bold;");

        int occupied = (int) hotel.getChambres().stream().filter(Chambre::isOccupe).count();
        int free = hotel.getChambres().size() - occupied;

        PieChart pie = new PieChart();
        pie.getData().addAll(
                new PieChart.Data("Occupées (" + occupied + ")", occupied),
                new PieChart.Data("Libres (" + free + ")", free));
        pie.setLegendVisible(true);
        pie.setPrefHeight(300);

        box.getChildren().addAll(lbl, pie);
        return box;
    }

    private VBox createRevenueChart() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        HBox.setHgrow(box, Priority.ALWAYS);

        Label lbl = new Label("Chiffre d'affaires par Type");
        lbl.setStyle("-fx-font-weight: bold;");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bar = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Group by type
        double simpleRev = hotel.getReservations().stream()
                .filter(r -> r.getChambre().getType().equals("Simple"))
                .mapToDouble(Reservation::calculerPrixTotal).sum();
        double doubleRev = hotel.getReservations().stream()
                .filter(r -> r.getChambre().getType().equals("Double"))
                .mapToDouble(Reservation::calculerPrixTotal).sum();
        double suiteRev = hotel.getReservations().stream()
                .filter(r -> r.getChambre().getType().equals("Suite"))
                .mapToDouble(Reservation::calculerPrixTotal).sum();

        series.getData().add(new XYChart.Data<>("Simple", simpleRev));
        series.getData().add(new XYChart.Data<>("Double", doubleRev));
        series.getData().add(new XYChart.Data<>("Suite", suiteRev));

        bar.getData().add(series);
        bar.setLegendVisible(false);
        bar.setPrefHeight(300);

        box.getChildren().addAll(lbl, bar);
        return box;
    }

    private VBox createPerformersSection() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        Label title = new Label("Top Performance");
        title.setStyle("-fx-font-weight: bold;");

        Chambre topRoom = hotel.getChambrePlusReservee();
        String roomInfo = (topRoom != null) ? "Chambre N°" + topRoom.getNumero() + " (" + topRoom.getType() + ")"
                : "Aucune donnée";

        HBox topRoomRow = new HBox(20);
        topRoomRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label lblRoom = new Label("Chambre la plus populaire :");
        Label valRoom = new Label(roomInfo);
        valRoom.setStyle("-fx-font-weight: bold; -fx-text-fill: #135bec;");
        topRoomRow.getChildren().addAll(lblRoom, valRoom);

        box.getChildren().addAll(title, topRoomRow);
        return box;
    }
}
