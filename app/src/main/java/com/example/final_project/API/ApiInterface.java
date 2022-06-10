package com.example.final_project.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public  interface ApiInterface {

    @GET("release-group/")
    Call<List<MusicBrainzApiPOJO>> getMusicBrainzResponse
            (@Query("query") String query, @Query("fmt") String json);

    @GET("release-group/{MBID}&fmt=json")
    Call<String> getImageResponse(@Path("MBID") String MBID);
}


