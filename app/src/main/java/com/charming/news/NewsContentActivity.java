package com.charming.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class NewsContentActivity extends AppCompatActivity implements GetNewsTask.onResponseCallback {

    private WebView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_content);
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent content = getIntent();
        String contentUrl = content.getStringExtra("url");
        contentView = new WebView(this);
        contentView.getSettings().setJavaScriptEnabled(true);
        contentView.getSettings().setSupportZoom(true);
        contentView.getSettings().setBuiltInZoomControls(true);
        contentView.getSettings().setUseWideViewPort(true);
        contentView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        contentView.getSettings().setLoadWithOverviewMode(true);
        contentView.loadUrl(contentUrl);
//        task.execute(url);
//        ((TextView) findViewById(R.id.news_title)).setText(content.getStringExtra("title"));
//        ((TextView) findViewById(R.id.news_time)).setText(content.getStringExtra("cTime"));
//        picHolder = (WebView) findViewById(R.id.pic_holder);
//        picUrl = content.getStringExtra("picUrl");
//        WebView webView = new WebView(this);
//        webView.loadUrl(content.getStringExtra("url"));
        setContentView(contentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        picHolder.loadUrl(picUrl);
    }

    @Override
    public void onResponseSuccess(String result) {
//        if (result != null && contentView != null) {
//            contentView.setText(result);
//        }
    }

    @Override
    public void onResponseError(Exception e) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
