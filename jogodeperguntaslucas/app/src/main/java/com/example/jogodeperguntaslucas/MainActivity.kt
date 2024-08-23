package com.example.jogodeperguntaslucas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //cria uma função para os botões da tela inicial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cria duas variaveis para os botoes da tela inicial
        val jogoButton: Button = findViewById(R.id.jogo)
        val sairButton: Button = findViewById(R.id.sair)

        //inicia o jogo quando aperta no botão "Começar"
        jogoButton.setOnClickListener {
            //redireciona para a tela de jogo
            val intent = Intent(this, jogo::class.java)
            intent.putExtra("modoJogo", "jogo")
            startActivity(intent)
        }

        //fecha o aplicativo quando aperta no botão "Sair"
        sairButton.setOnClickListener {
            finishAffinity()
        }
    }
}
