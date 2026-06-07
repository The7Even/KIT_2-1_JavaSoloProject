package com.example.stockscoinviewer.service;

import com.example.stockscoinviewer.model.FavoriteItem;
import com.example.stockscoinviewer.ui.TabType;

import java.util.ArrayList;
import java.util.List;

public class FavoriteService {

    private final List<FavoriteItem> favorites =
            new ArrayList<>();

    public boolean isFavorite(
            TabType market,
            String code) {

        return favorites.stream()
                .anyMatch(item ->
                        item.getMarket() == market &&
                                item.getCode().equals(code));
    }

    public boolean toggleFavorite (FavoriteItem item) {
        if (isFavorite(item.getMarket(), item.getCode())) {
            removeFavorite(item.getMarket(), item.getCode());

            return false;
        }
        addFavorite(item);
        return true;
    }

    public void addFavorite(FavoriteItem item) {
        favorites.add(item);
    }

    public void removeFavorite(
            TabType market,
            String code) {

        favorites.removeIf(item ->
                item.getMarket() == market &&
                        item.getCode().equals(code));
    }
}
