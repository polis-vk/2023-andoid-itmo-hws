package ru.ok.itmo.example

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

//        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        supportActionBar!!.run {
            setDisplayShowCustomEnabled(true)
            setDisplayShowHomeEnabled(false)
            setCustomView(R.layout.action_bar_simple)

            customView.run {
                (parent as Toolbar)
                    .setContentInsetsAbsolute(0, 0)

                findViewById<ImageButton>(R.id.buttonBack)
                    .setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
            }
        }



//        val actionBar: View = layoutInflater.inflate(R.layout.action_bar_simple, null)
//        val layoutParams = ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
//        supportActionBar!!.setCustomView(actionBar, layoutParams)

//        setSupportActionBar(actionBar as Toolbar)
//        supportActionBar!!.setCustomView(R.layout.action_bar_simple, ActionBar.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!
//        val navController = navHostFragment.findNavController()
//        navController.navigate(R.id.loginFragment)
    }

}