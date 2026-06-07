package com.example.stockscoinviewer.ui.typeView;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class FavoriteView extends VBox {
    private VBox favBox = new VBox(5);

    public FavoriteView(){
        initalize();
    }

    private void initalize(){
        this.setSpacing(15);
        this.setPadding(new Insets(15));

        favBox.getChildren().addAll(fav1,fav2,fav3,fav4,fav5);

        this.getChildren().addAll(favBox);
    }

    public void UpdateFavorites(String[] favorites)
    {
        Label[] labels = {fav1, fav2, fav3, fav4, fav5};

        for (int i = 0; i < labels.length; i++) {
            if (i < favorites.length) {
                labels[i].setText(favorites[i]);
            } else {
                labels[i].setText("");
            }
        }
    }

    public Label fav1 = new Label("즐겨찾기에 종목을 추가해 보세요.");
    public Label fav2 = new Label();
    public Label fav3 = new Label();
    public Label fav4 = new Label();
    public Label fav5 = new Label();
}
