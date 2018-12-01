package com.enchantme.akali;

import android.app.Application;

import com.enchantme.akali.core.rss.RssApi;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class App extends Application {

    //region Variables

    private static RssApi rssApi;

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ka-news.de")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();
        rssApi = retrofit.create(RssApi.class);
    }

    //endregion

    //region Public Methods

    public static RssApi getRssApi() {
        return rssApi;
    }

    //endregion

}
