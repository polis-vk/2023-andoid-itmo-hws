package ru.ok.itmo.example.chatsPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.ChatCardBinding

class Adapter() : ListAdapter<Channel, Adapter.Holder>(MyDiffUtils()) {

    class MyDiffUtils : DiffUtil.ItemCallback<Channel>(){

        override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        var binding = ChatCardBinding.bind(view)
        fun bind(chat: Channel) = with(binding){
            binding.channelName.text = chat.name
            binding.channelTime.text = chat.lastMessageText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
