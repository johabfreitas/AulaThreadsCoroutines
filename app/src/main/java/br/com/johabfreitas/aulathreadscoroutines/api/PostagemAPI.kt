package br.com.johabfreitas.aulathreadscoroutines.api

import br.com.johabfreitas.aulathreadscoroutines.model.Comentario
import br.com.johabfreitas.aulathreadscoroutines.model.Postagem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostagemAPI {

    @GET("posts")
    suspend fun recuperarPostagens(): Response<List<Postagem>>

    @GET("posts/{id}")
    suspend fun recuperarPostagemUnica(
        @Path("id") id: Int
    ): Response<Postagem>

    @GET("posts/{id}/comments")
    suspend fun recuperarComentariosPostagem(
        @Path("id") id: Int
    ): Response<List<Comentario>>

    @GET("comments")
    suspend fun recuperarComentariosPostagemQuery(
        @Query("postId") id: Int
    ): Response<List<Comentario>>

    @POST("posts")
    suspend fun salvarPostagem(
        @Body postagem: Postagem
    ): Response<Postagem>

    @FormUrlEncoded
    @POST("posts")
    suspend fun salvarPostagemFormulario(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<Postagem>
}