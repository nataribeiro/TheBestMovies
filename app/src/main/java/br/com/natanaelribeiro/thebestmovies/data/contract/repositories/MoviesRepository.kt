package br.com.natanaelribeiro.thebestmovies.data.contract.repositories

import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface MoviesRepository {

    fun getUpcomingMovies(language: String?, page: Int?, region: String?): Deferred<Response<MoviesDTO>>
}