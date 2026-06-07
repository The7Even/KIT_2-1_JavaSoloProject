package com.example.stockscoinviewer.ui.typeView;

import com.example.stockscoinviewer.model.FavoriteItem;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.List;

public class FavoriteView extends VBox {
    private VBox favBox = new VBox(5);

    public FavoriteView(){
        initalize();
    }

    private void initalize(){
        this.setSpacing(15);
        this.setPadding(new Insets(15));

        favBox.getChildren().add(favoriteBox);

        this.getChildren().addAll(favBox);
    }

    public void UpdateFavorites(List<FavoriteItem> favorites)
    {

        favoriteBox.getChildren().clear();

        if(favorites.isEmpty()) {
            favoriteBox.getChildren().add(new Label("아직 즐겨찾기한 종목이 없습니다."));
            return;
        }

        for (FavoriteItem item : favorites) {
            Label label = new Label(item.getName() + " (" + item.getCode() + ")");

            favoriteBox.getChildren().add(label);
        }
    }

    private VBox favoriteBox = new VBox(5);
}
