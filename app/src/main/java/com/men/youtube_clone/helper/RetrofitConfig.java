package com.men.youtube_clone.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(YoutubeConfig.URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
