package br.com.natanaelribeiro.thebestmovies.di

import br.com.natanaelribeiro.thebestmovies.BuildConfig
import br.com.natanaelribeiro.thebestmovies.data.RestClient
import br.com.natanaelribeiro.thebestmovies.data.contract.GenresDataContract
import br.com.natanaelribeiro.thebestmovies.data.contract.MoviesDataContract
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.GenresRepository
import br.com.natanaelribeiro.thebestmovies.data.contract.repositories.MoviesRepository
import br.com.natanaelribeiro.thebestmovies.data.remote.GenresRemoteDataSource
import br.com.natanaelribeiro.thebestmovies.data.remote.MoviesRemoteDataSource
import br.com.natanaelribeiro.thebestmovies.data.remote.endpoint.GenresApiClient
import br.com.natanaelribeiro.thebestmovies.data.remote.endpoint.MoviesApiClient
import br.com.natanaelribeiro.thebestmovies.data.repositories.GenresDataRepository
import br.com.natanaelribeiro.thebestmovies.data.repositories.MoviesDataRepository
import br.com.natanaelribeiro.thebestmovies.viewmodels.SplashViewModel
import br.com.natanaelribeiro.thebestmovies.viewmodels.UpcomingMoviesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val genresNetworkModule: Module = module {

    factory<GenresDataContract.Remote> { GenresRemoteDataSource(genresApiClient = get()) }
    single { RestClient.getApiClient(
        serviceClass = GenresApiClient::class.java,
        baseEndpoint = BuildConfig.API_URL
    ) }
}

val genresRepositoryModule: Module = module {

    factory<GenresRepository> { GenresDataRepository(
        genresRemoteDataSource = get()
    ) }
}

val moviesNetworkModule: Module = module {

    factory<MoviesDataContract.Remote> { MoviesRemoteDataSource(moviesApiClient = get()) }
    single { RestClient.getApiClient(
        serviceClass = MoviesApiClient::class.java,
        baseEndpoint = BuildConfig.API_URL
    ) }
}

val moviesRepositoryModule: Module = module {

    factory<MoviesRepository> { MoviesDataRepository(
        moviesRemoteDataSource = get()
    ) }
}

val splashViewModelModule: Module = module {

    viewModel { SplashViewModel(
        genresRepository = get(),
        commandProvider = get(),
        coroutineContext = Dispatchers.Main
    ) }
}

val upcomingMoviesViewModelModule: Module = module {

    viewModel { UpcomingMoviesViewModel(
        moviesRepository = get(),
        commandProvider = get(),
        coroutineContext = Dispatchers.Main
    ) }
}