package br.com.natanaelribeiro.thebestmovies

import android.app.Application
import br.com.natanaelribeiro.thebestmovies.di.genresNetworkModule
import br.com.natanaelribeiro.thebestmovies.di.genresRepositoryModule
import br.com.natanaelribeiro.thebestmovies.di.moviesNetworkModule
import br.com.natanaelribeiro.thebestmovies.di.moviesRepositoryModule
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
                    moviesRepositoryModule
                )
            )
        }
    }
}