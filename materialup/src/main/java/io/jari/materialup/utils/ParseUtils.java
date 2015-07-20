package io.jari.materialup.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.jari.materialup.models.Item;
import io.jari.materialup.responses.ItemResponse;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class ParseUtils {

    public static ItemResponse parseItems(String response) {
        ItemResponse itemsResponse = new ItemResponse();
        Document document = Jsoup.parse(response);
        Elements elements = document.select(".post-list-items .post-list-item");

        for (Element element : elements) {
            Item item = new Item();
            item.setId(element.attr("id"));
            item.setTitle(element.select("h2").first().text());
            if (item.getTitle().equals("")) continue;
            item.setImageUrl(element.select("img.preview").first().attr("data-cfsrc"));
            if (item.getImageUrl() == null || "".equals(item.getImageUrl())) continue;
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

            itemsResponse.getItemList().add(item);
        }

        return itemsResponse;

    }

    public static String parseImageUrl(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        String html = jsonObject.getString("content");

        Document document = Jsoup.parse(html);

        Element img = document.select("img.preview").first();

        if (img != null) {
            return img.attr("src");
        } else {
            return null;
        }
    }
}
