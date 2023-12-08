package ru.ok.itmo.example.fragment_with_navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import ru.ok.itmo.example.fragment_section.FragmentSection
import ru.ok.itmo.example.R

class FragmentWithNavigation : Fragment(R.layout.fragment_with_navigation) {
    companion object {
        object TAGS {
            const val NUMBER_OF_SECTIONS = "numberOfSections"
            const val INIT_BACK_STACK_TAG = "initSection"
        }

        object ResultTags {
            const val RESULT = "result"
            const val COUNT_FRAGMENTS = "countFragments"
        }

        fun newInstance(numberOfSections: Int) = FragmentWithNavigation().apply {
            arguments = bundleOf(
                TAGS.NUMBER_OF_SECTIONS to numberOfSections
            )
        }
    }

    private lateinit var navigationView: NavigationViewInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView = processNavigationView(view.findViewById(R.id.nav_view))

        val numberOfSections = arguments?.getInt(TAGS.NUMBER_OF_SECTIONS)
            ?: throw IllegalArgumentException("I don't know how many sections are needed")

        if (savedInstanceState != null) {
            trimMenu(numberOfSections)
        } else {
            trimMenu(numberOfSections)
            initSections()
        }

        navigationView.setOnItemSelectedListener { item ->
            replaceSection(title = item.title.toString())
            true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                childFragmentManager.run {
                    if (backStackEntryCount <= 1 + navigationView.menu.size()) {
                        setResult()
                        childFragmentManager.popBackStack(
                            TAGS.INIT_BACK_STACK_TAG, POP_BACK_STACK_INCLUSIVE
                        )
                        parentFragmentManager.popBackStack()
                    } else {
                        val lastFragmentBackStackTag =
                            getBackStackEntryAt(backStackEntryCount - 2).name

                        navigationView.menu.getItem(
                            when (lastFragmentBackStackTag) {
                                getString(R.string.sec_1) -> 0
                                getString(R.string.sec_2) -> 1
                                getString(R.string.sec_3) -> 2
                                getString(R.string.sec_4) -> 3
                                getString(R.string.sec_5) -> 4
                                else -> throw IllegalArgumentException(
                                    "Unknown Fragment in BackStack: $lastFragmentBackStackTag"
                                )
                            }
                        ).isChecked = true

                        popBackStack()
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setResult() {
        var result = 0

        for (index in 0 until navigationView.menu.size()) {
            result += (childFragmentManager.findFragmentByTag(
                navigationView.menu[index].title.toString()
            ) as FragmentSection).getPageCountOrZero()
        }

        parentFragmentManager.setFragmentResult(
            ResultTags.RESULT, bundleOf(
                ResultTags.COUNT_FRAGMENTS to result
            )
        )
    }

    private fun replaceSection(title: String) {
        childFragmentManager.popBackStack(title, POP_BACK_STACK_INCLUSIVE)
        addSection(
            childFragmentManager.findFragmentByTag(title)
                ?: throw IllegalArgumentException("I can't find fragment: \"$title\""), title, title
        )
    }

    private fun addSection(fragment: Fragment, fragmentTag: String, backStackTag: String) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_with_navigation_container, fragment, fragmentTag
            )
            addToBackStack(backStackTag)
        }
    }

    private fun trimMenu(numberOfSections: Int) {
        for (index in navigationView.menu.size() - 1 downTo numberOfSections) {
            navigationView.menu.removeItem(navigationView.menu.getItem(index).itemId)
        }
    }

    private fun initSections() {
        for (index in navigationView.menu.size() - 1 downTo 1) {
            val title = navigationView.menu.getItem(index).title.toString()
            addSection(
                FragmentSection.newInstance(title), title, backStackTag = TAGS.INIT_BACK_STACK_TAG
            )
        }

        val firstItem = navigationView.menu.getItem(0)
        firstItem.isChecked = true
        val title = firstItem.title.toString()
        val firstFragment = FragmentSection.newInstance(title)
        addSection(
            FragmentSection.newInstance(title), title, backStackTag = TAGS.INIT_BACK_STACK_TAG
        )
        addSection(firstFragment, title, title)
    }
}