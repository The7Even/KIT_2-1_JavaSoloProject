package com.example.stockscoinviewer.app;

import com.example.stockscoinviewer.controller.MainController;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        BithumbService service = new BithumbService(); // 빗썸 API 적용
        MainView view = new MainView(); // ui.MainView (UI 세팅)
        MainController controller = new MainController(service);

        VBox root = view.createView(stage);
        controller.init(view);

        Scene scene = new Scene(root, 300, 230);
        scene.getStylesheets().add(getClass()
                .getResource("/com/example/stockscoinviewer/ui/styles.css")
                .toExternalForm());

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}