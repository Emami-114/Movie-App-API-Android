package com.example.movie_app_api.api

import com.example.movie_app_api.response.DetailsMovies
import com.example.movie_app_api.response.MovieListResponse
import com.example.movie_app_api.response.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface  ApiService {
    @GET("movie/popular")
     fun getPopularMovie(@Query("page") id: Int) : Call<MovieListResponse>

     @GET("movie/{movie_id}")
     fun getMoviedetailes(@Path("movie_id") id:Int ) : Call<DetailsMovies>
}