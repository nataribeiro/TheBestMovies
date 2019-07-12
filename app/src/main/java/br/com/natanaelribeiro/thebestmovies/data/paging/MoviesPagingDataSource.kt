package br.com.natanaelribeiro.thebestmovies.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import br.com.natanaelribeiro.thebestmovies.data.contract.MoviesDataContract
import br.com.natanaelribeiro.thebestmovies.data.enums.PagingNetworkState
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.helpers.getFormattedLanguage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class MoviesPagingDataSource(
    private val moviesRemoteDataSource: MoviesDataContract.Remote,
    private val coroutineContext: CoroutineContext
) : ItemKeyedDataSource<String, MoviesDTO.Movie>() {

    val initialLoadingLiveData = MutableLiveData<PagingNetworkState>()
    val networkStateLiveData = MutableLiveData<PagingNetworkState>()

    private val LANGUAGE = Locale.getDefault().getFormattedLanguage()
    private var PAGE = 1
    private val REGION = null

    fun setPage(page: Int) {

        PAGE = page
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<MoviesDTO.Movie>) {

        GlobalScope.launch(coroutineContext) {

            initialLoadingLiveData.postValue(PagingNetworkState.LOADING)
            networkStateLiveData.postValue(PagingNetworkState.LOADING)

            try {

                val response = moviesRemoteDataSource.getUpcomingMovies(LANGUAGE, PAGE, REGION).await()

                if (response.isSuccessful) {

                    networkStateLiveData.postValue(PagingNetworkState.SUCCESS)

                    var moviesList: MutableList<MoviesDTO.Movie> = mutableListOf()

                    response.body()?.let {
                        moviesList = it.results.toMutableList()
                    }

                    if (moviesList.isNotEmpty()) {

                        initialLoadingLiveData.postValue(PagingNetworkState.SUCCESS_WITH_ITEMS)
                    } else {

                        initialLoadingLiveData.postValue(PagingNetworkState.SUCCESS_WITHOUT_ITEMS)
                    }

                    callback.onResult(moviesList)
                } else {

                    onInitialLoadError()
                }

            } catch (e: Exception) {

                onInitialLoadError()
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<MoviesDTO.Movie>) {
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<MoviesDTO.Movie>) {

        PAGE++

        GlobalScope.launch(coroutineContext) {

            networkStateLiveData.postValue(PagingNetworkState.LOADING)

            try {

                val response = moviesRemoteDataSource.getUpcomingMovies(LANGUAGE, PAGE, REGION).await()

                if (response.isSuccessful) {

                    networkStateLiveData.postValue(PagingNetworkState.SUCCESS)

                    var moviesList: MutableList<MoviesDTO.Movie> = mutableListOf()

                    response.body()?.let {
                        moviesList = it.results.toMutableList()
                    }

                    callback.onResult(moviesList)
                } else {

                    onPagingError()
                }

            } catch (e: Exception) {

                onPagingError()
            }


        }
    }

    override fun getKey(item: MoviesDTO.Movie): String = ""

    private fun onInitialLoadError() {

        initialLoadingLiveData.postValue(PagingNetworkState.FAILED)
    }

    private fun onPagingError() {

        networkStateLiveData.postValue(PagingNetworkState.FAILED)
    }
}