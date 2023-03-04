package com.example.movie_app_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app_api.adapter.MovieAdapter
import com.example.movie_app_api.api.ApiClien
import com.example.movie_app_api.api.ApiService
import com.example.movie_app_api.databinding.ActivityMainBinding
import com.example.movie_app_api.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val movieAdapter by lazy { MovieAdapter() }
    private val api : ApiService by lazy {
        ApiClien().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            progressBarMovie.visibility = View.VISIBLE
            val callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse>{
                override fun onResponse(call: Call<MovieListResponse>, response: Response<MovieListResponse>) {
                    progressBarMovie.visibility = View.GONE
                    when(response.code()){
                        in 200..299->{
                            response.body().let { itBody->
                                itBody?.results.let { itData->
                                    if (itData!!.isNotEmpty()){
                                        movieAdapter.differ.submitList(itData)
                                        recycleViewMovies.apply {
                                            layoutManager = LinearLayoutManager(this@MainActivity)
                                            adapter = movieAdapter
                                        }
                                    }
                                }
                            }
                        }
                        in 300..399->{
                            Log.d("Response Code","Redirection message : ${response.code()}")
                        }
                        in 400..499->{
                            Log.d("Response Code","Client error message : ${response.code()}")

                        }
                        in 500..599->{
                            Log.d("Response Code","Server error message : ${response.code()}")

                        }
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {

                    binding.progressBarMovie.visibility=View.GONE
                    Log.e("onFailure","Err: ${t.message}")
                }

            })
        }
    }
}