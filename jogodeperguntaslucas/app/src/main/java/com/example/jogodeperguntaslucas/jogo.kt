package com.example.jogodeperguntaslucas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class jogo : AppCompatActivity() {

    //classe de dados que armazena as perguntas do jogo
    data class pergunta(
        val text: String,
        val options: List<String>,
        val correctAnswerIndex: Int
    )

    //lista das perguntas do jogo
    val perguntas = listOf(
        pergunta(
            text = "Qual animal é conhecido como o Rei das Selvas?",
            options = listOf("Tigre", "Leão", "Elefante", "Macaco"),
            correctAnswerIndex = 1
        ),
        pergunta(
            text = "Qual a capital do Estado do Rio de Janeiro",
            options = listOf("Rio de Janeiro", "Niterói", "Macaé", "São Paulo"),
            correctAnswerIndex = 0
        ),
        pergunta(
            text = "Quanto é 10+10?",
            options = listOf("20", "1010", "11", "30"),
            correctAnswerIndex = 0
        ),
        pergunta(
            text = "Qual o nome da estrela que ilumina nosso planeta?",
            options = listOf("Sol", "Lua", "Marte", "Vênus"),
            correctAnswerIndex = 0
        ),
        pergunta(
            text = "Qual é a capital do Brasil",
            options = listOf("São Paulo", "Distrito Federal", "Salvador", "Rio de Janeiro"),
            correctAnswerIndex = 1
        )
    )

    //variaveis usadas no activity_main.xml
    private lateinit var questionTextView: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitAnswerButton: Button

    //seleciona perguntas aleatoriamente
    private lateinit var selectedQuestions: List<pergunta>
    //variaveis para iniciar o jogo
    private var currentQuestionIndex = 0
    private var score = 0

    //inicializa o jogo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jogo)

        //mostra o texto das perguntas, as opções e o botão de ir para a próxima pergunta
        questionTextView = findViewById(R.id.questionTextView)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        submitAnswerButton = findViewById(R.id.submitAnswerButton)

        //seleciona 5 perguntas da lista de forma aleatoria
        selectedQuestions = perguntas.shuffled().take(5)

        //mostra a pergunta
        displayQuestion()

        //chama a função verificaResposta após o jogador apertar no botão de ir para a próxima pergunta
        submitAnswerButton.setOnClickListener {
            verificarResposta()
        }
    }

    //exibe a pergunta e tira a pergunta anterior para aparecer a nova
    private fun displayQuestion() {
        if (currentQuestionIndex < selectedQuestions.size) {
            val question = selectedQuestions[currentQuestionIndex]
            questionTextView.text = question.text

            optionsRadioGroup.removeAllViews()

            question.options.forEachIndexed { index, option ->
                val radioButton = RadioButton(this)
                radioButton.text = option
                radioButton.id = index
                optionsRadioGroup.addView(radioButton)
            }
        } else {
            //mostra quantas perguntas o jogador conseguiu acertar no final.
            mostrarPontuacaoFinal()
        }
    }

    //verifica a resposta selecionada e adiciona a pontuação caso a resposta esteja correta.
    private fun verificarResposta() {
        val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val question = selectedQuestions[currentQuestionIndex]
            val isCorrect = selectedOptionId == question.correctAnswerIndex

            if (isCorrect) {
                score++
                //mostra um texto dizendo que a resposta está correta
                Toast.makeText(this, "Resposta Correta!", Toast.LENGTH_SHORT).show()
            } else {
                //mostra um texto dizendo que a resposta está errada e mostra qual era a resposta correta
                val correctAnswer = question.options[question.correctAnswerIndex]
                Toast.makeText(this, "Resposta Errada! A resposta correta era: $correctAnswer", Toast.LENGTH_LONG).show()
            }

            //mostra a próxima pergunta
            currentQuestionIndex++
            if (currentQuestionIndex < selectedQuestions.size) {
                submitAnswerButton.postDelayed({
                    displayQuestion()
                }, 1000)
            } else {
                mostrarPontuacaoFinal()
            }

            return
        } else {
            //avisa ao jogador para selecionar uma opção caso nenhuma tenha sido selecionada
            Toast.makeText(this, "Por favor, selecione uma resposta", Toast.LENGTH_SHORT).show()
        }
    }

    //mostra uma caixa de aviso com a pontuação final.
    private fun mostrarPontuacaoFinal() {
        AlertDialog.Builder(this)
            .setTitle("Fim de Jogo")
            .setMessage("Você marcou $score de ${selectedQuestions.size}")
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }
}
