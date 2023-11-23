package ru.ok.itmo.tamtam.ui.communication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.tamtam.R
import ru.ok.itmo.tamtam.domain.model.CommunicationViewModel

class CommunicationAdapter(
    private val items: List<CommunicationViewModel.MessageInfo>
) : RecyclerView.Adapter<ActiveCommunicationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveCommunicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_message,
            parent,
            false
        )

        return ActiveCommunicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ActiveCommunicationViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
