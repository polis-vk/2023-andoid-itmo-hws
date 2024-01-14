package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.MapTypeAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment(R.layout.fragment) {
    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
    }

    fun setAccountData(password: String, login: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val textMessage = requireView().findViewById<TextView>(R.id.text_message);
            requireView().findViewById<TextView>(R.id.list_chat).isVisible = false
            requireView().findViewById<TextView>(R.id.progress_bar).isVisible = true
            textMessage.isVisible = false
            try {
                val data = api.getData(login, password)
                if (data.isEmpty()) {
                    textMessage.text = "There is no data"
                    textMessage.isVisible = true
                }else {
                    val chatList = requireActivity().findViewById<ListView>(R.id.list_chat)
                    chatList.adapter = ChatAdapter(requireActivity(), data)
                    requireView().findViewById<TextView>(R.id.list_chat).isVisible = true
                }
            } catch (e: Throwable) {
                textMessage.text = "Download error: " + e.message
                textMessage.isVisible = true
            }
            requireView().findViewById<TextView>(R.id.progress_bar).isVisible = false

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.bt_back).setOnClickListener {
            parentFragmentManager.setFragmentResult("back", bundleOf())
        }
    }
}