package com.example.progettoguimortadella;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.*;



import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class LaboratorioController {


    public Button avviaButton;
    public Button clearLogButton;
    @FXML public FlowPane mag1FlowPane;
    @FXML public FlowPane mag2FlowPane;
    @FXML public Label mag2CountLabel;
    @FXML public TextArea logMagazzini;
    @FXML public VBox puntiVenditaContainer;
    @FXML
    private ListView<Laboratorio> laboratorioListView;
    @FXML private HashMap<PuntoVendita, FlowPane> flowPaneMap=new HashMap<>();
    Gestione g;
    @FXML TextArea logArea;
    @FXML private Label prodotteLabel;
    @FXML private Label approvateLabel;
    @FXML private Label scartateLabel;
    @FXML private Label distribuiteLabel;
    @FXML private ProgressBar globalProgressBar;
    @FXML private Image spriteMortadella;
    @FXML private Label mag1CountLabel;
    private GestioneMagazzini  gestioneMagazzini;


    private AtomicInteger mag1VisualCount = new AtomicInteger(0);
    private AtomicInteger mag2VisualCount = new AtomicInteger(0);

    private UICallback uiCallback= new UICallback() {

        public void log(String msg) {
            Platform.runLater(() -> logArea.appendText(msg + "\n"));
        }

        @Override
        public void logMag(String msg) {
            Platform.runLater(() -> logMagazzini.appendText(msg + "\n"));
        }

        public void onProdotta(int total) {
            Platform.runLater(() -> {
                prodotteLabel.setText(total + " / 50");
                globalProgressBar.setProgress(total / 50.0);

                if (total % 50 == 0) {
                    log("fine produzione");
                }
            });

        }


        public void onPut1() {
            int count = mag1VisualCount.incrementAndGet();
            Platform.runLater(() -> {
                while (mag1FlowPane.getChildren().size() < count) {
                    ImageView iv = new ImageView(spriteMortadella);
                    iv.setFitWidth(40);
                    iv.setFitHeight(40);
                    mag1FlowPane.getChildren().add(iv);
                }
                mag1CountLabel.setText(count + " / 10");
            });
        }

        public void onTaken1() {
            int count = mag1VisualCount.decrementAndGet();
            Platform.runLater(() -> {
                while (mag1FlowPane.getChildren().size() > count) {
                    mag1FlowPane.getChildren().remove(0);
                }
                mag1CountLabel.setText(count + " / 10");
            });
        }

        public void onTaken2() {
            int count = mag2VisualCount.decrementAndGet();
            Platform.runLater(() -> {
                while (mag2FlowPane.getChildren().size() > count) {
                    mag2FlowPane.getChildren().remove(0);
                }
                mag2CountLabel.setText(count + " / 15");
            });
        }

        public void onPut2() {
            int count = mag2VisualCount.incrementAndGet();
            Platform.runLater(() -> {
                while (mag2FlowPane.getChildren().size() < count) {
                    ImageView iv = new ImageView(spriteMortadella);
                    iv.setFitWidth(40);
                    iv.setFitHeight(40);
                    mag2FlowPane.getChildren().add(iv);
                }
                mag2CountLabel.setText(count + " / 15");
            });
        }

        public void onApprovata(int total) {
            Platform.runLater(() -> approvateLabel.setText(String.valueOf(total)));
        }
        public void onScartata(int total) {
            Platform.runLater(() -> scartateLabel.setText(String.valueOf(total)));
        }
        public void onDistribuita(int total, PuntoVendita puntoVendita) {
            Platform.runLater(() -> {
                distribuiteLabel.setText(String.valueOf(total));
                FlowPane fp = flowPaneMap.get(puntoVendita);
                if (fp != null) {
                    ImageView iv = new ImageView(spriteMortadella);
                    iv.setFitWidth(40);
                    iv.setFitHeight(40);
                    fp.getChildren().add(iv);
                }
            });
        }
    };



    @FXML public void initialize(){
        try {
            gestioneMagazzini=new GestioneMagazzini(uiCallback);
            g = new Gestione(gestioneMagazzini, uiCallback );
            ObservableList<Laboratorio> items = FXCollections.observableArrayList(g.getLaboratori());
            laboratorioListView.setItems(items);
            laboratorioListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            spriteMortadella = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/progettoguimortadella/mortadella.png")));
            for (PuntoVendita p: g.getPuntiVendita()){
                VBox card = new VBox();
                card.setStyle("-fx-background-color: #f0f0f0;-fx-border-color: black; -fx-border-radius: 5;-fx-padding: 10;");
                card.setPrefHeight(150);
                Label puntoVenditaLabel = new Label();
                puntoVenditaLabel.setText(p.toString());

                FlowPane flowPane = new FlowPane();

                flowPane.setHgap(10);
                flowPane.setVgap(10);
                flowPane.setPrefWrapLength(300);
                flowPaneMap.put(p, flowPane);
                card.getChildren().addAll(puntoVenditaLabel, flowPane);
                puntiVenditaContainer.getChildren().add(card);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }




    @FXML public void handleAvvia() {

        ObservableList<Laboratorio> selectedItems = laboratorioListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) return;
        g.start();
        for (Laboratorio l : selectedItems){
            l.start();
        }
    }

    @FXML public void handleSelectAll() {
        laboratorioListView.getSelectionModel().selectAll();
    }

    @FXML public void handleDeselectAll(ActionEvent actionEvent) {
        laboratorioListView.getSelectionModel().clearSelection();
    }

    @FXML public void handleClearLog(ActionEvent actionEvent) {
        logArea.setText("");
    }
}
