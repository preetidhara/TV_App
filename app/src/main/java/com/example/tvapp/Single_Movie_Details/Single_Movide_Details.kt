package com.example.tvapp.Single_Movie_Details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.tvapp.API.MovieDBClient
import com.example.tvapp.API.POSTER_BASE_URL
import com.example.tvapp.API.TheMovieDBInterface
import com.example.tvapp.R
import com.example.tvapp.Repository.MovieDetailsRepository
import com.example.tvapp.Repository.NetworkState
import com.example.tvapp.Vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single__movide__details.*
import java.text.NumberFormat
import java.util.*

class Single_Movide_Details : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single__movide__details)

        //viewModel= ViewModelProviders.of(this).get(SingleMovieViewModel::class.java)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
        })



        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = if (it == NetworkState.Loading) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.Error) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);


    }


    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}


