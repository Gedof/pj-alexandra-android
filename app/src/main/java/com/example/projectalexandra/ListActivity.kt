package com.example.projectalexandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_list2.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list2)

        getSupportActionBar()?.setTitle("Lista de Áudios");

        //var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae ante a risus pulvinar rutrum. Donec porttitor varius neque, nec egestas quam tempor eget. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam interdum quam ante. Donec nec finibus nisl. Mauris aliquam tempor tempor. Donec suscipit lacus vel neque consequat malesuada. Phasellus ut eros sed tortor euismod aliquet eu et lectus. Nam fermentum, lorem sed facilisis faucibus, lorem massa imperdiet libero, et pellentesque lectus nunc sed felis. Donec justo velit, efficitur eu semper id, malesuada eget sapien. Nulla ornare eu tortor ut elementum. Nunc ipsum dui, lobortis in rutrum eu, ultricies id nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc commodo scelerisque orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae ante a risus pulvinar rutrum. Donec porttitor varius neque, nec egestas quam tempor eget. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam interdum quam ante. Donec nec finibus nisl. Mauris aliquam tempor tempor. Donec suscipit lacus vel neque consequat malesuada. Phasellus ut eros sed tortor euismod aliquet eu et lectus. Nam fermentum, lorem sed facilisis faucibus, lorem massa imperdiet libero, et pellentesque lectus nunc sed felis. Donec justo velit, efficitur eu semper id, malesuada eget sapien. Nulla ornare eu tortor ut elementum. Nunc ipsum dui, lobortis in rutrum eu, ultricies id nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc commodo scelerisque orci."

        //var texts = text.split(" ");

        //texts = texts.map{t -> "$t " }

        //var totalTime = 0

        //var words = texts.map{t ->
            //var iw = IndexedWord(t, totalTime, 2000)
            //totalTime += 2000
            //iw
        //}

        var text = arrayListOf<Pair<Int,String>>(
            Pair(2000,	"Nesta interação, "),
            Pair(3000,	"estavam presentes dois alunos "),
            Pair(4000,	"que não sabiam "),
            Pair(6000,	"do que o projeto se trata. "),
            Pair(8000,	"Após 30 segundos de observação "),
            Pair(10000,	"e 8 minutos e 49 segundos "),
            Pair(11000,	"de interação, "),
            Pair(12000,	"o resultado está representado "),
            Pair(13000,	"na Figura 4. "),

            Pair(16000,	"Uma grande parte da discussão "),
            Pair(17000,	"foi relacionada a dúvidas "),
            Pair(18000,	"e confusões a respeito "),
            Pair(19000,	"dos cards de “Menu” "),
            Pair(20000,	"e “Configuração”, "),
            Pair(21000,	"desta forma, "),
            Pair(23000,	"todas as funções do aplicativo "),
            Pair(24000,	"foram colocadas a partir "),
            Pair(26000,	"de algum desses cards. "),

            Pair(27000,	"Eles estavam em dúvida "),
            Pair(29000,	"em qual seria a plataforma, "),
            Pair(31000,	"o que causou confusões a respeito "),
            Pair(32000,	"do objetivo do projeto. "),

            Pair(33000,	"Após a interação, "),
            Pair(35000,	"quando foi mencionada a possibilidade "),
            Pair(37000,	"de ser um aplicativo mobile, "),
            Pair(38000,	"entenderam a proposta muito melhor "),
            Pair(39000,	"sem a necessidade "),
            Pair(40000,	"de muitas explicações. ")
        )


        var previousTime = 0
        var words = text.map {p ->

            var timestamp = previousTime
            var duration = p.first - previousTime
            var idxw = IndexedWord(p.second,timestamp,duration)

            previousTime = timestamp + duration

            idxw
        }

        var audios:ArrayList<ItemAudio> = arrayListOf(
            ItemAudio("Audio1.mp3", words as ArrayList<IndexedWord>),
            ItemAudio("Audio2.mp3", words as ArrayList<IndexedWord>),
            ItemAudio("Audio3.mp3", words as ArrayList<IndexedWord>),
            ItemAudio("Audio4.mp3", words as ArrayList<IndexedWord>)
        )

        list.adapter = ItemAdapter(this, audios)


        btnAddAudio.setOnClickListener {
            val it = Intent(this,AdicionarActivity::class.java)
            startActivity(it)
        }

    }
}
