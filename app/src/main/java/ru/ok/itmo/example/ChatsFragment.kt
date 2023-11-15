package ru.ok.itmo.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ru.ok.itmo.example.databinding.FragmentChatsBinding
import ru.ok.itmo.example.databinding.FragmentLoginBinding

class ChatsFragment : Fragment() {
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
        }
    }
}
