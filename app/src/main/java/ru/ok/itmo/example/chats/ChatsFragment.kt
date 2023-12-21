package ru.ok.itmo.example.chats

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ok.itmo.example.R
import ru.ok.itmo.example.chats.retrofit.models.ChannelId
import ru.ok.itmo.example.chats.retrofit.models.Message
import ru.ok.itmo.example.custom_view.AvatarCustomView
import java.util.Arrays

@AndroidEntryPoint
class ChatsFragment : Fragment() {
    companion object {
        const val TAG = "CHATS_FRAGMENT"
    }

    private val chatsViewModel by viewModels<ChatsViewModel>()
    private lateinit var channels: List<ChannelId>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, chatsViewModel.getCurrentUser())

        chatsViewModel.channels.onEach {
            if (it is ChannelsState.Success) {
                Log.d(TAG, it.channels.toTypedArray().contentDeepToString())
                channels = it.channels
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        chatsViewModel.getUserChannels()
    }

    private fun getInitials(name: String): String {
        if (name.isBlank()) return ""

        val cur = name.split(" ")
        if (cur.size == 1) {
            return if (name[0] != '@') name[0].toString() else ""
        }
        return "${cur[0][0]}${cur[0][1]}"
    }
}