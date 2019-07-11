package br.com.natanaelribeiro.thebestmovies

import android.app.Application
import br.com.natanaelribeiro.thebestmovies.di.*
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            modules(
                listOf(
                    genresNetworkModule,
                    genresRepositoryModule,
                    moviesNetworkModule,
                    moviesRepositoryModule,
                    splashViewModelModule,
                    upcomingMoviesViewModelModule
                )
            )
        }
    }
}