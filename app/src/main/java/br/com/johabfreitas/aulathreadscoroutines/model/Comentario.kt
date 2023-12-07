package br.com.johabfreitas.aulathreadscoroutines.model

import com.google.gson.annotations.SerializedName

data class Comentario(

    @SerializedName("body")
    val description: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)