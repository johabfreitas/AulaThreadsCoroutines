package br.com.johabfreitas.aulathreadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.johabfreitas.aulathreadscoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAbrir.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
        }

        binding.btnParar.setOnClickListener {
            pararThread = true
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



            CoroutineScope(Dispatchers.IO).launch{
                /*repeat(15){indice->
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")
                    withContext(Dispatchers.Main){
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    }
                    delay(1000)
                }*/
                executar()
            }

        }
    }

    //Minhas Classes

    private suspend fun executar(){
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