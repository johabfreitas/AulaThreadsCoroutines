package br.com.johabfreitas.aulathreadscoroutines.api

import br.com.johabfreitas.aulathreadscoroutines.model.FilmeDetalhes
import br.com.johabfreitas.aulathreadscoroutines.model.FilmeRespota
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmeAPI {

    @GET("movie/popular?api_key=${RetrofitHelper.API_KEY}")
    suspend fun recuperarFilmesPopulares(): Response<FilmeRespota>

    @GET("movie/{idFilme}?api_key=${RetrofitHelper.API_KEY}")
    suspend fun recuperarDetalhesFilme(
        @Path("idFilme")idFilme: Int
    ): Response<FilmeDetalhes>
}