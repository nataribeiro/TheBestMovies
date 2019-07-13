package br.com.natanaelribeiro.thebestmovies.data.paging.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.natanaelribeiro.thebestmovies.data.contract.MoviesDataContract
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.data.paging.MoviesPagingDataSource
import kotlinx.coroutines.Dispatchers

class MoviesDataSourceFactory(private val moviesRemoteDataSource: MoviesDataContract.Remote)
    : DataSource.Factory<String, MoviesDTO.Movie>() {

    val moviesPagingDataSourceLiveData = MutableLiveData<MoviesPagingDataSource>()

    private lateinit var moviesPagingDataSource: MoviesPagingDataSource

    private var page: Int = 1

    fun setPage(page: Int) {

        this.page = page
    }

    override fun create(): DataSource<String, MoviesDTO.Movie> {

        moviesPagingDataSource = MoviesPagingDataSource(moviesRemoteDataSource, Dispatchers.Main)

        moviesPagingDataSource.setPage(page)

        moviesPagingDataSourceLiveData.postValue(moviesPagingDataSource)

        return moviesPagingDataSource
    }
}