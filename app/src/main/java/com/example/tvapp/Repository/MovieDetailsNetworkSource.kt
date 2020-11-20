package com.example.tvapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvapp.API.MovieDBClient
import com.example.tvapp.API.TheMovieDBInterface
import com.example.tvapp.Vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkSource(private val apiservice: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState=MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
    get() = _networkState

    private val _downloadedMovieDetailsResponse=MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse:LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse


    fun FetchMovieDetails(movieId:Int) {
        _networkState.postValue(NetworkState.Loading)
        try {
            compositeDisposable.add(
                apiservice.getmovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.Loaded)
                        },
                        {
                            _networkState.postValue(NetworkState.Error)
                            Log.e("MovieDetailsDataSource", it.message!!)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message!!)
        }

    }

}