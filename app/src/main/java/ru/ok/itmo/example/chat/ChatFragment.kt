package ru.ok.itmo.example.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ok.itmo.example.R
import ru.ok.itmo.example.chatsPackage.Adapter
import ru.ok.itmo.example.chatsPackage.Channel
import ru.ok.itmo.example.chatsPackage.ChatUseCase
import ru.ok.itmo.example.chatsPackage.Message
import ru.ok.itmo.example.chatsPackage.chatApi
import ru.ok.itmo.example.databinding.FragmentChatBinding
import ru.ok.itmo.example.retrofit.RetrofitProvider
import java.io.IOException


class ChatFragment : Fragment(R.layout.fragment_chat)  {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private val viewModel: ChatViewModel by viewModels()

    private val chatUseCase by lazy {
        val retrofit = RetrofitProvider.retrofit
        val chatApiInter = chatApi.provideRequestApi(retrofit)
        ChatUseCase(chatApiInter)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = Adapter()
        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.adapter = adapter

        val allChannels = mutableListOf<Channel>()

        val call = chatUseCase.myGetAllChannels()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val channels = response.body()

                    channels?.forEach { channelId ->
                        val messageCall = chatUseCase.myGetChannelMessages(channelId)
                        messageCall.enqueue(object : Callback<List<Message>> {
                            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                                if (response.isSuccessful) {
                                    val lastMessage = response.body()!!.last()
                                    val text = lastMessage.data.Text?.text ?: ""
                                    allChannels += Channel(channelId, text)
                                    if (allChannels.size == channels.size) {
                                        adapter.submitList(allChannels)
                                    }
                                } else {
                                    val errorBody = response.errorBody()
                                    throw IOException("$errorBody")
                                }
                            }

                            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                                throw t
                            }
                        })
                    }
                } else {
                    val errorBody = response.errorBody()
                    throw IOException("$errorBody")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                throw t
            }
        })



        binding.toolbar.setOnClickListener {
            try {
                MainScope().launch {
                    viewModel.logout(viewModel.token.toString())
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

}

