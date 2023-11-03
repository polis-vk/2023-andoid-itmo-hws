package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {
    private var _binding: FragmentNavigationBinding? = null
    private val binding: FragmentNavigationBinding
        get() = _binding ?: throw RuntimeException("FragmentNavigationBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startTask1.setOnClickListener {
            navigateToFragment(Task1Fragment.getTask1FragmentInstance())
        }
        binding.startTask2.setOnClickListener {
            navigateToFragment(Task2Fragment.getTask2FragmentInstance())
        }
        binding.startTask3.setOnClickListener {
            navigateToFragment(Task3Fragment.getTask3FragmentInstance())
        }
        binding.startTask4.setOnClickListener {
            navigateToFragment(Task4Fragment.getTask4FragmentInstance())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        this.parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
