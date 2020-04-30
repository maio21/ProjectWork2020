package com.example.projectwork2020.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false")
    Call<MoviePageResult> getMovie(@Query("api_key") String apiKey, @Query("page") int page);

}
