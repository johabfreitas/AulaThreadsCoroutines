package br.com.johabfreitas.aulathreadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.johabfreitas.aulathreadscoroutines.api.EnderecoAPI
import br.com.johabfreitas.aulathreadscoroutines.api.PostagemAPI
import br.com.johabfreitas.aulathreadscoroutines.api.RetrofitHelper
import br.com.johabfreitas.aulathreadscoroutines.databinding.ActivityMainBinding
import br.com.johabfreitas.aulathreadscoroutines.model.Comentario
import br.com.johabfreitas.aulathreadscoroutines.model.Endereco
import br.com.johabfreitas.aulathreadscoroutines.model.Foto
import br.com.johabfreitas.aulathreadscoroutines.model.Postagem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }

    private var pararThread = false
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAbrir.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
        }

        binding.btnParar.setOnClickListener {
            //pararThread = true
            job?.cancel()
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true
        }

        binding.btnIniciar.setOnClickListener{
                /*

            //MinhaThread().start()
            //Thread(MinhaRunnable()).start()
Thread{
                repeat(30){indice ->
                    Log.i("info_thread", "MinhaThread: $indice T: ${Thread.currentThread().name}")
                    runOnUiThread{
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                        binding.btnIniciar.isEnabled = false
                        if(indice == 29){
                            binding.btnIniciar.text = "Reiniciar execução"
                            binding.btnIniciar.isEnabled = true
                        }
                    }
                    Thread.sleep(1000)
                }
            }.start()

CoroutineScope (Dispatchers.Main).launch{
                binding.btnIniciar.text = "Executou"
            }




            job = CoroutineScope(Dispatchers.IO).launch{
repeat(15){indice->
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")
                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    }
                    delay(1000)
                }

withTimeout(7000L){
                    executar()
                }

                val tempo = measureTimeMillis {

var resultado1 = tarefa1()
                    var resultado2 = tarefa2()


                    val resultado1 = async {tarefa1() }
                    val resultado2 = async {tarefa2() }

                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "${resultado1.await()}"
                        binding.btnParar.text = "${resultado2.await()}"
                    }

job1.join()
                    job2.join()


                    Log.i("info_coroutine", "resultado1:${resultado1.await()}")
                    Log.i("info_coroutine", "resultado2:${resultado2.await()}")


                }
                Log.i("info_coroutine", "Tempo:$tempo")

            }
*/
            CoroutineScope(Dispatchers.IO).launch {
                //recuperarEndereco()
                //recuperarPostagens()
                //recuperarPostagemUnica()
                //recuperarComentariosPostagens()
                //salvarPostagem()
                //atualizarPostagem()
                //atualizarPostagemPatch()
                //removerPostagem()
                recuperarFotoUnica()
            }
        }
    }

    private suspend fun recuperarFotoUnica() {

        var retorno: Response<Foto>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.recuperarFoto(5)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val foto = retorno.body()
                val resultado = "[${retorno.code()}] - ${foto?.id} - ${foto?.url}"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                    Picasso.get()
                        //.load(foto?.url)
                        .load(R.drawable.picasso)
                        .resize(100, 200)
                        //.centerInside()
                        //.centerCrop(200)
                        .placeholder(R.drawable.carregando)
                        //.error(R.drawable.picasso)
                        .into(binding.imageFoto)
                }

                Log.i("info_jsonplace", resultado)

            }else {
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun removerPostagem() {

        var retorno: Response<Unit>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.removerPostagem(1)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){

                var resultado = "[${retorno.code()}] sucesso ao remover postagem"

                Log.i("info_jsonplace", "R:$retorno")

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

            } else {
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun atualizarPostagemPatch() {

        var retorno: Response<Postagem>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.atualizarPostagemPatch(
                1,
                Postagem("Corpo da postagem", -1, null, 1090 )

            )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val idUsuario = postagem?.userId
                val corpo = postagem?.description

                var resultado = "[${retorno.code()}] ID:$id - T:$titulo - C:$corpo - U:$idUsuario"

                Log.i("info_jsonplace", "id:$id - T:$titulo - U:$idUsuario")

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

            } else {
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun atualizarPostagem() {

        var retorno: Response<Postagem>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.atualizarPostagemPut(
                1,
                Postagem("Corpo da postagem", -1, "Titulo", 1090 )

            )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val idUsuario = postagem?.userId
                val corpo = postagem?.description

                var resultado = "[${retorno.code()}] ID:$id - T:$titulo - C:$corpo - U:$idUsuario"

                Log.i("info_jsonplace", "id:$id - T:$titulo - U:$idUsuario")

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

            } else {
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun salvarPostagem() {

        var retorno: Response<Postagem>? = null

        val postagem = Postagem(
            "Corpo da postagem",
            -1,
            "Titulo da postagem",
            1090
        )

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            //retorno = postagemAPI.salvarPostagem(postagem)
            retorno = postagemAPI.salvarPostagemFormulario(
                1090,
                -1,
                "Titulo da postagem",
                "Corpo da postagem"
            )
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val postagem = retorno.body()

                val id = postagem?.id
                val titulo = postagem?.title
                val idUsuario = postagem?.userId
                var resultado = "[${retorno.code()}] id:$id - T:$titulo - U:$idUsuario"

                Log.i("info_jsonplace", "id:$id - T:$titulo - U:$idUsuario")

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

            } else {
                withContext(Dispatchers.Main){
                    binding.textResultado.text = "ERRO CODE:${retorno.code()}"
                }
            }
        }
    }

    private suspend fun recuperarComentariosPostagens() {

        var retorno: Response<List<Comentario>>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            //retorno = postagemAPI.recuperarComentariosPostagem(1) //Path
            retorno = postagemAPI.recuperarComentariosPostagemQuery(1) //Query
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val listaComentario = retorno.body()

                var resultado = ""
                listaComentario?.forEach {comentario ->
                    val idComentario = comentario.id
                    val email = comentario.email
                    val comentarioResultado = "$idComentario + $email \n"
                    resultado += comentarioResultado

                    Log.i("info_jsonplace", "$resultado")
                }

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

            }
        }
    }

    private suspend fun recuperarPostagemUnica() {

        var retorno: Response<Postagem>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.recuperarPostagemUnica(87)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val postagem = retorno.body()
                val resultado = "${postagem?.id} - ${postagem?.title}"

                withContext(Dispatchers.Main){
                    binding.textResultado.text = resultado
                }

                Log.i("info_jsonplace", resultado)

            }
        }
    }

    private suspend fun recuperarPostagens() {

        var retorno: Response<List<Postagem>>? = null

        try {
            val postagemAPI= retrofit.create(PostagemAPI::class.java)
            retorno = postagemAPI.recuperarPostagens()
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_jsonplace", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val listaPostagens = retorno.body()
                listaPostagens?.forEach {postagem ->
                    val id = postagem.id
                    val title = postagem.title
                    Log.i("info_jsonplace", "$id - $title")
                }

            }
        }
    }

    private suspend fun recuperarEndereco(){

        var retorno: Response<Endereco>? = null
        val cepDigitadoUsuario = "59300000"

        try {
            val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
            retorno = enderecoAPI.recuperarEndereco(cepDigitadoUsuario)
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_endereco", "Erro ao recuperar")
        }

        if(retorno != null){

            if(retorno.isSuccessful){
                val endereco = retorno.body()
                val rua = endereco?.logradouro
                val cidade = endereco?.localidade
                val cep = endereco?.cep
                Log.i("info_endereco", "Endereco: $rua, $cidade, $cep")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    //Minhas Classes

    private suspend fun tarefa1(): String{
        repeat(3){indice->
            Log.i("info_coroutine", "Tarefa1: ${indice + 1} T: ${Thread.currentThread().name}")
            delay(1000L)
        }
        return "Executou tarefa 1"
    }

    private suspend fun tarefa2(): String{
        repeat(3){indice->
            Log.i("info_coroutine", "Tarefa2: ${indice + 1} T: ${Thread.currentThread().name}")
            delay(1000L)
        }
        return "Executou tarefa 2"
    }

    private suspend fun executar(){
        repeat(15){indice->
            Log.i("info_coroutine", "Executando: ${indice + 1} T: ${Thread.currentThread().name}")

            withContext(Dispatchers.Main){
                binding.btnIniciar.text = "Executando: ${indice + 1} T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false
            }
            delay(1000L)
        }
    }

    private suspend fun dadosUsuario(){
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine", "Usuario: ${usuario.nome} T: ${Thread.currentThread().name}")
        val postagens = recuperarPostagensPeloId(usuario.id)
        Log.i("info_coroutine", "Usuario: ${postagens.size} T: ${Thread.currentThread().name}")
    }

    private suspend fun recuperarPostagensPeloId(idUsuario: Int) : List<String>{
        delay(2000)
        return listOf(
            "Viagem Nordeste",
            "Estudando Kotlin para Android",
            "Lanchando"
        )
    }

    private suspend fun recuperarUsuarioLogado() : Usuario{
            delay(2000)
            return Usuario(1020, "Johab")
    }

    inner class MinhaRunnable : Runnable {
        override fun run() {
            repeat(30){indice ->

                if(pararThread) {
                    pararThread = false
                    return
                }

                Log.i("info_thread", "MinhaThread: ${indice + 1} T: ${Thread.currentThread().name}")
                runOnUiThread{
                    binding.btnIniciar.text = "Executando: ${indice + 1} T: ${Thread.currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice == 29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)
            }
        }
    }

    inner class MinhaThread : Thread(){

        override fun run() {
            super.run()
            repeat(30){indice ->
                Log.i("info_thread", "MinhaThread: $indice T: ${currentThread().name}")
                runOnUiThread{
                    binding.btnIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if(indice == 29){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                sleep(1000)
            }
        }
    }
}