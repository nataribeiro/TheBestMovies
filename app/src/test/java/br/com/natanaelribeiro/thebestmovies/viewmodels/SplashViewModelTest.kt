package br.com.natanaelribeiro.thebestmovies.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.GenresRepository
import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import br.com.natanaelribeiro.thebestmovies.helpers.CommandProvider
import br.com.natanaelribeiro.thebestmovies.helpers.GenericCommand
import br.com.natanaelribeiro.thebestmovies.helpers.SingleLiveEvent
import br.com.natanaelribeiro.thebestmovies.helpers.getFormattedLanguage
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.*

class SplashViewModelTest {

    private lateinit var genresRepositoryMock: GenresRepository
    private lateinit var commandProviderMock: CommandProvider

    private lateinit var commandMock: SingleLiveEvent<GenericCommand>

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var splashViewModel: SplashViewModel

    @Before fun setUp() {

        genresRepositoryMock = mock()
        commandProviderMock = mock()

        commandMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        splashViewModel = SplashViewModel(
            genresRepositoryMock,
            commandProviderMock,
            Dispatchers.Unconfined)
    }

    @Test fun `Load genres, when is requested, then call upcoming movies list activity`() {

        // ARRANGE

        val EXPECTED_GENRE_ID_1 = 1
        val EXPECTED_GENRE_NAME_1 = "Ação"

        val EXPECTED_GENRE_ID_2 = 2
        val EXPECTED_GENRE_NAME_2 = "Comédia"

        val EXPECTED_GENRE_ID_3 = 3
        val EXPECTED_GENRE_NAME_3 = "Suspense"

        val expectedGenre1 = GenresDTO(listOf()).Genre(
            EXPECTED_GENRE_ID_1,
            EXPECTED_GENRE_NAME_1
        )

        val expectedGenre2 = GenresDTO(listOf()).Genre(
            EXPECTED_GENRE_ID_2,
            EXPECTED_GENRE_NAME_2
        )

        val expectedGenre3 = GenresDTO(listOf()).Genre(
            EXPECTED_GENRE_ID_3,
            EXPECTED_GENRE_NAME_3
        )

        val expectedGenresList = GenresDTO(arrayListOf(
            expectedGenre1,
            expectedGenre2,
            expectedGenre3
        ))

        val expectedResponse = Response.success(expectedGenresList)

        val expectedDeferredFeesDTO = CompletableDeferred<Response<GenresDTO>>(expectedResponse)

        val EXPECTED_COMMAND = SplashViewModel.Command.CallUpcomingMoviesList(expectedResponse.body()!!)

        whenever(genresRepositoryMock.getMoviesList(Locale.getDefault().getFormattedLanguage())).thenReturn(expectedDeferredFeesDTO)

        // ACT

        splashViewModel.loadGenres()

        // ASSERT

        assertEquals(EXPECTED_COMMAND.genresList, runBlocking { expectedDeferredFeesDTO.await().body() })
    }
}