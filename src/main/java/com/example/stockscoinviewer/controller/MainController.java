package com.example.stockscoinviewer.controller;

import com.example.stockscoinviewer.controller.DomesticController;
import com.example.stockscoinviewer.model.DomesticSearch;
import com.example.stockscoinviewer.model.GlobalSearch;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.service.DomesticService;
import com.example.stockscoinviewer.service.GlobalService;
import com.example.stockscoinviewer.ui.MainView;
import com.example.stockscoinviewer.ui.TopTabBar;
import com.example.stockscoinviewer.ui.TabType;
import com.example.stockscoinviewer.ui.typeView.DomesticView;
import javafx.application.Platform;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final DecimalFormat df = new DecimalFormat("#,###");

    private DomesticService domesticService;
    private BithumbService bithumbService;
    private GlobalService globalService;

    public MainController(DomesticService domesticService, GlobalService globalService, BithumbService bithumbService) {
        this.domesticService = domesticService;
        this.bithumbService = bithumbService;
        this.globalService = globalService;
    }

    public void init(MainView view) {
        view.searchButton.setOnAction(e -> {
            handleSearch(view);
        });
        view.input.setOnAction(e -> {
            handleSearch(view);
        });
    }

    private void handleSearch(MainView view) {
        String keyword = view.input.getText().trim();

        new Thread(() -> {
            TabType current = view.getTopTabBar().getCurrentTab();
            if (current == TabType.DOMESTIC) {
                List<DomesticSearch> list = DomesticService.searchStock(keyword);

                Platform.runLater(() -> {
                    view.getDomesticView().UpdateSearchResult(list);
                });
            } else if (current == TabType.CRYPTO) {
                var result = BithumbService.getCoinPrice(keyword);

                Platform.runLater(() -> {
                    view.getCryptoView().resultLabel.setText(
                            result != null ? result.getName() + ": " + df.format(result.getPrice()) + " KRW" : "조회 실패"
                    );
                });
            } else if (current == TabType.GLOBAL) {
                List<GlobalSearch> list = GlobalService.SearchStock(keyword);

                Platform.runLater(() -> {
                    view.getGlobalView().UpdateSearchResult(list);
                });
            }
        }).start();
    }
}
