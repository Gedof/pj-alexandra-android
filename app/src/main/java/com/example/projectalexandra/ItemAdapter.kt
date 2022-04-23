package com.example.projectalexandra

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_nome.*
import kotlinx.android.synthetic.main.list_item_item.view.*

class ItemAdapter(private val context: Context, private val dataSource: ArrayList<ItemAudio>) : BaseAdapter(), ListAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null){
            var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item_item, parent, false)
        }

        val itemAudio = getItem(position) as ItemAudio

        view!!.txtNome.text = itemAudio.nome

        view!!.txtNome.setOnClickListener {
            val it = Intent(context,MainActivity::class.java)
            it.putExtra("itemAudio",itemAudio)
            context.startActivity(it)
        }

        view!!.btnExcluir.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setMessage("Tem certeza que deseja excluir ${itemAudio.nome}?")
            builder.setPositiveButton("Sim"){dialog, which -> Toast.makeText(context, "${itemAudio.nome} excluído.", Toast.LENGTH_SHORT).show()}
            //builder.setNeutralButton("Cancelar") { dialog, which -> }
            builder.setNegativeButton("Não") { dialog, which -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return view!!
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}