package br.com.natanaelribeiro.thebestmovies.data.remote

import br.com.natanaelribeiro.thebestmovies.BuildConfig
import br.com.natanaelribeiro.thebestmovies.data.contract.MoviesDataContract
import br.com.natanaelribeiro.thebestmovies.data.remote.endpoint.MoviesApiClient

class MoviesRemoteDataSource(
    private val moviesApiClient: MoviesApiClient
) : MoviesDataContract.Remote {

    override fun getUpcomingMovies(language: String?, page: Int?, region: String?)
            = moviesApiClient.getGenres(BuildConfig.API_KEY, language, page, region)
}