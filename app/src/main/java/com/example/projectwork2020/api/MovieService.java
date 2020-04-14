package com.example.projectwork2020.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("/popular?api_key=cf42789ae5319df7aa57aaa0dd6473df")
    Call<List<Movie>> getMovie();

}
