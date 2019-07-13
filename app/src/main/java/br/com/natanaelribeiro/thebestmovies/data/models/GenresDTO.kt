package br.com.natanaelribeiro.thebestmovies.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GenresDTO(
    @SerializedName("genres") val genres: List<Genre>
) : Serializable {

    inner class Genre(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
    ) : Serializable
}