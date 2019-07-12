package br.com.natanaelribeiro.thebestmovies.data.enums

enum class PagingNetworkState(val stateType: String) {
    FAILED("failed"),
    LOADING("loading"),
    SUCCESS("success"),
    SUCCESS_WITH_ITEMS("success_with_items"),
    SUCCESS_WITHOUT_ITEMS("success_without_items")
}