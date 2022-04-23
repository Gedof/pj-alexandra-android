package com.example.projectalexandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_adicionar.*

class AdicionarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.title = "Adicionar Áudio"


        btnGravar.setOnClickListener {
            var it = Intent(this, GravarActivity::class.java)
            startActivity(it)
        }

        btnImportar.setOnClickListener {
            val intent = Intent()
                .setType("audio/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Selecione um áudio"), 111)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file

            Toast.makeText(this, "Adicionado ${selectedFile}", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
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
