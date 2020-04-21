package com.example.projectwork2020.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("movie/popular")
    Call<MoviePageResult> getMovie(@Query("api_key") String apiKey);

}
