package ru.ok.itmo.example

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.signedIn.observe(this, {
            if (it) {
                Navigation.findNavController(this, R.id.navHostFragment)
                    .navigate(R.id.action_loginFragment_to_chatsFragment)
            }
        })
    }


}