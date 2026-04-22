package com.example.stockscoinviewer.service;

import com.example.stockscoinviewer.model.DomesticPopular;
import com.example.stockscoinviewer.model.DomesticSearch;
import com.example.stockscoinviewer.ui.MainView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;

public class DomesticService {

    private static final String BASE_URL = "https://finance.naver.com";

    public List<DomesticPopular> getTop3() {
        List<DomesticPopular> result = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla/5.0").timeout(5000).get();

            Elements rows = doc.select(".aside_area.aside_popular table tr");

            for(Element row : rows) {

                if (row.selectFirst("th a") == null) { continue; }

                Element nameEl = row.selectFirst("th a");

                String name = nameEl.text();

                Elements tds = row.select("td");
                if (tds.size() < 2) { continue; }

                String price = tds.get(0).text();

                result.add(new DomesticPopular(name, price));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return result;
    }

    public static List<DomesticSearch> searchStock(String keyword) {
        List<DomesticSearch> result = new ArrayList<>();

        try {
            String encoded = URLEncoder.encode(keyword, "EUC-KR");
            String url = "https://finance.naver.com/search/search.naver?query=" +  encoded;

            Document doc = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0").get();

            Elements items = doc.select("table.tbl_search tbody tr");

            if (items.size() > 0) {
                for(Element item : items) {
                    Element link = item.selectFirst(".tit a");
                    Elements tds = item.select("td");

                    if (link != null && tds.size() >= 8) {
                        String name = link.text();
                        String price = link.attr("href").split("code=")[1];

                        String code = tds.get(1).text();
                        String diff = tds.get(3).text();

                        result.add(new DomesticSearch(name, price, code, diff));
                    }

                    if (result.size() >= 5) break;
                }
            } else {
                Element script = doc.selectFirst("script");

                if (script != null && script.html().contains("location.href")) {
                    String html = script.html();

                    String redirectUrl = html.split("location.href")[1].split("'")[1];

                    String fullUrl = "https://finance.naver.com" + redirectUrl;

                    Document newDoc = Jsoup.connect(fullUrl).userAgent("Mozilla/5.0").get();

                    DomesticSearch single = parseSingleStock(newDoc);
                    if (single != null) {result.add(single);}
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static DomesticSearch parseSingleStock(Document doc) {
        String name = doc.selectFirst(".wrap_company h2 a").text();
        String price = doc.selectFirst(".no_today .blind").text();

        Element rate = doc.select(".no_exday em").get(1);
        String sign = "";
        Element signEl = rate.selectFirst(".ico");
        if (signEl != null) {
            String cls = signEl.className();

            if (cls.contains("minus") || cls.contains("down")) { sign = "-"; }
            else if (cls.contains("up") || cls.contains("plus"))  { sign = "+"; }
        }
        String value = rate.selectFirst(".blind").text();

        String diff = sign + value + "%";

        return new DomesticSearch(name, "",  price, diff);
    }

    public String getPriceByCode(String code) {
        try {
            String url = "https://finance.naver.com/item/main.naver?code=" +  code;

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

            return doc.selectFirst(".no_today .blind").text();
        } catch (Exception e) {
            return "Failed to Fetch";
        }
    }
}
