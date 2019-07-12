package br.com.natanaelribeiro.thebestmovies

import android.app.Application
import br.com.natanaelribeiro.thebestmovies.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@BaseApplication)

            modules(
                listOf(
                    commandInjectorModule,
                    genresNetworkModule,
                    genresRepositoryModule,
                    moviesNetworkModule,
                    moviesRepositoryModule,
                    splashViewModelModule,
                    moviesDataPagingModule,
                    upcomingMoviesViewModelModule
                )
            )
        }
    }
}