package com.charming.dataparser;

import android.util.Log;

import com.charming.news.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 56223 on 2016/9/22.
 */

public class JsonParser {

    private static final String TAG = "JsonParser";

    private static JsonParser parser;

    private JsonParser() {
    }

    public static JsonParser getInstance() {
        if (parser == null) {
            synchronized (JsonParser.class) {
                if (parser == null) {
                    parser = new JsonParser();
                    return parser;
                }
            }
        }
        return parser;
    }

    public ArrayList<News> parse(String source) {
        ArrayList<News> newsList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(source);
            int responseCode = object.getInt("code");
            String result_ok = object.getString("msg");
            if (responseCode == 200 && "success".equals(result_ok)) {
                JSONArray newsArray = object.getJSONArray("newslist");
                for (int i = 0; i < newsArray.length(); i++) {
                    News newsItem = new News();
                    JSONObject news = (JSONObject) newsArray.get(i);
                    newsItem.cTime = news.getString("ctime");
                    newsItem.title = news.getString("title");
                    newsItem.url = news.getString("url");
                    newsItem.picUrl = news.getString("picUrl");
                    newsItem.description = news.getString("description");
                    newsList.add(newsItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG, source);
            return null;
        }
        return newsList;
    }

}
