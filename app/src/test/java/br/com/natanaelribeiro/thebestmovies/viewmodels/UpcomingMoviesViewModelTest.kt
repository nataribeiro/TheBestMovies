package br.com.natanaelribeiro.thebestmovies.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.MoviesRepository
import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import br.com.natanaelribeiro.thebestmovies.data.paging.factory.MoviesDataSourceFactory
import br.com.natanaelribeiro.thebestmovies.helpers.CommandProvider
import br.com.natanaelribeiro.thebestmovies.helpers.GenericCommand
import br.com.natanaelribeiro.thebestmovies.helpers.SingleLiveEvent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpcomingMoviesViewModelTest {

    private lateinit var moviesRepositoryMock: MoviesRepository
    private lateinit var moviesDataSourceFactoryMock: MoviesDataSourceFactory
    private lateinit var commandProviderMock: CommandProvider

    private lateinit var viewStateObserver: Observer<UpcomingMoviesViewModel.ViewState>
    private lateinit var commandMock: SingleLiveEvent<GenericCommand>

    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var upcomingMoviesViewModel: UpcomingMoviesViewModel

    @Before fun setUp() {

        moviesRepositoryMock = mock()
        moviesDataSourceFactoryMock = mock()
        commandProviderMock = mock()

        commandMock = mock()

        whenever(commandProviderMock.getCommand()).thenReturn(commandMock)

        upcomingMoviesViewModel = UpcomingMoviesViewModel(
            moviesRepositoryMock,
            moviesDataSourceFactoryMock,
            commandProviderMock,
            Dispatchers.Unconfined
        )
    }

    @Test fun `Get movie genres, when is passed a list of genre ids, then return the genres formatted`() {

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

        val expectedGenresIds = listOf<Int>(2, 3)

        val expectedFormattedGenres = "$EXPECTED_GENRE_NAME_2, $EXPECTED_GENRE_NAME_3"

        upcomingMoviesViewModel.genresList = expectedGenresList

        // ACT

        val returnedFormattedGenres = upcomingMoviesViewModel.getMovieGenres(expectedGenresIds)

        // ASSERT

        assertEquals(expectedFormattedGenres, returnedFormattedGenres)
    }
}