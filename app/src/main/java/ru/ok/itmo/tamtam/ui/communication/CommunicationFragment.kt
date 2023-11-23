package ru.ok.itmo.tamtam.ui.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ok.itmo.tamtam.R
import ru.ok.itmo.tamtam.databinding.FragmentCommunicationBinding
import ru.ok.itmo.tamtam.domain.CommunicationStorage
import ru.ok.itmo.tamtam.domain.model.CommunicationViewModel
import ru.ok.itmo.tamtam.domain.state.ChatsState
import ru.ok.itmo.tamtam.domain.state.CommunicationState
import ru.ok.itmo.tamtam.ui.chats.ChatsAdapter
import ru.ok.itmo.tamtam.util.ErrorPresenter
import ru.ok.itmo.tamtam.util.TextPresentObjects

class CommunicationFragment : Fragment() {
    private val viewModel by viewModels<CommunicationViewModel>()
    private lateinit var binding: FragmentCommunicationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentCommunicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMessages()
        setupUI()

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_communicationFragment_to_chatsFragment)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

        viewModel.chatsState.observe(viewLifecycleOwner) {
            when (val result = it) {
                is CommunicationState.Success -> {
                    shorOrHideError(null)
                    if (result.messages.isEmpty()) {
                        binding.communicationInfoText.isVisible = true
                        binding.communicationInfoText.text = TextPresentObjects.noneChats
                    } else {
                        binding.communicationInfoText.isInvisible = true
                        binding.recyclerView.adapter = CommunicationAdapter(result.messages)
                    }
                    binding.communicationLoadingPanel.isInvisible = true
                }

                is CommunicationState.LoadingMessages -> {
                    shorOrHideError(null)
                    binding.communicationInfoText.isInvisible = true
                    binding.communicationLoadingPanel.isVisible = true
                }

                is CommunicationState.Failure -> {
                    binding.communicationLoadingPanel.isInvisible = true
                    binding.communicationInfoText.isInvisible = true
                    shorOrHideError(result.throwable)
                }

                else -> {}
            }
        }
    }

    private fun shorOrHideError(error: Throwable?) {
        ErrorPresenter.present(error, binding.communicationErrorText)
    }

    private fun setupUI() {
        binding.toolbar.title = CommunicationStorage.channelName
    }
}
