package com.example.tvapp.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.tvapp.API.FIRST_PAGE
import com.example.tvapp.API.TheMovieDBInterface
import com.example.tvapp.Vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource (private val apiService : TheMovieDBInterface,private val compositeDisposable: CompositeDisposable)
: PageKeyedDataSource<Int, Movie>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        networkState.postValue(NetworkState.Loading)

        compositeDisposable.add(
                apiService.getPopularMovie(page)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    callback.onResult(it.movieList, null, page+1)
                                    networkState.postValue(NetworkState.Loaded)
                                },
                                {
                                    networkState.postValue(NetworkState.Error)
                                    Log.e("MovieDataSource", it.message!!)
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.Loading)

        compositeDisposable.add(
                apiService.getPopularMovie(params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    if(it.totalPages >= params.key) {
                                        callback.onResult(it.movieList, params.key+1)
                                        networkState.postValue(NetworkState.Loaded)
                                    }
                                    else{
                                        networkState.postValue(NetworkState.ENDOFLIST)
                                    }
                                },
                                {
                                    networkState.postValue(NetworkState.Error)
                                    Log.e("MovieDataSource", it.message!!)
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}