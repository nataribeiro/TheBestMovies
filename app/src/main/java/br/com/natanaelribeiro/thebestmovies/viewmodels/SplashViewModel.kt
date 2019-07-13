package br.com.natanaelribeiro.thebestmovies.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.GenresRepository
import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import br.com.natanaelribeiro.thebestmovies.helpers.CommandProvider
import br.com.natanaelribeiro.thebestmovies.helpers.GenericCommand
import br.com.natanaelribeiro.thebestmovies.helpers.SingleLiveEvent
import br.com.natanaelribeiro.thebestmovies.helpers.getFormattedLanguage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    private val genresRepository: GenresRepository,
    private val commandProvider: CommandProvider,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    val command: SingleLiveEvent<GenericCommand> = commandProvider.getCommand()

    sealed class Command: GenericCommand() {
        class CallUpcomingMoviesList(val genresList: GenresDTO) : Command()
        object ShowUnexpectedError: Command()
    }

    fun loadGenres() {

        GlobalScope.launch(coroutineContext) {

            try {


                val response = genresRepository.getMoviesList(Locale.getDefault().getFormattedLanguage()).await()

                if (response.isSuccessful) {

                    response.body()?.let {

                        command.setValue(Command.CallUpcomingMoviesList(it))
                    }

                } else {

                    command.setValue(Command.ShowUnexpectedError)
                }
            } catch (e: Exception) {

                command.setValue(Command.ShowUnexpectedError)
            }
        }
    }
}