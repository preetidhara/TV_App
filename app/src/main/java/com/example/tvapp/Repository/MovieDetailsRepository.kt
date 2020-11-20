package com.example.tvapp.Repository

import androidx.lifecycle.LiveData
import com.example.tvapp.API.TheMovieDBInterface
import com.example.tvapp.Vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkSource: MovieDetailsNetworkSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<MovieDetails> {

        movieDetailsNetworkSource=MovieDetailsNetworkSource(apiService,compositeDisposable)
        movieDetailsNetworkSource.FetchMovieDetails(movieId)

        return movieDetailsNetworkSource.downloadedMovieDetailsResponse

    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState> {
        return movieDetailsNetworkSource.networkState
    }
}