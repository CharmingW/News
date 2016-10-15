package com.charming.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by 56223 on 2016/9/24.
 */

public class NewsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<News> newsList;
    private ImageLoader imageLoader;

    public NewsListAdapter(Context context) {
        this.context = context;
        initImageLoader();
    }

    public NewsListAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        initImageLoader();
    }

    public void setData(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.small_image);
            TextView title = (TextView) convertView.findViewById(R.id.news_title);
            TextView time = (TextView) convertView.findViewById(R.id.news_time);
            holder.image = image;
            holder.title = title;
            holder.time = time;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News news = newsList.get(position);
        holder.title.setText(news.title);
        holder.time.setText(news.cTime);
        imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(news.picUrl, holder.image);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = (News) getItem(position);
                Intent intent = new Intent(context, NewsContentActivity.class);
                intent.putExtra("url", news.url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
        TextView time;
    }

    private void initImageLoader() {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
}
