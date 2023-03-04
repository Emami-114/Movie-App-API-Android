package com.example.movie_app_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import coil.size.Scale
import com.example.movie_app_api.api.ApiClien
import com.example.movie_app_api.api.ApiService
import com.example.movie_app_api.databinding.ActivityDetailBinding
import com.example.movie_app_api.databinding.ActivityMainBinding
import com.example.movie_app_api.response.DetailsMovies
import com.example.movie_app_api.utils.Constants.POSTER_BASEURL
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private val api:ApiService by lazy {
        ApiClien().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId = intent.getIntExtra("id",1)
        binding.apply {
            val callDetaileMovieApi = api.getMoviedetailes(movieId)
            callDetaileMovieApi.enqueue(object : retrofit2.Callback<DetailsMovies>{
                override fun onResponse(call: Call<DetailsMovies>, response: Response<DetailsMovies>) {
                    when(response.code()){
                        in 200..299 ->{
                            response.body().let { itBody->
                                val imagePoster = POSTER_BASEURL + itBody!!.poster_path
                                imgMovie.load(imagePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.ic_launcher_background)
                                    scale(Scale.FILL)
                                }
                                imgBackground.load(imagePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.ic_launcher_background)
                                    scale(Scale.FILL)
                                }
                                tvMovieName.text = itBody.title
                                tvTagLine.text=itBody.tagline
                                tvMovieRelasedData.text=itBody.release_date
                                tvMovieRateNumber.text=itBody.vote_average.toString()
                                tvMovieRundTime.text=itBody.runtime.toString()
                                tvMovieBudgetNumber.text=itBody.budget.toString()
                                tvMovieRevenueNumber.text=itBody.revenue.toString()
                                tvMovieOverViewText.text=itBody.overview
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

                override fun onFailure(call: Call<DetailsMovies>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }
    }
}