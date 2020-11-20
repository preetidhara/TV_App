package com.example.tvapp.Repository

import androidx.lifecycle.MutableLiveData
import com.example.tvapp.API.TheMovieDBInterface
import com.example.tvapp.Vo.Movie
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}