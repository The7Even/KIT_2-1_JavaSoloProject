package com.example.stockscoinviewer.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.example.stockscoinviewer.model.CoinVolume;
import org.json.JSONObject;
import com.example.stockscoinviewer.model.CoinPrice;

public class BithumbService {

    public CoinPrice getCoinPrice(String coin) {
        try {
            String url = "https://api.bithumb.com/public/ticker/" + coin + "_KRW";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (!json.getString("status").equals("0000")) {
                return null;
            }

            JSONObject data = json.getJSONObject("data");

            double price = Double.parseDouble(data.getString("closing_price"));

            return new CoinPrice(coin, price);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CoinVolume> getTop3Coins() {
        try {
            String url = "https://api.bithumb.com/public/ticker/ALL_KRW";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            JSONObject data = json.getJSONObject("data");

            List<CoinVolume> list = new ArrayList<>();

            for (String key : data.keySet()) {
                if (key.equals("date")) continue;

                JSONObject coin = data.getJSONObject(key);

                double volume = Double.parseDouble(
                        coin.getString("acc_trade_value_24H")
                );

                double price = Double.parseDouble(
                        coin.getString("closing_price")
                );

                list.add(new CoinVolume(key, volume, price));
            }

            // 정렬
            list.sort((a, b) -> Double.compare(b.getVolume(), a.getVolume()));

            return list.subList(0, 3);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}