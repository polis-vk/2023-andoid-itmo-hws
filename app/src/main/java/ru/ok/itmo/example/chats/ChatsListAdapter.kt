package ru.ok.itmo.example.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.example.MainViewModel
import ru.ok.itmo.example.R

class ChatsListAdapter(val viewModel: MainViewModel) :
    RecyclerView.Adapter<ChatsListAdapter.ViewHolder>() {

    var data: Array<Chat> = emptyArray()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textViewName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_block, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val channelName = data[position].name
        viewHolder.textView.text = channelName
        viewHolder.itemView.setOnClickListener {
            viewModel.openChannel(channelName)
        }
    }

    override fun getItemCount() = data.size
}
