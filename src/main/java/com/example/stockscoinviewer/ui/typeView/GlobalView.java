package com.example.stockscoinviewer.ui.typeView;

import com.example.stockscoinviewer.model.GlobalSearch;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GlobalView extends VBox {

    private VBox favoriteBox = new VBox(5);
    private VBox Top3Box = new VBox(5);

    private Label topTitle = new Label("미국 증시 주요 지수");

    public GlobalView() {
        initialize();
    }

    private List<GlobalSearch> currentResults = new ArrayList<>();

    public void SetLabel(Label l) {
        l.setMaxWidth(180);
        l.setTextOverrun(OverrunStyle.ELLIPSIS);
    }

    private void initialize() {
        this.setSpacing(15);
        this.setPadding(new Insets(15));

        favoriteBox.getChildren().addAll(fav1, fav2, fav3, fav4, fav5);

        Top3Box.getChildren().addAll(topTitle, top1, top2, top3);

        this.getChildren().addAll(favoriteBox, new Separator(), Top3Box);
    }

    public void WriteTop3(String t1, String t2, String t3) {
        top1.setText(t1);
        top2.setText(t2);
        top3.setText(t3);
    }

    public void UpdateSearchResult(List<GlobalSearch> list) {
        currentResults = list;
        String output;
        String[] results = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            output = list.get(i).getName() + " : " + list.get(i).getPrice() + " USD (" + list.get(i).getDiff() + ")";
            results[i] = output;
        }
        Label[] labels = {fav1, fav2, fav3, fav4, fav5};

        for (int i = 0; i < labels.length; i++) {
            if (i < list.size()) {
                labels[i].setText(results[i]);
            } else {
                labels[i].setText("");
            }
        }
        if (list.isEmpty()) {
            labels[0].setText("검색 결과가 없습니다.");
        }
    }

    public Label top1 = new Label();
    public Label top2 = new Label();
    public Label top3 = new Label();
    public Label fav1 = new Label("상단 검색 탭을 이용해 검색해주세요.");
    public Label fav2 = new Label("차후에 이 탭은 즐겨찾기 한 주식이 표시될 예정입니다.");
    public Label fav3 = new Label();
    public Label fav4 = new Label();
    public Label fav5 = new Label();

    public List<GlobalSearch> getCurrentResults() {
        return currentResults;
    }

}
