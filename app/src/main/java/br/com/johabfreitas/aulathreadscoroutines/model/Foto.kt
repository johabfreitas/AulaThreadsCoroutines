package br.com.johabfreitas.aulathreadscoroutines.model

data class Foto(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)