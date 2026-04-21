package com.example.stockscoinviewer.controller;

import com.example.stockscoinviewer.service.DomesticService;
import com.example.stockscoinviewer.ui.MainView;
import com.example.stockscoinviewer.ui.typeView.DomesticView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DomesticController {
    private final DomesticService service;

    public DomesticController(DomesticService service) {
        this.service = service;
    }

    public void init(MainView view) {
        DomesticView domestic = view.getDomesticView();

        updateTop3(view);
    }

    private void updateTop3(MainView view) {
        DomesticView domestic = view.getDomesticView();

        new Thread (() -> {
            var list = service.getTop3();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Platform.runLater(() -> {
                if (list != null && list.size() >= 3) {
                    String top1 = "1. " + list.get(0).getName() + " - " + list.get(0).getPrice() + " KRW";
                    String top2 = "2. " + list.get(1).getName() + " - " + list.get(1).getPrice() + " KRW";
                    String top3 = "3. " + list.get(2).getName() + " - " + list.get(2).getPrice() + " KRW";

                    domestic.WriteTop3(top1, top2, top3);
                }

                view.lastUpdate.setText("마지막 갱신 시간 : " + time);
            });
        }).start();
    }
}
