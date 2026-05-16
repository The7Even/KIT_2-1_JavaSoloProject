package com.example.stockscoinviewer.controller;

import com.example.stockscoinviewer.service.GlobalService;
import com.example.stockscoinviewer.ui.MainView;
import com.example.stockscoinviewer.ui.typeView.GlobalView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GlobalController {
    private final GlobalService service;

    public GlobalController(GlobalService service) {
        this.service = service;
    }

    public void init(MainView view) {
        GlobalView global = view.getGlobalView();

        updateTop3(view);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(5),
                        e -> updateTop3(view)
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateTop3(MainView view) {
        GlobalView global = view.getGlobalView();

        new Thread (() -> {

            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Platform.runLater(() -> {
                var list = service.getTop3();
                if (list != null && list.size() >= 3) {
                    String top1 = list.get(0).getName() + " - " + list.get(0).getPrice() + " USD";
                    String top2 = list.get(1).getName() + " - " + list.get(1).getPrice() + " USD";
                    String top3 = list.get(2).getName() + " - " + list.get(2).getPrice() + " USD";

                    global.WriteTop3(top1, top2, top3);
                }

                view.lastUpdate.setText("마지막 갱신 시간 : " + time);
            });
        }).start();
    }
}
