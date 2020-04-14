package com.example.projectwork2020.api;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {

    private String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static WebService instance;
    private MovieService movieService;

    private WebService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    public static WebService getInstance() {
        if (instance == null)
            instance = new WebService();
        return instance;
    }
    public void getMovie(final IWebServer callback) {
        Call<List<Movie>> moviesRequest = movieService.getMovie();
        moviesRequest.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.code() == 200) {
                    callback.onMovieFetched(true, response.body(), -1, null);
                } else {
                    try {
                        callback.onMovieFetched(true, null, response.code(), response.errorBody().string());
                    } catch (IOException ex) {
                        Log.e("WebService", ex.toString());
                        callback.onMovieFetched(true, null, response.code(), "Generic error message");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                callback.onMovieFetched(false, null, -1, t.getLocalizedMessage());
            }

        });
    }


}
