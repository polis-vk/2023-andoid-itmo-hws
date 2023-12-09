package ru.ok.itmo.example.fragment_section

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.ok.itmo.example.FragmentPage
import ru.ok.itmo.example.R

class FragmentSection : Fragment(R.layout.fragment_section) {
    companion object {

        object TAGS {
            const val SECTION_TITLE = "sectionTitle"
            const val PAGE_COUNT = "pageCount"
        }

        fun newInstance(sectionTitle: CharSequence) = FragmentSection().apply {
            arguments = bundleOf(
                TAGS.SECTION_TITLE to sectionTitle
            )
        }
    }

    private lateinit var callback: OnBackPressedCallback
    private lateinit var sectionTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionTitle = requireArguments().getCharSequence(TAGS.SECTION_TITLE)?.toString()
            ?: throw IllegalArgumentException("I don't know section title")

        if (getPageCount() == 0) {
            createNewPage()
        } else {
            viewCurrentPage()
        }

        val button = view.findViewById<Button>(R.id.button_new_page)
        button.setOnClickListener {
            createNewPage()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                childFragmentManager.run {
                    popBackStack()
                    if (backStackEntryCount <= 2) {
                        disableCallback()
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun createNewPage() {
        getAndIncPageCount()
        viewCurrentPage()
    }

    private fun viewCurrentPage() {
        val pageNumber = getPageCount()

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_section_container,
                childFragmentManager.findFragmentByTag("$sectionTitle - $pageNumber")
                    ?: FragmentPage.newInstance(sectionTitle, pageNumber),
                "$sectionTitle - $pageNumber"
            )
            addToBackStack(null)
        }

        if (childFragmentManager.backStackEntryCount < 1) {
            disableCallback()
        } else {
            enableCallback()
        }
    }

    private fun disableCallback() {
        callback.isEnabled = false
    }

    private fun enableCallback() {
        callback.isEnabled = true
    }

    private fun getPageCount(): Int {
        return requireArguments().getInt(TAGS.PAGE_COUNT, 0)
    }

    fun getPageCountOrZero(): Int {
        val arguments = arguments ?: return 0
        return arguments.getInt(TAGS.PAGE_COUNT, 0)
    }

    private fun getAndIncPageCount(): Int {
        return (getPageCount() + 1).also { requireArguments().putInt(TAGS.PAGE_COUNT, it) }
    }
}