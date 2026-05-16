package com.example.stockscoinviewer.app;

import com.example.stockscoinviewer.controller.CryptoController;
import com.example.stockscoinviewer.controller.DomesticController;
import com.example.stockscoinviewer.controller.GlobalController;
import com.example.stockscoinviewer.controller.MainController;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.service.DomesticService;
import com.example.stockscoinviewer.service.GlobalService;
import com.example.stockscoinviewer.ui.MainView;
import com.example.stockscoinviewer.ui.TopTabBar;
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
        DomesticService domeService = new DomesticService();
        GlobalService globalService = new GlobalService();
        TopTabBar tabBar = new TopTabBar();
        MainView view = new MainView(stage); // ui.MainView (UI 세팅)
        // view.setTopTabBar(tabBar);
        CryptoController crycon = new CryptoController(service);
        DomesticController domecon = new DomesticController(domeService);
        GlobalController globalcon = new GlobalController(globalService);
        MainController controller = new MainController(domeService, globalService, service);

        crycon.init(view);
        domecon.init(view);
        controller.init(view);
        globalcon .init(view);

        Scene scene = new Scene(view, 400, 400);
        scene.getStylesheets().add(getClass()
                .getResource("/com/example/stockscoinviewer/ui/styles.css")
                .toExternalForm());
        scene.getStylesheets().add(
                getClass().getResource("/com/example/stockscoinviewer/ui/tabui.css").toExternalForm()
        );

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}