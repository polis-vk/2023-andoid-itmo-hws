package ru.ok.itmo.tamtam.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.tamtam.R
import ru.ok.itmo.tamtam.domain.model.ChatsViewModel
import ru.ok.itmo.tamtam.dto.ChannelName

class ChatsAdapter(
    private val items: List<ChatsViewModel.ChatInfo>,
    private val byTapCellCompletion: (channelName: ChannelName) -> Unit
) : RecyclerView.Adapter<ChatsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_chats,
            parent,
            false
        )

        view.setOnClickListener {
            byTapCellCompletion(items[0].channelName)
        }

        return ChatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(items[position], byTapCellCompletion)
    }

}
