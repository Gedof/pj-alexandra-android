package com.example.projectalexandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_gravar.*

class GravarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gravar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.title = "Gravar"

        btnRec.setScaleType(ImageView.ScaleType.FIT_XY)

        var recording = false
        var timeWhenStopped : Long = 0

        btnRec.setOnClickListener {
            if (!recording) {
                btnRec.setImageResource(R.drawable.ic_pause_white_150dp)
                chronoTimer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                chronoTimer.start()
                recording = true
            } else {
                btnRec.setImageResource(R.drawable.ic_mic_white_150dp)
                timeWhenStopped = chronoTimer.getBase() - SystemClock.elapsedRealtime();
                chronoTimer.stop()
                recording = false
            }

        }

        btnSalvar.setOnClickListener {
            if (recording){
                btnRec.setImageResource(R.drawable.ic_mic_white_150dp)
                timeWhenStopped = chronoTimer.getBase() - SystemClock.elapsedRealtime();
                chronoTimer.stop()
                recording = false
            }

            val intent = Intent(this, NomeActivity::class.java)
            startActivity(intent)
        }



        btnCancelar.setOnClickListener {
            if (recording){
                btnRec.setImageResource(R.drawable.ic_mic_white_150dp)
                timeWhenStopped = chronoTimer.getBase() - SystemClock.elapsedRealtime();
                chronoTimer.stop()
                recording = false
            }

            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true;
    }
}
