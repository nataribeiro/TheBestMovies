package br.com.natanaelribeiro.thebestmovies.data.repositories

import br.com.natanaelribeiro.thebestmovies.data.contract.GenresDataContract
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.GenresRepository
import br.com.natanaelribeiro.thebestmovies.data.remote.GenresRemoteDataSource

class GenresDataRepository(
    private val genresRemoteDataSource: GenresDataContract.Remote
) : GenresRepository {

    override fun getMoviesList(language: String) = genresRemoteDataSource.getMoviesList(language)
}