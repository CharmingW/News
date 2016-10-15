package com.charming.news;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 56223 on 2016/9/22.
 */

public class GetNewsTask extends AsyncTask<String, Integer, Object> {

    private static final String TAG = "GetNewsTask";
    private static final String API_KEY = "c58b256f3b2c3b79dad4320888b8e5e3";

    private onResponseCallback responseCallback;

    public interface onResponseCallback {
        public void onResponseSuccess(String result);

        public void onResponseError(Exception e);
    }

    public void setResponseCallback(onResponseCallback callback) {
        responseCallback = callback;
    }


    @Override
    protected Object doInBackground(String... params) {
        BufferedReader reader;
        String result;
        HttpURLConnection connection;
        URL url;

        try {
            if (params.length == 2) {
                url = new URL(params[0] + "?" + params[1]);

            } else {
                url = new URL(params[0]);
            }
            connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestProperty("apikey", API_KEY);
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
                sb.append("\r\n");
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (responseCallback != null) {
            if (result instanceof String) {
                Log.d(TAG, "onResponseSuccess");
                responseCallback.onResponseSuccess((String) result);
            } else if (result instanceof Exception) {
                Log.d(TAG, "onResponseError");
                responseCallback.onResponseError((Exception) result);
            } else {
                Log.e(TAG, "Unknown type data");
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    }
}
