package ru.ok.itmo.tamtam.ui.chats

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.tamtam.R
import ru.ok.itmo.tamtam.domain.model.ChatsViewModel
import ru.ok.itmo.tamtam.dto.ChannelName
import ru.ok.itmo.tamtam.ui.custom.AvatarImageView

class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val authorNameView: TextView = itemView.findViewById(R.id.channel_author)
    private val lastMessageView: TextView = itemView.findViewById(R.id.channel_last_message)
    private val timeLastMessageView: TextView = itemView.findViewById(R.id.channel_message_time)
    private val avatarImageView: AvatarImageView = itemView.findViewById(R.id.channel_avatar)

    fun bind(
        item: ChatsViewModel.ChatInfo,
        byTapCellCompletion: (channelName: ChannelName) -> Unit
    ) {
        authorNameView.text = item.author
        lastMessageView.text = item.lastMessage
        timeLastMessageView.text = item.time

        itemView.setOnClickListener {
            byTapCellCompletion(item.channelName)
        }

        avatarImageView.text = item.channelName
    }
}
