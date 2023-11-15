package ru.ok.itmo.example.fragment_with_navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.ok.itmo.example.R

class FragmentWithNavigation : Fragment(R.layout.fragment_with_navigation) {
    companion object {
        object TAGS {
            const val NUMBER_OF_SECTIONS = "numberOfSections"
            const val SELECTED_ITEM_ID = "selectedItemId"
            const val MENU_DATA = "menuData"
        }

        fun newInstance(numberOfSections: Int) = FragmentWithNavigation().apply {
            arguments = bundleOf(
                TAGS.NUMBER_OF_SECTIONS to numberOfSections
            )
        }

        class FragmentWithNavigationSharedViewModel(private val savedStateHandle: SavedStateHandle) :
            ViewModel() {
            val fragmentMap = mutableMapOf<String, Fragment>()

            fun isStoresSavedState(): Boolean {
                return savedStateHandle.contains(TAGS.MENU_DATA)
                        && savedStateHandle.contains(TAGS.SELECTED_ITEM_ID)
            }

            var menuData: MenuData
                get() = savedStateHandle[TAGS.MENU_DATA]
                    ?: throw IllegalArgumentException("${TAGS.MENU_DATA} is missing from savedStateHandle")
                set(value) {
                    savedStateHandle[TAGS.MENU_DATA] = value
                }

            var selectedItemId: Int
                get() = savedStateHandle[TAGS.SELECTED_ITEM_ID]
                    ?: throw IllegalArgumentException("${TAGS.SELECTED_ITEM_ID} is missing from savedStateHandle")
                set(value) {
                    savedStateHandle[TAGS.SELECTED_ITEM_ID] = value
                }
        }
    }

    private val sharedViewModel: FragmentWithNavigationSharedViewModel by viewModels()
    private lateinit var navigationView: NavigationViewInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView = processNavigationView(view.findViewById(R.id.nav_view))

        val numberOfSections = arguments?.getInt(TAGS.NUMBER_OF_SECTIONS)
            ?: throw IllegalArgumentException("I don't know how many sections are needed")

        if (sharedViewModel.isStoresSavedState()) {
            drawMenu()
            navigationView.selectedItemId = sharedViewModel.selectedItemId
        } else {
            sharedViewModel.menuData = MenuData(numberOfSections)
            drawMenu()
            val firstItem = navigationView.menu.getItem(0)
            firstItem.isChecked = true
            addNewSection(firstItem.title.toString(), firstItem.itemId.toString())
        }

        navigationView.setOnItemSelectedListener { item ->
            replaceSection(title = item.title.toString(), tag = item.itemId.toString())
            true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                childFragmentManager.run {
                    if (backStackEntryCount <= 1)
                        parentFragmentManager.popBackStack()
                    else {
                        val lastFragmentTag =
                            getBackStackEntryAt(backStackEntryCount - 2).name?.toInt()
                                ?: throw IllegalArgumentException("Unknown Fragment in BackStack")

                        navigationView.menu.findItem(lastFragmentTag).isChecked = true
                    }
                    popBackStack()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun replaceSection(title: String, tag: String) {
        childFragmentManager.findFragmentByTag(tag)?.let {
            childFragmentManager.popBackStack(tag, 0)
        } ?: addNewSection(title, tag)
    }

    private fun addNewSection(title: String, tag: String) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_with_navigation_container,
                sharedViewModel.fragmentMap.getOrPut(tag) { FragmentSection.newInstance(title) },
                tag
            )
            addToBackStack(tag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        sharedViewModel.selectedItemId = navigationView.selectedItemId
    }

    private fun drawMenu() {
        for (section in sharedViewModel.menuData.sectionsData) {
            navigationView.menu.add(section.groupId, section.itemId, section.order, section.title)
                .setIcon(section.iconRes)
        }
    }
}