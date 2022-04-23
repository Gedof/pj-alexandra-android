package com.example.projectalexandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nome.*
import kotlinx.android.synthetic.main.list_item_item.*

class NomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nome)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.title = "Salvar Gravação"



        btnSalvar.setOnClickListener {

            if(!edtNome.text.isNullOrBlank()) {
                Toast.makeText(this, "Adicionado ${edtNome.text}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Digite um nome.", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
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
