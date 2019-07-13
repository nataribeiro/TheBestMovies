package br.com.natanaelribeiro.thebestmovies.data.repositories

import br.com.natanaelribeiro.thebestmovies.data.contract.MoviesDataContract
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.MoviesRepository
import br.com.natanaelribeiro.thebestmovies.data.remote.MoviesRemoteDataSource

class MoviesDataRepository(
    private val moviesRemoteDataSource: MoviesDataContract.Remote
): MoviesRepository {

    override fun getUpcomingMovies(language: String?, page: Int?, region: String?)
            = moviesRemoteDataSource.getUpcomingMovies(language, page, region)
}