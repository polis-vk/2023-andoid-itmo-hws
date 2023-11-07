package ru.ok.itmo.tamtam.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.itmo.tamtam.App
import ru.ok.itmo.tamtam.data.AuthRepository
import ru.ok.itmo.tamtam.databinding.FragmentChatsBinding
import ru.ok.itmo.tamtam.utils.FragmentWithBinding
import ru.ok.itmo.tamtam.utils.OnBackPressed
import javax.inject.Inject

class ChatsFragment : FragmentWithBinding<FragmentChatsBinding>(FragmentChatsBinding::inflate) {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnBackPressedCallback()
    }

    private fun setupOnBackPressedCallback() {
        (requireActivity() as? OnBackPressed)?.addCustomOnBackPressed(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                authRepository.logout()
            }
        }
    }
}