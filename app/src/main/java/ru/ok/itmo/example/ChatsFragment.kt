package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

class ChatsFragment : Fragment(R.layout.fragment_chats) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FragmentContainerView>(R.id.action_bar_container)
            .getFragment<SimpleActionBar>().title = getString(R.string.action_bar_chats)
    }
}