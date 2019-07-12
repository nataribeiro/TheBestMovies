package br.com.natanaelribeiro.thebestmovies.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.natanaelribeiro.thebestmovies.R
import br.com.natanaelribeiro.thebestmovies.data.enums.PagingNetworkState
import br.com.natanaelribeiro.thebestmovies.data.models.MoviesDTO
import br.com.natanaelribeiro.thebestmovies.viewmodels.UpcomingMoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

class PagedMoviesAdapter(private val upcomingMoviesViewModel: UpcomingMoviesViewModel)
    : PagedListAdapter<MoviesDTO.Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val IMAGE_PATH_BASE_URL = "https://image.tmdb.org/t/p/w500"

    private val LOADING_ITEM_TYPE = 0
    private val CONTENT_ITEM_TYPE = 1

    private var pagingNetworkState: PagingNetworkState? = null

    fun setPagingNetworkState(newPagingNetworkState: PagingNetworkState) {

        val previousState = pagingNetworkState

        val previousExtraRowStatus = hasExtraRow()

        pagingNetworkState = newPagingNetworkState

        val newExtraRowStatus = hasExtraRow()

        if (previousExtraRowStatus != newExtraRowStatus) {

            if (previousExtraRowStatus) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRowStatus && previousState != newPagingNetworkState) {

            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {

            CONTENT_ITEM_TYPE -> {

                return ContentItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_movie,
                            parent,
                            false
                        )
                )
            }

            else -> {

                return LoadingItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_loading,
                            parent,
                            false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ContentItemViewHolder) {

            val movie = getItem(position)

            movie?.let { mov ->

                Picasso.get()
                    .load(IMAGE_PATH_BASE_URL + mov.poster_path)
                    .fit()
                    .into(holder.ivMovie)

                holder.tvMovieTitle.text = mov.title
                holder.tvMovieGenres.text = upcomingMoviesViewModel.getMovieGenres(mov.genre_ids)
                holder.tvMovieReleaseDate.text = mov.release_date

                holder.bind(holder.itemView, movie)
            }
        } else if (holder is LoadingItemViewHolder) {

            if (pagingNetworkState != null && (
                        pagingNetworkState == PagingNetworkState.LOADING ||
                                pagingNetworkState == PagingNetworkState.FAILED)) {

                holder.itemProgressBar.visibility = View.VISIBLE
            } else {

                holder.itemProgressBar.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (hasExtraRow() && (position == (itemCount - 1))) return LOADING_ITEM_TYPE

        return CONTENT_ITEM_TYPE
    }

    private fun hasExtraRow() =
        pagingNetworkState != null &&
                pagingNetworkState != PagingNetworkState.SUCCESS

    inner class ContentItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        val ivMovie: ImageView = view.ivMovie
        val tvMovieTitle: TextView = view.tvMovieTitle
        val tvMovieGenres: TextView = view.tvMovieGenres
        val tvMovieReleaseDate: TextView = view.tvMovieReleaseDate

        fun bind(targetView: View, movie: MoviesDTO.Movie) {

            targetView.setOnClickListener {
                upcomingMoviesViewModel.showItemDetails(movie)
            }
        }
    }

    inner class LoadingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemProgressBar: ProgressBar = view.itemProgressBar
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesDTO.Movie>() {

            override fun areItemsTheSame(
                oldItem: MoviesDTO.Movie,
                newItem: MoviesDTO.Movie
            ): Boolean = oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: MoviesDTO.Movie,
                newItem: MoviesDTO.Movie
            ): Boolean = oldItem == newItem
        }
    }
}