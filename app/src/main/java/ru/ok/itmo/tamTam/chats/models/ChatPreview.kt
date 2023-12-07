package ru.ok.itmo.tamTam.chats.models

import android.content.IntentSender

data class ChatPreview(val isChat : Boolean, val title : String,
                       val textMessage : String, val senderName: String, val time : String)