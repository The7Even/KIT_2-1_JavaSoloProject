package com.example.stockscoinviewer.controller;

import com.example.stockscoinviewer.controller.DomesticController;
import com.example.stockscoinviewer.model.DomesticSearch;
import com.example.stockscoinviewer.model.FavoriteItem;
import com.example.stockscoinviewer.model.GlobalSearch;
import com.example.stockscoinviewer.service.BithumbService;
import com.example.stockscoinviewer.service.DomesticService;
import com.example.stockscoinviewer.service.FavoriteService;
import com.example.stockscoinviewer.service.GlobalService;
import com.example.stockscoinviewer.ui.MainView;
import com.example.stockscoinviewer.ui.TopTabBar;
import com.example.stockscoinviewer.ui.TabType;
import com.example.stockscoinviewer.ui.typeView.DomesticView;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final DecimalFormat df = new DecimalFormat("#,###");

    private DomesticService domesticService;
    private BithumbService bithumbService;
    private GlobalService globalService;
    private FavoriteService favoriteService;

    public MainController(DomesticService domesticService, GlobalService globalService, BithumbService bithumbService, FavoriteService favoriteService) {
        this.domesticService = domesticService;
        this.bithumbService = bithumbService;
        this.globalService = globalService;
        this.favoriteService = favoriteService;
    }

    public void init(MainView view) {
        view.searchButton.setOnAction(e -> {
            handleSearch(view);
        });
        view.input.setOnAction(e -> {
            handleSearch(view);
        });

        setupFavoriteButton(view);

        view.getFavoriteView().UpdateFavorites(favoriteService.getFavorites());
    }

    private void handleSearch(MainView view) {
        String keyword = view.input.getText().trim();

        new Thread(() -> {
            TabType current = view.getTopTabBar().getCurrentTab();
            if (current == TabType.DOMESTIC) {
                List<DomesticSearch> list = DomesticService.searchStock(keyword);

                Platform.runLater(() -> {
                    view.getDomesticView().UpdateSearchResult(list);
                    refreshDomesticFavorite(view);
                });
            } else if (current == TabType.CRYPTO) {
                var result = BithumbService.getCoinPrice(keyword);

                Platform.runLater(() -> {
                    view.getCryptoView().setCurrentCoin(result);

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

    private void setupFavoriteButton(MainView view) {
        DomesticView domesticView = view.getDomesticView();
        domesticView.star1.setOnAction(e -> toggleDomesticFavorite(view, 0));
        domesticView.star2.setOnAction(e -> toggleDomesticFavorite(view, 1));
        domesticView.star3.setOnAction(e -> toggleDomesticFavorite(view, 2));
        domesticView.star4.setOnAction(e -> toggleDomesticFavorite(view, 3));
        domesticView.star5.setOnAction(e -> toggleDomesticFavorite(view, 4));
    }

    private void toggleDomesticFavorite(MainView view, int index) {
        DomesticView domesticView = view.getDomesticView();
        if (index >= domesticView.getCurrentResults().size()) { return; }

        DomesticSearch stock = domesticView.getCurrentResults().get(index);

        FavoriteItem item = new FavoriteItem(TabType.DOMESTIC, stock.getCode(), stock.getName());

        boolean favorite = favoriteService.toggleFavorite(item);

        Button button = switch (index)
        {
            case 0 -> domesticView.star1;
            case 1 -> domesticView.star2;
            case 2 -> domesticView.star3;
            case 3 -> domesticView.star4;
            default -> domesticView.star5;
        };

        button.setText(favorite ? "★" : "☆");

        view.getFavoriteView().UpdateFavorites(favoriteService.getFavorites());
        refreshDomesticFavorite(view);
    }

    private void refreshDomesticFavorite(MainView view) {
        DomesticView domesticView = view.getDomesticView();

        List<DomesticSearch> list = domesticView.getCurrentResults();

        Button[] stars = {domesticView.star1, domesticView.star2,  domesticView.star3, domesticView.star4, domesticView.star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < list.size()) {
                DomesticSearch stock = list.get(i);

                boolean favorite = favoriteService.isFavorite(TabType.DOMESTIC, stock.getCode());

                stars[i].setText(favorite ?  "★" : "☆");
            }
            else {stars[i].setText("☆");}
        }
    }
}
