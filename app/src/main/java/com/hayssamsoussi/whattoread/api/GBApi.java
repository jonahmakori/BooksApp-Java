package com.hayssamsoussi.whattoread.api;

import com.hayssamsoussi.whattoread.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GBApi {
    @GET("volumes")
    Call<Example> getBooks(@Query("q") String id, @Query("maxResults") Integer results, @Query("country") String country, @Query("startIndex") Integer startIndex, @Query("key") String key);
}
