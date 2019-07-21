package com.hayssamsoussi.whattoread.api;

import com.hayssamsoussi.whattoread.helpers.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GBApiClient {

    private static final String TMDB_BASE_URL = Constants.GB_API_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
