package ru.ok.itmo.example


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.signIn.observe(this) {
            if (it) {
                Navigation.findNavController(this, R.id.navHostFragment)
                    .navigate(R.id.action_loginFragment_to_chatsFragment)
                viewModel.signIn.value = false
            }
        }

        viewModel.toastMessage.observe(this) {
            if (it is Int) {
                Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
                viewModel.toastMessage.value = null
            }
        }
    }
}