package br.com.natanaelribeiro.thebestmovies.data.contract.repositories

import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface GenresRepository {

    fun getMoviesList(language: String): Deferred<Response<GenresDTO>>
}