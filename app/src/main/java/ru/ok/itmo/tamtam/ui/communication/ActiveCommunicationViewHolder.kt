package ru.ok.itmo.tamtam.ui.communication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.tamtam.R
import ru.ok.itmo.tamtam.domain.model.CommunicationViewModel

class ActiveCommunicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val authorView: TextView = itemView.findViewById(R.id.message_authtor)
    private val textView: TextView = itemView.findViewById(R.id.message_text)
    private val timeView: TextView = itemView.findViewById(R.id.message_time)

    fun bind(item: CommunicationViewModel.MessageInfo) {
        authorView.text = item.author
        textView.text = item.text
        timeView.text = item.time
    }
}
