package br.com.natanaelribeiro.thebestmovies.data.contract

import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface MoviesDataContract {

    interface Remote {

        fun getUpcomingMovies(language: String?, page: Int?, region: String?): Deferred<Response<MoviesDTO>>
    }
}