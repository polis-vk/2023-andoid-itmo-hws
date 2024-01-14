package ru.ok.itmo.example

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ChatAdapter(private val context: Activity, private val data: Array<DataChat>)
    : ArrayAdapter<Any>(context, R.layout.chat_item, data) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val chat_item = inflater.inflate(R.layout.chat_item, null, true)

        chat_item.findViewById<TextView>(R.id.textView).text = data[position].name
        Picasso.get().load(data[position].image).into(chat_item.findViewById<ImageView>(R.id.icon))

        return chat_item
    }
}