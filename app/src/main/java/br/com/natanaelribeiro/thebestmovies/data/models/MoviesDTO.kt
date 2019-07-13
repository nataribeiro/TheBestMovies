package br.com.natanaelribeiro.thebestmovies.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("dates") val dates: Dates,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
) {

    inner class Movie(
        @SerializedName("poster_path") val poster_path: String,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("overview") val overview: String,
        @SerializedName("release_date") val release_date: String,
        @SerializedName("genre_ids") val genre_ids: List<Int>,
        @SerializedName("id") val id: Int,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("original_language") val original_language: String,
        @SerializedName("title") val title: String,
        @SerializedName("backdrop_path") val backdrop_path: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("vote_count") val vote_count: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val vote_average: Double
    ) : Serializable

    inner class Dates(
        @SerializedName("maximum") val maximum: String,
        @SerializedName("minimum") val minimum: String
    )
}