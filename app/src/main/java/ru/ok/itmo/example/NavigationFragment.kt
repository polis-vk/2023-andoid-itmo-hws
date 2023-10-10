package ru.ok.itmo.example


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels


class NavigationFragment : Fragment(R.layout.fragment_navigation) {
    companion object {
        const val FRAGMENT_NAME_KEY = "name"
    }

    private val viewModel by viewModels<NavigationViewModel>()

    private lateinit var navigationBar: ru.ok.itmo.example.views.TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigationBar = view.findViewById(R.id.navigation_bar)
        navigationBar.setOnTabSelectedListener { name, index ->
            if (index == viewModel.currentTab)
                return@setOnTabSelectedListener
            viewModel.currentTab
            viewModel.currentTab = index
            if (viewModel.navInit) {
                viewModel.navInit = false
                addFragment(name, false)
            } else {
                chooseFragment(name)
            }
        }
        viewModel.tabs.observe(viewLifecycleOwner) {
            observeTabs(it, viewModel.currentTab)
        }
        childFragmentManager.addOnBackStackChangedListener {
            onBackStackChange()
        }
    }

    private fun onBackStackChange() {
        val name = backStackLast()
        for (i in 0 until navigationBar.tabCount()) {
            if (name == navigationBar.getTextAt(i) && i != navigationBar.selectedTabPos) {
                navigationBar.selectTab(i)
                break
            }
        }

    }

    private fun observeTabs(list: ArrayList<String>, index: Int) {
        navigationBar.removeAllTabs()
        if (!viewModel.navInit) {
            navigationBar.addTabsWithClear(index, list)
        } else {
            for (barName in list) {
                navigationBar.addTab(barName)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.commit {
            setPrimaryNavigationFragment(this@NavigationFragment)
        }
    }

    private fun checkForExisting(name: String): Boolean {
        if (backStackSize() == 0) {
            return firstName() == name
        }
        if (firstName() == name) {
            childFragmentManager.popBackStack(
                backStackAt(0).name!!,
                POP_BACK_STACK_INCLUSIVE
            )
            return true
        }
        for (i in 0 until backStackSize()) {
            if (name == backStackAt(i).name!!) {
                childFragmentManager.popBackStack(name, 0)
                return true
            }
        }
        return false
    }

    private fun chooseFragment(fragmentName: String, addToBackStack: Boolean = true) {
        if (checkForExisting(fragmentName)) {
            return
        }
        addFragment(fragmentName, addToBackStack)
    }

    private fun addFragment(fragmentName: String, addToBackStack: Boolean = true) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = bundleOf(
                Pair(FRAGMENT_NAME_KEY, fragmentName),
            )
            replace(R.id.navigation_fragment_container, NavigationTabFragment::class.java, bundle)
            if (addToBackStack) addToBackStack(fragmentName)
        }
    }

    private fun backStackSize(): Int {
        return childFragmentManager.backStackEntryCount
    }

    private fun backStackAt(i: Int): BackStackEntry {
        return childFragmentManager.getBackStackEntryAt(i)
    }

    private fun backStackLast(): String {
        if (childFragmentManager.backStackEntryCount == 0) {
            return viewModel.tabs.value!![0]
        }
        return backStackAt(backStackSize() - 1).name!!
    }

    private fun firstName(): String {
        return navigationBar.getTextAt(0)
    }
}