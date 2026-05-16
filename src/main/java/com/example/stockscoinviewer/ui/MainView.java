package com.example.stockscoinviewer.ui;

import com.example.stockscoinviewer.controller.MainController;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.service.DomesticService;
import com.example.stockscoinviewer.ui.typeView.CryptoView;
import com.example.stockscoinviewer.ui.typeView.DomesticView;
import com.example.stockscoinviewer.ui.typeView.GlobalView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

public class MainView extends BorderPane {

    private StackPane contentArea;

    public MainView(Stage stage) {
        initialize(stage);
    }

    private CryptoView cryptoView;
    private DomesticView domesticView;
    private GlobalView globalView;
    public CryptoView getCryptoView() {
        return cryptoView;
    }
    public DomesticView getDomesticView() { return domesticView; }
    public GlobalView getGlobalView() { return globalView; }

    private TopTabBar topTabBar;
    public TopTabBar getTopTabBar() { return topTabBar; }

    private void initialize(Stage stage) {

        topTabBar = new TopTabBar();

        domesticView = new DomesticView();
        globalView = new GlobalView();
        cryptoView = new CryptoView();
        // searchView = new SearchView();

        contentArea = new StackPane();

        // 각 Ui 탭 개발 완료하면 삭제
        Pane searchView = new Pane();

        contentArea.getChildren().addAll(domesticView, globalView, cryptoView, searchView);

        showView(domesticView);

        topTabBar.setTabChangeListener(type -> {
            switch (type) {
                case DOMESTIC:
                    showView(domesticView);
                    break;
                case GLOBAL:
                    showView(globalView);
                    break;
                case CRYPTO:
                    showView(cryptoView);
                    break;
                case SEARCH: // 차후 SEARCH에서 FAVORITE로 변경 가능.
                    showView(searchView);
                    break;
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);

        HBox titleBar = createTitleBar(stage);

        HBox searchBox = new HBox(5, input, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        VBox Display = new VBox(titleBar, searchBox, topTabBar);
        VBox TabBox = new VBox(contentArea, lastUpdate);
        this.setTop(Display);
        this.setCenter(TabBox);
    }

    private void showView(Pane tr) {
        for (Node node : contentArea.getChildren()) {
            node.setVisible(false);
        }
        tr.setVisible(true);
    }

    public TextField input = new TextField();
    public Button searchButton = new Button("조회");
    public Label lastUpdate = new Label("마지막 갱신 : -");

    private HBox createTitleBar(Stage stage) {
        Button closeBtn = new Button("×");
        Button minBtn = new Button("_");
        Label titleLabel = new Label("Stocks & Coins Price Tracker");

        closeBtn.getStyleClass().add("close-btn"); // css 기반 디자인 불러오기
        minBtn.getStyleClass().add("min-btn");

        HBox titleBar = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleBar.getChildren().addAll(titleLabel, spacer, minBtn, closeBtn);

        titleBar.getStyleClass().add("title-bar");
        titleBar.setPadding(new Insets(2, 8, 2, 8));
        titleBar.setAlignment(Pos.CENTER_LEFT);

        // 드래그 기능
        final double[] xOffset = {0};
        final double[] yOffset = {0};

        titleBar.setOnMousePressed(e -> {
            xOffset[0] = e.getSceneX();
            yOffset[0] = e.getSceneY();
        });

        titleBar.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - xOffset[0]);
            stage.setY(e.getScreenY() - yOffset[0]);
        });

        closeBtn.setOnAction(e -> stage.close());
        minBtn.setOnAction(e -> stage.setIconified(true));

        return  titleBar;
    }
}
