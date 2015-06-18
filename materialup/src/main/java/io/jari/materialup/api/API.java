package io.jari.materialup.api;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jari on 07/06/15.
 */
public class API {

    public static Item[] getListing(String path, Context context, Integer page) throws IOException {
        Document document = Jsoup.connect("http://www.materialup.com/" + path).timeout(30000).get();
        ArrayList<Item> items = new ArrayList<Item>();

        Elements elements = document.select(".post-list-items .post-list-item");
        for (Element element : elements) {
            Item item = new Item();
            item.id = element.attr("id");
            item.title = element.select("h2").first().text();
            if (item.title.equals("")) continue;
            item.imageUrl = element.select("img.preview").first().attr("data-cfsrc");
            if (item.imageUrl == null || item.imageUrl == "") continue;
            Element score = element.select("div.count").first();
            if (score != null)
                item.score = score.text();
            else item.score = "0";

            item.views = element.select(".post__stats a:eq(0)").first().ownText();
            item.comments = element.select(".post__stats a:eq(1)").first().ownText();

            Element label = element.select(".post__label").first();
            if (label != null)
                item.label = label.attr("alt").split(" ")[0];
            else item.label = "";

            Element category = element.select("a[href^=/posts/c]").first();
            item.categoryLink = category.attr("href");
            item.categoryName = category.text();

            Element maker = category.nextElementSibling();
            if (maker != null) {
                item.makerName = maker.text();
                item.makerUrl = maker.attr("href");
            } else {
                //no url, try to get name eitherway
                String[] parts = category.parent().text().split(" by ");
                if (parts.length > 1)
                    item.makerName = parts[1];
            }

            if (item.makerName == null) continue;

            Element avatar = element.select(".avatar").first();
            if (avatar != null) {
                item.makerAvatar = avatar.attr("data-cfsrc");
            }

            items.add(item);
        }


        Item[] returnList = new Item[items.size()];
        items.toArray(returnList);
        return returnList;
    }

    public static ItemDetails getItemDetails(String id) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.materialup.com/posts/" + id)
                .addHeader("Accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject object = new JSONObject(response.body().string());
        String html = object.getString("content");
        Document document = Jsoup.parse(html);
        ItemDetails itemDetails = new ItemDetails();

        itemDetails.imageUrl = document.select("img.preview").first().attr("src");

        return itemDetails;
    }
}
