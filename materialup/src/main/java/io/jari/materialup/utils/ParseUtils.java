package io.jari.materialup.utils;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * Created by Akash.
 */
public class ParseUtils {

    public static <T> String convertModelToJson(T model) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> jsonAdapter = moshi.adapter(model.getClass().getGenericSuperclass());
        return jsonAdapter.toJson(model);
    }

    public static <T> T convertJsonToModel(String json, Class<T> modelClass) throws IOException {
        JsonAdapter<T> adapter = new Moshi.Builder().build().adapter(modelClass);
        return adapter.fromJson(json);
    }
}
