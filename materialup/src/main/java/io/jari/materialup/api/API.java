package io.jari.materialup.api;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import io.jari.materialup.models.Comment;
import io.jari.materialup.models.Item;
import io.jari.materialup.models.ItemDetails;

/**
 * Created by jari on 07/06/15.
 *
 * This class are Deprecated by @sicareli. You should use
 * {@link io.jari.materialup.connection.UpRequests}
 * now.
 */
@Deprecated
public class API {

    public static Item[] getListing(String path, Context context, Integer page) throws IOException {
        Document document = Jsoup.connect("http://www.materialup.com/" + path).timeout(30000).get();
        ArrayList<Item> items = new ArrayList<Item>();

        Elements elements = document.select(".post-list-items .post-list-item");
        for (Element element : elements) {
            Item item = new Item();
            item.setId(element.attr("id"));
            item.setTitle(element.select("h2").first().text());
            if (item.getTitle().equals("")) continue;
            item.setImageUrl(element.select("img.preview").first().attr("data-cfsrc"));
            if (item.getImageUrl() == null || item.getImageUrl() == "") continue;
            Element score = element.select("div.count").first();
            if (score != null)
                item.setScore(score.text());
            else item.setScore("0");

            item.setViews(element.select(".post__stats a:eq(0)").first().ownText());
            item.setComments(element.select(".post__stats a:eq(1)").first().ownText());

            Element label = element.select(".post__label").first();
            if (label != null)
                item.setLabel(label.attr("alt").split(" ")[0]);
            else item.setLabel("");

            Element category = element.select("a[href^=/posts/c]").first();
            item.setCategoryLink(category.attr("href"));
            item.setCategoryName(category.text());

            Element maker = category.nextElementSibling();
            if (maker != null) {
                item.setMakerName(maker.text());
                item.setMakerUrl(maker.attr("href"));
            } else {
                //no url, try to get name eitherway
                String[] parts = category.parent().text().split(" by ");
                if (parts.length > 1)
                    item.setMakerName(parts[1]);
            }

            if (item.getMakerName() == null) continue;

            Element avatar = element.select(".avatar").first();
            if (avatar != null) {
                item.setMakerAvatar(avatar.attr("data-cfsrc"));
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

        Element img = document.select("img.preview").first();

        if (img != null)
            itemDetails.imageUrl = img.attr("src");

        return itemDetails;
    }

    public static Comment[] getComments(String id) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.materialup.com/posts/" + id + "/comments")
                .build();

        Response response = client.newCall(request).execute();
        JSONArray comments = new JSONArray(response.body().string());

        for (int i = 0; i < comments.length(); i++) {
            JSONObject jcomment = (JSONObject) comments.get(i);
        }

        return null;
    }
}
