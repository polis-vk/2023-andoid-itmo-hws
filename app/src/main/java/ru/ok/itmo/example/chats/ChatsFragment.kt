package ru.ok.itmo.example.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentChatsBinding

class ChatsFragment : Fragment(R.layout.fragment_chats) {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private val args: ChatsFragmentArgs by navArgs()
    private val viewModel by viewModels<ChatsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.info.text = resources.getString(R.string.success_login, args.token)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.logout(args.token)
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )
    }
}