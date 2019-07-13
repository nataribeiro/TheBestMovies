package br.com.natanaelribeiro.thebestmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.MoviesRepository
import br.com.natanaelribeiro.thebestmovies.data.enums.PagingNetworkState
import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.data.paging.factory.MoviesDataSourceFactory
import br.com.natanaelribeiro.thebestmovies.helpers.CommandProvider
import br.com.natanaelribeiro.thebestmovies.helpers.GenericCommand
import br.com.natanaelribeiro.thebestmovies.helpers.SingleLiveEvent
import kotlin.coroutines.CoroutineContext

class UpcomingMoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val moviesDataSourceFactory: MoviesDataSourceFactory,
    private val commandProvider: CommandProvider,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    data class ViewState(
        val isLoadingUpcomingMovies: Boolean = false
    )

    sealed class Command: GenericCommand() {

        class ShowMovieDetails(val movie: MoviesDTO.Movie, val movieGenres: String): Command()
    }

    lateinit var moviesInitialLoadingLiveData: LiveData<PagingNetworkState>
    lateinit var moviesNetworkStateLiveData: LiveData<PagingNetworkState>
    lateinit var moviesPagedListLiveData: LiveData<PagedList<MoviesDTO.Movie>>

    lateinit var genresList: GenresDTO

    init {

        initViewState()
        initPagedMovies()
    }

    private fun initViewState() {

        viewState.value = ViewState()
    }

    private fun initPagedMovies() {

        moviesInitialLoadingLiveData = Transformations
            .switchMap(moviesDataSourceFactory.moviesPagingDataSourceLiveData) { moviesPagingDataSource ->
                moviesPagingDataSource.initialLoadingLiveData
            }

        moviesNetworkStateLiveData = Transformations
            .switchMap(moviesDataSourceFactory.moviesPagingDataSourceLiveData) { moviesPagingDataSource ->
                moviesPagingDataSource.networkStateLiveData
            }

        val moviesPagingConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(false)
            .build()

        moviesPagedListLiveData = LivePagedListBuilder(
            moviesDataSourceFactory,
            moviesPagingConfig
        ).build()
    }

    fun refreshMovies() {

        moviesDataSourceFactory.setPage(1)

        moviesDataSourceFactory.moviesPagingDataSourceLiveData.value?.invalidate()
    }

    fun getMovieGenres(genresIds: List<Int>): String {
        val genres: MutableList<String> = mutableListOf()

        genresIds.forEach { id ->

            genres.add(genresList.genres.find {
                it.id == id
            }?.name.orEmpty())
        }

        return genres.joinToString()
    }

    fun showItemDetails(movie: MoviesDTO.Movie) {

        command.setValue(Command.ShowMovieDetails(movie, getMovieGenres(movie.genre_ids)))
    }
}