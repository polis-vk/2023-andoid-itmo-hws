package ru.ok.itmo.example.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.example.MainViewModel
import ru.ok.itmo.example.R
import ru.ok.itmo.example.chats.Chat
import ru.ok.itmo.example.chats.ChatsListAdapter


class ChatsFragment : Fragment(R.layout.fragment_chats) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.simpleActionBar!!.run {
                title = getString(R.string.action_bar_chats)
                buttonImage = R.drawable.ic_action_bar_chats
            }

        val adapter = ChatsListAdapter(viewModel)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter

        viewModel.channels.observe(viewLifecycleOwner) {
            adapter.data = it.map { Chat(it) }.toTypedArray()
        }

        viewModel.updateChannels { e, time ->
            Log.e("fragment", e.toString())
        }
    }
}