package ru.ok.itmo.example

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.ok.itmo.example.Retrofit.MainApi
import ru.ok.itmo.example.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_startFragment)
            }
            enterButton.setOnClickListener {
                viewModel.editLogin(loginEdit.text.toString())
                viewModel.editPwd(pwdEdit.text.toString())
                viewModel.checkInternet(checkConnection(requireContext()))
                viewModel.auth()
                viewModel.navigationLive.observe(viewLifecycleOwner) {
                    when (it){
                        200 -> {
                            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                        }
                        401 -> {
                                binding.errorMessage.text = resources.getString(R.string.unauthorized)
                        }

                        402 -> {
                                binding.errorMessage.text = resources.getString(R.string.no_internet)
                        }

                        403 -> {
                                binding.errorMessage.text = resources.getString(R.string.server_connection_error)
                        }
                    }
                }
            }

            loginEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.editLogin(loginEdit.text.toString())
                    viewModel.isEnabledLive.observe(viewLifecycleOwner) {
                        enterButton.isEnabled = it
                    }
                }
            })


            pwdEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.editPwd(pwdEdit.text.toString())
                    viewModel.isEnabledLive.observe(viewLifecycleOwner) {
                        enterButton.isEnabled = it
                    }
                }
            })

        }

    }

    private fun checkConnection(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true;
            }
        }
        return false
    }

}