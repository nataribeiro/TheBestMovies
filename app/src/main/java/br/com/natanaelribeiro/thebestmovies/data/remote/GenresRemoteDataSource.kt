package br.com.natanaelribeiro.thebestmovies.data.remote

import br.com.natanaelribeiro.thebestmovies.BuildConfig
import br.com.natanaelribeiro.thebestmovies.data.contract.GenresDataContract
import br.com.natanaelribeiro.thebestmovies.data.remote.endpoint.GenresApiClient

class GenresRemoteDataSource(
    private val genresApiClient: GenresApiClient
) : GenresDataContract.Remote {

    override fun getMoviesList(language: String) = genresApiClient.getGenres(BuildConfig.API_KEY, language)
}