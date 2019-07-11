package br.com.natanaelribeiro.thebestmovies.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import br.com.natanaelribeiro.thebestmovies.R
import br.com.natanaelribeiro.thebestmovies.helpers.Constants
import br.com.natanaelribeiro.thebestmovies.helpers.Constants.EXTRA_GENRES_LIST
import br.com.natanaelribeiro.thebestmovies.viewmodels.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prepareViewModel()
    }

    private fun prepareViewModel() {

        splashViewModel.apply {

            loadGenres()

            command.observe(this@SplashActivity, Observer { cmd ->

                when(cmd) {

                    is SplashViewModel.Command.CallUpcomingMoviesList -> {

                        val intent = Intent(this@SplashActivity, UpcomingMoviesActivity::class.java)
                        intent.putExtra(EXTRA_GENRES_LIST, cmd.genresList)

                        startActivity(intent)
                    }

                    is SplashViewModel.Command.ShowUnexpectedError -> {

                        val alert = AlertDialog.Builder(this@SplashActivity)

                        alert
                            .setTitle("Warning")
                            .setMessage("Something went wrong, please check your connection")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                                loadGenres()
                                dialog.dismiss()
                            }
                    }
                }
            })
        }
    }
}
