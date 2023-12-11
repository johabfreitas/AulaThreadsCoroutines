package br.com.johabfreitas.aulathreadscoroutines.model

data class FilmeRespota(
    val page: Int,
    val results: List<Filme>,
    val total_pages: Int,
    val total_results: Int
)