package com.example.stockscoinviewer.ui.typeView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CryptoView extends VBox {

    private VBox favoriteBox = new VBox(5);
    private VBox Top3Box = new VBox(5);

    private Label topTitle = new Label("실시간 거래량 Top 3");

    public CryptoView() {
        initialize();
    }

    private void initialize() {
        this.setSpacing(15);
        this.setPadding(new Insets(15));

        favoriteBox.getChildren().add(resultLabel);

        Top3Box.getChildren().addAll(topTitle, top1, top2, top3);

        this.getChildren().addAll(favoriteBox, new Separator(), Top3Box);
    }

    public void WriteTop3(String t1, String t2, String t3) {
        top1.setText(t1);
        top2.setText(t2);
        top3.setText(t3);
    }

    public Label top1 = new Label();
    public Label top2 = new Label();
    public Label top3 = new Label();
    public Label resultLabel = new Label("현재 가격이 여기에 표시됩니다.");
}
