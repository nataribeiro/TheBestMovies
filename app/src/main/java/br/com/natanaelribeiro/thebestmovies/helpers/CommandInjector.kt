package br.com.natanaelribeiro.thebestmovies.helpers

object CommandInjector : CommandProvider {

    override fun getCommand(): SingleLiveEvent<GenericCommand> = SingleLiveEvent()
}