package br.com.natanaelribeiro.thebestmovies.helpers

import java.util.*

fun Locale.getFormattedLanguage() : String{

    return "$language-$country"
}