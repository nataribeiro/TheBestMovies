package br.com.natanaelribeiro.thebestmovies.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.MoviesRepository
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.helpers.CommandProvider
import br.com.natanaelribeiro.thebestmovies.helpers.GenericCommand
import br.com.natanaelribeiro.thebestmovies.helpers.SingleLiveEvent
import kotlin.coroutines.CoroutineContext

class UpcomingMoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val commandProvider: CommandProvider,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()
    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    data class ViewState(
        val isLoadingUpcomingMovies: Boolean = false
    )

    sealed class Command: GenericCommand() {

        class ShowMovieDetails(val movie: MoviesDTO.Movie): Command()
    }

    init {

        initViewState()
    }

    private fun initViewState() {

        viewState.value = ViewState()
    }
}