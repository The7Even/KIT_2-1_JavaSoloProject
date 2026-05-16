package com.example.stockscoinviewer.service;

import com.example.stockscoinviewer.model.DomesticSearch;
import com.example.stockscoinviewer.model.GlobalPopular;
import com.example.stockscoinviewer.model.GlobalSearch;
import com.example.stockscoinviewer.ui.MainView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GlobalService {
    private static final String BASE_URL = "https://finance.naver.com/world/";

    public static List<GlobalSearch> SearchStock(String keyword) {
        List<GlobalSearch> result = new ArrayList<>();

        try {
            String encoded = URLEncoder.encode(keyword, "EUC-KR");
            String url = "https://finance.naver.com/search/search.naver?query=" + encoded;

            Document doc = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0").get();

            Elements items = doc.select("table.tbl_search tbody tr");

            for (Element item : items) {
                Elements tds = item.select("td");
                Element link = tds.get(0).selectFirst("a");

                if (link != null && tds.size() == 6) {
                    String name = link.text();
                    String href = link.attr("href");

                    String code = "";

                    if (href.contains("/stock/")) {

                        String[] parts = href.split("/stock/");

                        if (parts.length > 1) {
                            code = parts[1].split("/")[0];
                        }
                    }

                    String price = tds.get(1).text();
                    String diff = tds.get(3).text();

                    result.add(new GlobalSearch(name, code,price, diff));
                }

                if (result.size() >= 5) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<GlobalPopular> getTop3() {
        List<GlobalPopular> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla/5.0").timeout(5000).get();

            Elements items = doc.select("ul.data_lst li dl");

            for (Element item : items) {
                Element nameEl = item.selectFirst("dt a .blind");

                if (nameEl == null) { continue; }
                String name = nameEl.text();

                if (
                        !name.contains("다우 산업") &&
                                !name.contains("나스닥 종합") &&
                                !name.contains("S&P500")
                ) {
                    continue;
                }

                Element priceEl = item.selectFirst("dd.point_status strong");

                if(priceEl == null)
                    continue;

                String price = priceEl.text();

                result.add(new GlobalPopular(name, price));

                if(result.size() >= 3)
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }
}
