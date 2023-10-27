package ru.ok.itmo.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast

class Reciever1: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "AI YA BLOODY RATS ALARM HERE!!!!", Toast.LENGTH_SHORT).show();
       val alarm = MediaPlayer.create(context, R.raw.rickroll);
        alarm.start();
    }
}