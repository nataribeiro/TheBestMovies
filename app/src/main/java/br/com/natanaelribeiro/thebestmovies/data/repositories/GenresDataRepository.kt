package br.com.natanaelribeiro.thebestmovies.data.repositories

import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.GenresRepository
import br.com.natanaelribeiro.thebestmovies.data.remote.GenresRemoteDataSource

class GenresDataRepository(
    private val genresRemoteDataSource: GenresRemoteDataSource
) : GenresRepository {

    override fun getMoviesList(language: String) = genresRemoteDataSource.getMoviesList(language)
}