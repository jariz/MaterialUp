package io.jari.materialup.responses;

import java.util.ArrayList;
import java.util.List;

import io.jari.materialup.models.Item;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class ItemResponse {

    private List<Item> mItemList = new ArrayList<>();

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        this.mItemList = itemList;
    }
}
