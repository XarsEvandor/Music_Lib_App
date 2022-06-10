package com.example.final_project.API;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHandler {
    private ReleaseGroup releaseGroup;
    private String imageURL;
    List<MusicBrainzApiPOJO> pojos;

    public ReleaseGroup getReleaseGroup(String title, String artist, Context context) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        String query = "release= %s &artist= %s";
        query = String.format(query, title, artist);
        Call<List<MusicBrainzApiPOJO>> call = api.getMusicBrainzResponse(query, "json");

        call.enqueue(new Callback<List<MusicBrainzApiPOJO>>() {
            @Override
            public void onResponse(Call<List<MusicBrainzApiPOJO>> call, Response<List<MusicBrainzApiPOJO>> response) {
                pojos = response.body();
                releaseGroup = pojos.get(0).getReleaseGroups().get(0);
            }

            @Override
            public void onFailure(Call<List<MusicBrainzApiPOJO>> call, Throwable t) {
                Toast.makeText(context, "Error while communicating with the server.", Toast.LENGTH_SHORT).show();
            }
        });

        String debugURL = String.valueOf(call.request().url());
        return releaseGroup;
    }

    public String getImageURL(String title, String artist, Context context){
        ReleaseGroup releaseGroup = this.getReleaseGroup(title, artist, context);
        String releaseId = releaseGroup.getId();


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://coverartarchive.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> call = api.getImageResponse(releaseId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                imageURL = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error while communicating with the server.", Toast.LENGTH_SHORT).show();
            }
        });

        return imageURL;
    }


}
