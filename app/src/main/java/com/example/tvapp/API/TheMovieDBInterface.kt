package com.example.tvapp.API
import com.example.tvapp.Vo.MovieDetails
import com.example.tvapp.Vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int):Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getmovieDetails(@Path("movie_id") id:Int):Single<MovieDetails>
}
