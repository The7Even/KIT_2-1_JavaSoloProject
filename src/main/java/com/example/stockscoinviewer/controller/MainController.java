package com.example.stockscoinviewer.controller;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.ui.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainController {

    private final BithumbService service;
    private final DecimalFormat df = new DecimalFormat("#,###");

    public MainController(BithumbService service) {
        this.service = service;
    }

    public void init(MainView view) {

        // 버튼 이벤트
        view.button.setOnAction(e -> {
            String coin = view.input.getText().toUpperCase();

            new Thread(() -> {
                var result = service.getCoinPrice(coin);

                Platform.runLater(() -> {
                    if (result != null) {
                        view.resultLabel.setText(
                                result.getName() + ": " + df.format(result.getPrice()) + " KRW"
                        );
                    } else {
                        view.resultLabel.setText("조회 실패");
                    }
                });
            }).start();
        });

        // 초기 Top3
        updateTop3(view);

        // 타이머
        int sec = LocalTime.now().getSecond();
        int delay = 5 - (sec % 5);

        PauseTransition init = new PauseTransition(Duration.seconds(delay));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> updateTop3(view))
        );

        init.setOnFinished(e -> timeline.play());
        init.play();

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTop3(MainView view) {
        new Thread(() -> {
            var list = service.getTop3Coins();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Platform.runLater(() -> {
                if (list != null && list.size() >= 3) {
                    view.top1.setText("1. " + list.get(0).getName() + " - " + df.format(list.get(0).getPrice()) + " KRW");
                    view.top2.setText("2. " + list.get(1).getName()  + " - " + df.format(list.get(1).getPrice()) + " KRW");
                    view.top3.setText("3. " + list.get(2).getName()   + " - " + df.format(list.get(2).getPrice()) + " KRW");
                }
                view.lastUpdate.setText("마지막 갱신 시간 : " + time);
            });
        }).start();
    }
}
