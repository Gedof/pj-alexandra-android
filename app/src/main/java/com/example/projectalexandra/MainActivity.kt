package com.example.projectalexandra

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var edtSearch : EditText
    private var mp : MediaPlayer? = null
    private var isPlaying = false
    private var totalTimeMp : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemAudio = intent.getSerializableExtra("itemAudio") as? ItemAudio

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.title = itemAudio?.nome

        mp = MediaPlayer.create(this,R.raw.audio_1)
        totalTimeMp = mp!!.duration


        seekBar = findViewById(R.id.seekBar)
        edtSearch = findViewById(R.id.edtSearch)
        var searchResults : MutableList<IdxWordView> = mutableListOf()
        var currentResult = 0



        var totalTime = 0

        totalTime = itemAudio!!.idxWords!!.last().timestamp + itemAudio!!.idxWords!!.last().duration

        if (totalTime < totalTimeMp) totalTime = totalTimeMp


        //itemAudio?.idxWords?.forEach { w ->
        //    totalTime += w.duration
        //}

        var words = itemAudio?.idxWords as List<IndexedWord>

        seekBar.max = totalTime
        seekBar.progress = 0

        txtEndTime.text = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(totalTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()))
        )

        flexbox.removeAllViews()

        words = words.map {w ->
            var wordStrings = w.word.split(" ");

            var wordIdxs = wordStrings.map {ws ->
                IndexedWord("${ws.replace(" ","")} ", w.timestamp, w.duration)
            }

            wordIdxs
        }.flatten()

        words.forEach{w ->
            var txtV = IdxWordView(this)
            txtV.text = w.word
            txtV.layoutParams = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT)
            txtV.textSize = 18f

            txtV.isClickable = true

            txtV.timestamp = w.timestamp
            txtV.duration = w.duration

            txtV.setOnClickListener{movePlayer(w.timestamp)}

            flexbox.addView(txtV)
        }

        var nextCheck:Long = 0
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    if (mp!!.duration >= i) {
                        mp?.seekTo(i)
                    } else {
                        mp?.seekTo(mp!!.duration)
                        mp?.pause()
                        btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                        isPlaying = false
                    }
                }

                if (true || nextCheck <= System.currentTimeMillis()) {

                    txtTime.text = String.format(
                        "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(i.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(i.toLong()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(i.toLong()))
                    )

                    var currentTexts: MutableList<IdxWordView> = mutableListOf()

                    for (x in 0 until (flexbox as ViewGroup).childCount) {
                        var child = (flexbox as ViewGroup).getChildAt(x)
                        if (child is IdxWordView) {
                            child.setBackgroundColor(Color.TRANSPARENT)
                            if (i >= child.timestamp && i < (child.timestamp + child.duration)) {
                                //currentTexts.add(child)
                                child.setBackgroundColor(Color.YELLOW)
                                child.parent.requestChildFocus(child, child)
                            }
                        }
                    }

                    //nextCheck = System.currentTimeMillis() + 200
                    //currentTexts.forEach{ct ->

                    //}
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        edtSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                searchResults.clear()
                currentResult = 0

                for(x in 0 until (flexbox as ViewGroup).childCount){
                    var child = (flexbox as ViewGroup).getChildAt(x)
                    if (child is IdxWordView){
                        child.setBackgroundColor(Color.TRANSPARENT)
                        if(!s.isNullOrBlank() && child.text.contains(s,true)){
                            searchResults.add(child)
                        }
                    }
                }

                if (searchResults.size > 0){

                    searchResults[0].setBackgroundColor(Color.YELLOW)
                    searchResults[0].parent.requestChildFocus(searchResults[0],searchResults[0])
                    searchResults[0].performClick()

                    currentResult = 1

                    txtResult.text = "1 de " + searchResults.size
                }

                if(s.isNullOrBlank()){
                    searchResults.clear()
                    currentResult = 0
                    txtResult.text = "0 de 0"
                }
            }
        })

        btnNext.setOnClickListener {
            if (currentResult < searchResults.size){
                currentResult++
                var index = currentResult-1

                searchResults[index].setBackgroundColor(Color.YELLOW)
                searchResults[index].parent.requestChildFocus(searchResults[index],searchResults[index])
                searchResults[index].performClick()

                txtResult.text = "" + currentResult + " de " + searchResults.size
            }
        }

        btnPrev.setOnClickListener {
            if (searchResults.size > 1 && currentResult > 1){
                currentResult--
                var index = currentResult-1

                searchResults[index].setBackgroundColor(Color.YELLOW)
                searchResults[index].parent.requestChildFocus(searchResults[index],searchResults[index])
                searchResults[index].performClick()

                txtResult.text = "" + currentResult + " de " + searchResults.size
            }
        }

        //----

        btnPlay.setOnClickListener {
            if (!isPlaying || !mp!!.isPlaying){
                if(seekBar.progress <= mp!!.duration) {
                    mp?.start()
                    btnPlay.setImageResource(R.drawable.ic_pause_black_24dp)
                    isPlaying = true
                }
            }else{
                mp?.pause()
                btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                isPlaying = false
            }
        }

        mp?.setOnCompletionListener {
            mp?.pause()
            btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            isPlaying = false
        }

        Thread(Runnable {
            while (mp != null){
                try {
                    if (mp!!.isPlaying){
                        var msg = Message()
                        msg.what = mp!!.currentPosition
                        handler.sendMessage(msg)
                    }
                    Thread.sleep(100)
                } catch (e : InterruptedException){}
            }
        }).start()
    }

    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            seekBar.progress = currentPosition
        }
    }

    fun movePlayer(timestamp: Int){
        if (mp!!.duration >= timestamp) {
            mp?.seekTo(timestamp)
        } else {
            mp?.seekTo(mp!!.duration)
            mp?.pause()
            btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            isPlaying = false
        }
        seekBar.progress = timestamp
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                mp?.release()
                mp = null
                finish()
            }
        }
        return true;
    }

    override fun onStop(){
        mp?.release()
        mp = null
        super.onStop()
    }
}
