package br.com.johabfreitas.aulathreadscoroutines.model

data class Endereco(
    val cep: String,
    val logradouro: String,
    val complemento: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val ibge: Int,
    //val gia: String,
    val ddd: Int,
    val siafi: Int
)
