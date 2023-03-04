package com.example.movie_app_api.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movie_app_api.DetailActivity
import com.example.movie_app_api.R
import com.example.movie_app_api.databinding.ItemRowBinding
import com.example.movie_app_api.response.MovieListResponse
import com.example.movie_app_api.response.Result
import com.example.movie_app_api.utils.Constants.POSTER_BASEURL

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var binding: ItemRowBinding
    private lateinit var context:Context

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        fun bind(item: Result){
            binding.apply {
                tvMovieName.text = item.title
                tvRate.text = item.vote_average.toString()
                val moviePosterUrl = POSTER_BASEURL + item.poster_path
                imgMovie.load(moviePosterUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    scale(Scale.FILL)
                }
                tvLanguage.text = item.original_language
                tvMovieDate.text = item.release_date

                root.setOnClickListener {
                    val intent = Intent(context,DetailActivity::class.java)
                    intent.putExtra("id",item.id)
                    context.startActivities(arrayOf(intent))
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRowBinding.inflate(inflater,parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private val differCallback=object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}