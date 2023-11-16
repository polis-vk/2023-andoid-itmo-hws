package ru.ok.itmo.example.fragment_section

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import ru.ok.itmo.example.FragmentPage
import ru.ok.itmo.example.R
import ru.ok.itmo.example.Destroyable
import ru.ok.itmo.example.SharedViewModel

class FragmentSection : Fragment(R.layout.fragment_section), Destroyable {
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

    private lateinit var argumentsNotNull: Bundle
    private lateinit var callback: OnBackPressedCallback
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var sectionData: SectionData
    private lateinit var sectionTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argumentsNotNull = arguments
            ?: throw IllegalArgumentException("Please, use newInstance for create Fragment")

        sectionTitle = argumentsNotNull.getCharSequence(TAGS.SECTION_TITLE)?.toString()
            ?: throw IllegalArgumentException("I don't know section title")

        sectionData = sharedViewModel.sectionDataMap.getOrPut(sectionTitle) {
            SectionData()
        }

        if (sectionData.backStack.isEmpty()) {
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
                    if (sectionData.backStack.size > 1) {
                        sectionData.backStack.pop()
                        viewCurrentPage()
                        popBackStack()
                    }
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun createNewPage() {
        sectionData.backStack.push(getAndIncPageCount())

        viewCurrentPage()
    }

    private fun viewCurrentPage() {
        val pageNumber = sectionData.backStack.last()

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_section_container,
                sectionData.fragmentMap.getOrPut(pageNumber) {
                    FragmentPage.newInstance(
                        sectionTitle,
                        pageNumber
                    )
                }
            )
        }

        if (sectionData.backStack.size <= 1) {
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
        return argumentsNotNull.getInt(TAGS.PAGE_COUNT, 0)
    }

    private fun getAndIncPageCount(): Int {
        return (getPageCount() + 1).also { argumentsNotNull.putInt(TAGS.PAGE_COUNT, it) }
    }

    override fun destroy() {
        sectionData.backStack.clear()
        sectionData.fragmentMap.clear()
    }
}