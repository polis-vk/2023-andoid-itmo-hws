package ru.ok.itmo.tamtam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.ok.itmo.tamtam.login.OnDataReadyCallback
import ru.ok.itmo.tamtam.model.Model
import ru.ok.itmo.tamtam.model.OnDataReadyCallbackString

data class MyJsonObject(val from: String, val data: String)

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var name: String

    private val modelInstance: Model by inject()
    private val sharedViewModel: SharedViewModel by viewModels(ownerProducer = { requireActivity() })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            name =
                it.getString(ARG_NAME) ?: throw IllegalArgumentException("I don't know chat name")
        }
        view.findViewById<TextView>(R.id.fragment_chat_navbar_title).text = name

        val textView = view.findViewById<TextView>(R.id.my_chat_text)
        textView.text = "$name get data? wait"

        lifecycleScope.launch {
            modelInstance.getMessage(sharedViewModel.token, object : OnDataReadyCallbackString {
                override fun onDataReady(jsonString: String) {
                    val gson = Gson()
                    val jsonArray = gson.fromJson(jsonString, Array<JsonObject>::class.java)
                    var result = ""
                    for (jsonObject in jsonArray) {
                        val from = jsonObject.getAsJsonPrimitive("from").asString
                        val data = jsonObject.getAsJsonObject("data")

                        result += "From: $from, Data: ${data.toString()}\n"
                    }

                    textView.text = result
                }
            })
        }
    }



    companion object {
        private const val ARG_NAME = "name"

        @JvmStatic
        fun newInstance(name: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
    }
}