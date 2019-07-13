package br.com.natanaelribeiro.thebestmovies.data.remote.endpoint

import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApiClient {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    ) @GET("movie/upcoming")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Deferred<Response<MoviesDTO>>
}