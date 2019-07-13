package br.com.natanaelribeiro.thebestmovies.data.remote.endpoint

import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GenresApiClient {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    ) @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): Deferred<Response<GenresDTO>>
}