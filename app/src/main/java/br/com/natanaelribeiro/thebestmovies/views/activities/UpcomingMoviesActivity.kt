package br.com.natanaelribeiro.thebestmovies.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.natanaelribeiro.thebestmovies.R
import br.com.natanaelribeiro.thebestmovies.data.enums.PagingNetworkState
import br.com.natanaelribeiro.thebestmovies.data.models.GenresDTO
import br.com.natanaelribeiro.thebestmovies.helpers.Constants
import br.com.natanaelribeiro.thebestmovies.viewmodels.UpcomingMoviesViewModel
import br.com.natanaelribeiro.thebestmovies.views.adapters.PagedMoviesAdapter
import kotlinx.android.synthetic.main.activity_upcoming_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingMoviesActivity : AppCompatActivity() {

    private val upcomingMoviesViewModel by viewModel<UpcomingMoviesViewModel>()

    private lateinit var pagedMoviesAdapter: PagedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movies)

        prepareUI()
        prepareViewModel()
    }

    override fun onResume() {
        super.onResume()

        upcomingMoviesViewModel.refreshMovies()
    }

    private fun prepareUI() {

        pagedMoviesAdapter = PagedMoviesAdapter(upcomingMoviesViewModel)

        recyclerView.adapter = pagedMoviesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun prepareViewModel() {

        upcomingMoviesViewModel.apply {

            genresList = intent.getSerializableExtra(Constants.EXTRA_GENRES_LIST) as GenresDTO

            moviesInitialLoadingLiveData.observe(this@UpcomingMoviesActivity, Observer { pagNetworkState ->

                pagNetworkState?.let {

                    when (pagNetworkState) {

                        PagingNetworkState.FAILED -> {

                            val alert = AlertDialog.Builder(this@UpcomingMoviesActivity)

                            alert
                                .setTitle("Warning")
                                .setMessage("Something went wrong, please check your connection")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                                    refreshMovies()
                                    dialog.dismiss()
                                }
                        }

                        PagingNetworkState.LOADING -> {

                            loadingProgressBar.visibility = View.VISIBLE
                        }

                        else -> {

                            loadingProgressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            })

            moviesNetworkStateLiveData.observe(this@UpcomingMoviesActivity, Observer { pagNetworkState ->

                if (pagNetworkState == PagingNetworkState.FAILED) {

                    val alert = AlertDialog.Builder(this@UpcomingMoviesActivity)

                    alert
                        .setTitle("Warning")
                        .setMessage("Something went wrong, please check your connection")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok) { dialog, _ ->
                            refreshMovies()
                            dialog.dismiss()
                        }
                } else {

                    pagNetworkState?.let {

                        pagedMoviesAdapter.setPagingNetworkState(it)
                    }
                }
            })

            moviesPagedListLiveData.observe(this@UpcomingMoviesActivity, Observer { movieItem ->

                pagedMoviesAdapter.submitList(movieItem)
            })
        }
    }
}
