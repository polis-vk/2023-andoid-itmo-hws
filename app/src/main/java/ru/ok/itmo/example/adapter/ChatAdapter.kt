package ru.ok.itmo.example.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import ru.ok.itmo.example.R
import ru.ok.itmo.example.data.Message

import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val username: String,
    private val messages: List<Message>
) : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private val sent = 1
    private val got = 2

    class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText = view.findViewById<TextView>(R.id.textMessage)
        private val time = view.findViewById<TextView>(R.id.time)

        @SuppressLint("SimpleDateFormat")
        fun bind(message: Message) {
            messageText.text = message.data.text?.text ?: "image"
            time.text = SimpleDateFormat("hh:mm").format(Date(message.time!!))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatHolder {
        val holder = when (viewType) {
            sent  -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_container_send_message, viewGroup, false)
            else  -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_container_received_message, viewGroup, false)
        }

        return ChatHolder(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].to == username) {
            got
        } else {
            sent
        }
    }
    override fun onBindViewHolder(holder: ChatHolder, position: Int) = holder.bind(messages[position])

    override fun getItemCount(): Int = messages.size
}