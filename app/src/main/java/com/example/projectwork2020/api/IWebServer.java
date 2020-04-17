package com.example.projectwork2020.api;

import java.util.List;

public interface IWebServer {
    void onMovieFetched(boolean success, List<MoviePageResult> movies, int errorCode, String errorMessage);
}
