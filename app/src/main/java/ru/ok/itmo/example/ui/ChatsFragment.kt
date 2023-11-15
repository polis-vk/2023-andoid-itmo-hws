package ru.ok.itmo.example.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentChatsBinding
import ru.ok.itmo.example.domain.AuthorizationStorage
import ru.ok.itmo.example.domain.ChatsViewModel
import ru.ok.itmo.example.domain.LoginViewModel

class ChatsFragment : Fragment() {
    private val viewModel by viewModels<ChatsViewModel>()
    private lateinit var binding: FragmentChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_chatsFragment_to_startFragment)
            viewModel.logout()
        }
    }
}
