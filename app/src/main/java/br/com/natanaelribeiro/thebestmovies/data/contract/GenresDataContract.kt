package br.com.natanaelribeiro.thebestmovies.data.contract

import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface GenresDataContract {

    interface Remote {

        fun getMoviesList(language: String): Deferred<Response<GenresDTO>>
    }

}