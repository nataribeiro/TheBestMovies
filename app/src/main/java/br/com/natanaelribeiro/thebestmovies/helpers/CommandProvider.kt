package br.com.natanaelribeiro.thebestmovies.helpers

interface CommandProvider {

    fun getCommand(): SingleLiveEvent<GenericCommand>
}