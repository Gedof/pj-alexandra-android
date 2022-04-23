package com.example.projectalexandra

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Timestamp
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        seekBar = findViewById(R.id.seekBar)

        var text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae ante a risus pulvinar rutrum. Donec porttitor varius neque, nec egestas quam tempor eget. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam interdum quam ante. Donec nec finibus nisl. Mauris aliquam tempor tempor. Donec suscipit lacus vel neque consequat malesuada. Phasellus ut eros sed tortor euismod aliquet eu et lectus. Nam fermentum, lorem sed facilisis faucibus, lorem massa imperdiet libero, et pellentesque lectus nunc sed felis. Donec justo velit, efficitur eu semper id, malesuada eget sapien. Nulla ornare eu tortor ut elementum. Nunc ipsum dui, lobortis in rutrum eu, ultricies id nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc commodo scelerisque orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae ante a risus pulvinar rutrum. Donec porttitor varius neque, nec egestas quam tempor eget. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam interdum quam ante. Donec nec finibus nisl. Mauris aliquam tempor tempor. Donec suscipit lacus vel neque consequat malesuada. Phasellus ut eros sed tortor euismod aliquet eu et lectus. Nam fermentum, lorem sed facilisis faucibus, lorem massa imperdiet libero, et pellentesque lectus nunc sed felis. Donec justo velit, efficitur eu semper id, malesuada eget sapien. Nulla ornare eu tortor ut elementum. Nunc ipsum dui, lobortis in rutrum eu, ultricies id nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc commodo scelerisque orci."

        var texts = text.split(" ");

        texts = texts.map{t -> "$t " }

        var totalTime = 0

        var words = texts.map{t ->
            var iw = IndexedWord(t, totalTime, 2000)
            totalTime += 2000
            iw
        }

        seekBar.max = totalTime
        seekBar.progress = 0

        txtEndTime.text = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(totalTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime.toLong()))
        )

        flexbox.removeAllViews()

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

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                txtTime.text = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(i.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(i.toLong()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(i.toLong()))
                )

                var currentTexts : MutableList<IdxWordView> = mutableListOf()

                for(x in 0 until (flexbox as ViewGroup).childCount){
                    var child = (flexbox as ViewGroup).getChildAt(x)
                    if (child is IdxWordView){
                        child.setBackgroundColor(Color.TRANSPARENT)
                        if(i >= child.timestamp && i < (child.timestamp + child.duration)){
                            currentTexts.add(child)
                        }
                    }
                }

                currentTexts.forEach{ct ->
                    ct.setBackgroundColor(Color.YELLOW)
                    ct.parent.requestChildFocus(ct,ct)
                }


            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something

            }
        })

    }

    fun movePlayer(timestamp: Int){
        seekBar.progress = timestamp
    }
}
