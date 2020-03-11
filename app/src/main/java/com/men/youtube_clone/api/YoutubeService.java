package com.men.youtube_clone.api;

import com.men.youtube_clone.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {
    @GET("search")
    Call<Resultado> listVideos(@Query("part") String part, @Query("order") String order, @Query("maxResults") String maxResults, @Query("key") String key, @Query("channelId") String channelId, @Query("q") String q);
}
