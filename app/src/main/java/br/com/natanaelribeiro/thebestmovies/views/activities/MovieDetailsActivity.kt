package br.com.natanaelribeiro.thebestmovies.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.natanaelribeiro.thebestmovies.R
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.helpers.Constants.EXTRA_MOVIE
import br.com.natanaelribeiro.thebestmovies.helpers.Constants.EXTRA_MOVIE_GENRES
import br.com.natanaelribeiro.thebestmovies.helpers.Constants.IMAGE_PATH_BASE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    var movie: MoviesDTO.Movie? = null
    var movieGenres = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        if (intent.hasExtra(EXTRA_MOVIE)) {
            movie = intent.getSerializableExtra(EXTRA_MOVIE) as MoviesDTO.Movie
            movieGenres = intent.getStringExtra(EXTRA_MOVIE_GENRES)
        } else {
            finish()
        }

        prepareUI()
    }

    fun prepareUI() {

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        Picasso.get()
            .load(IMAGE_PATH_BASE_URL + movie?.poster_path)
            .fit()
            .into(ivPoster)

        tvMovieTitle.text = movie?.title
        tvMovieGenres.text = movieGenres
        tvMovieReleaseDate.text = "${getString(R.string.release_date)} ${movie?.release_date}"
        tvMovieOverview.text = movie?.overview
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
