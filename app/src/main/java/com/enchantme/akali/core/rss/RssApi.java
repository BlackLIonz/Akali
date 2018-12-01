package com.enchantme.akali.core.rss;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RssApi {
    @GET("{path}")
    Call<Feed> getItems(@Path(value = "path", encoded = true) String path);
}
