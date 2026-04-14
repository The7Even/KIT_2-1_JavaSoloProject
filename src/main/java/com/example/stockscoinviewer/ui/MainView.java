package com.example.stockscoinviewer.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView {

    public TextField input = new TextField();
    public Button button = new Button("조회");
    public Label resultLabel = new Label("현재 가격이 여기에 표시됩니다.");

    public Label top1 = new Label();
    public Label top2 = new Label();
    public Label top3 = new Label();
    public Label lastUpdate = new Label("마지막 갱신 : -");

    public VBox createView(Stage stage) {

        stage.initStyle(StageStyle.UNDECORATED);

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

        HBox searchBox = new HBox(5, input, button);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(
                titleBar,
                searchBox, resultLabel,
                new Separator(),
                new Label("거래량 Top 3"),
                top1, top2, top3,
                lastUpdate
        );

        return root;
    }
}
