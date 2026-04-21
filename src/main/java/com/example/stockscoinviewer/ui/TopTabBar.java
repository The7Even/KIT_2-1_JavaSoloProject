package com.example.stockscoinviewer.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import java.util.Map;

public class TopTabBar extends HBox {
    private TopTabBar topTabBar;
    public TopTabBar getTopTabBar() { return topTabBar; }
    private TabType currentTab;
    public TabType getCurrentTab() { return currentTab; }
    private ToggleGroup toggleGroup = new ToggleGroup();
    private Map<TabType, ToggleButton> tabButtons = new HashMap<>();

    private TabChange listener;

    public TopTabBar() { initialize(); }

    private void initialize() {
        this.setSpacing(10);
        this.getStyleClass().add("TabStyle");

        createTab("국내", TabType.DOMESTIC);
        createTab("해외", TabType.GLOBAL);
        createTab("가상", TabType.CRYPTO);
        createTab("검색", TabType.SEARCH);

        tabButtons.get(TabType.DOMESTIC).setSelected(true);
        currentTab = TabType.DOMESTIC;
    }

    private void createTab(String name, TabType type) {
        ToggleButton btn = new ToggleButton(name);
        btn.setToggleGroup(toggleGroup);

        btn.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
           if (btn.isSelected()) {
               e.consume();
           }
        });

        btn.setOnAction(e -> {
            currentTab = type;

            if (listener != null) {
                listener.onTabChanged(type);
            }
        });

        tabButtons.put(type, btn);
        this.getChildren().add(btn);
    }

    public void setTabChangeListener(TabChange listener) {
        this.listener = listener;
    }
}
