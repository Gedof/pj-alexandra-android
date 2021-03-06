package com.example.projectalexandra

import java.io.Serializable


class IndexedWord : Serializable {

    var word : String
    var timestamp : Int
    var duration : Int

    constructor(word:String, timestamp:Int, duration:Int){
        this.word = word
        this.timestamp = timestamp
        this.duration = duration
    }

}