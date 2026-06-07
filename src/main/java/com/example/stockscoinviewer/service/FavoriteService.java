package com.example.stockscoinviewer.service;

import com.example.stockscoinviewer.model.FavoriteItem;
import com.example.stockscoinviewer.ui.TabType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteService {
    private static final String FILE_NAME = "favorites.csv";

    private final List<FavoriteItem> favorites =
            new ArrayList<>();

    public List<FavoriteItem> getFavorites() {
        return favorites;
    }

    public FavoriteService() {
        loadFavorites();
    }

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
        if(isFavorite(item.getMarket(), item.getCode())) {
            return;
        }
        favorites.add(item);
        saveFavorite();
    }

    public void removeFavorite(
            TabType market,
            String code) {

        favorites.removeIf(item ->
                item.getMarket() == market &&
                        item.getCode().equals(code));

        saveFavorite();
    }

    public void saveFavorite() {
        try(PrintWriter writer = new PrintWriter(FILE_NAME))
        {
            writer.println("market,code,name");
            for (FavoriteItem item : favorites) {
                writer.println(item.getMarket() +"," + item.getCode() + "," + item.getName());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadFavorites()
    {
        favorites.clear();

        File file = new File(FILE_NAME);

        if(!file.exists()) return;

        try(BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)))
        {
            String line;

            reader.readLine();

            while((line = reader.readLine()) != null)
            {
                String[] data = line.split(",");

                favorites.add(
                        new FavoriteItem(
                                TabType.valueOf(data[0]),
                                data[1],
                                data[2]
                        )
                );
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
