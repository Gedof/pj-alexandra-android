package com.example.projectalexandra

import java.io.Serializable

class ItemAudio : Serializable {

    var id:Int? = null
    var nome:String? = null
    var idxWords:ArrayList<IndexedWord>? = null

    internal  constructor(){}

    internal constructor(nome:String){
        this.nome = nome
    }

    internal constructor(nome:String, idxWords:ArrayList<IndexedWord>){
        this.nome = nome
        this.idxWords = idxWords
    }

}