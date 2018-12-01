package com.enchantme.akali;

import android.app.Application;

import com.enchantme.akali.core.rss.RssAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class App extends Application {

    //region Variables

    private static RssAdapter rssAdapter;

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ka-news.de")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();
        rssAdapter = retrofit.create(RssAdapter.class);
    }

    //endregion

    //region Public Methods

    public static RssAdapter getRssAdapter() {
        return rssAdapter;
    }

    //endregion

}
