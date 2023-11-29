package ru.ok.itmo.example.chats

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.R
import ru.ok.itmo.example.custom_view.AvatarCustomView

class ChatsFragment : Fragment() {
    companion object {
        const val TAG = "CUSTOM_VIEW"
    }

    private lateinit var avatarCustomView: AvatarCustomView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chats_fragment, container, false);
        avatarCustomView = view.findViewById(R.id.avatarCustomView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBitmapFromRes(R.drawable.kat)
    }

    private fun setBitmapFromRes(resId: Int) {
        avatarCustomView.setImage(BitmapFactory.decodeResource(context?.resources, resId))
    }
}