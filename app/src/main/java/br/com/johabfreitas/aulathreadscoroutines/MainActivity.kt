package br.com.johabfreitas.aulathreadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.johabfreitas.aulathreadscoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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

            //MinhaThread().start()
            //Thread(MinhaRunnable()).start()
            /*Thread{
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
            }.start()*/
            /*CoroutineScope (Dispatchers.Main).launch{
                binding.btnIniciar.text = "Executou"
            }*/



            job = CoroutineScope(Dispatchers.IO).launch{
                /*repeat(15){indice->
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")
                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    }
                    delay(1000)
                }*/
                /*withTimeout(7000L){
                    executar()
                }*/
                val tempo = measureTimeMillis {

                    /*var resultado1 = tarefa1()
                    var resultado2 = tarefa2()*/

                    val resultado1 = async {tarefa1() }
                    val resultado2 = async {tarefa2() }

                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "${resultado1.await()}"
                        binding.btnParar.text = "${resultado2.await()}"
                    }

                    /*job1.join()
                    job2.join()*/

                    Log.i("info_coroutine", "resultado1:${resultado1.await()}")
                    Log.i("info_coroutine", "resultado2:${resultado2.await()}")

                }
                Log.i("info_coroutine", "Tempo:$tempo")

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