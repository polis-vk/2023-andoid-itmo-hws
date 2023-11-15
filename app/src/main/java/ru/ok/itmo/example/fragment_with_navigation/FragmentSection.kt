package ru.ok.itmo.example.fragment_with_navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import ru.ok.itmo.example.FragmentPage
import ru.ok.itmo.example.R

class FragmentSection : Fragment(R.layout.fragment_section) {
    companion object {

        object TAGS {
            const val SECTION_TITLE = "sectionTitle"
            const val PAGE_COUNT = "pageCount"
            const val CURRENT_PAGE = "currentPage"
        }

        private const val DEFAULT_VALUE_INT = -1

        fun newInstance(sectionTitle: CharSequence) = FragmentSection().apply {
            arguments = bundleOf(
                TAGS.SECTION_TITLE to sectionTitle
            )
        }

        class FragmentSectionViewModel() : ViewModel() {
            val fragmentMap = mutableMapOf<Int, Fragment>()
        }
    }

    private lateinit var argumentsNotNull: Bundle

    private val viewModel: FragmentSectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argumentsNotNull = arguments
            ?: throw IllegalArgumentException("Please, use newInstance for create Fragment")

        if (argumentsNotNull.getInt(TAGS.CURRENT_PAGE, DEFAULT_VALUE_INT) == DEFAULT_VALUE_INT) {
            createNewPage()
        } else {
            viewCurrentPage()
        }
    }

    private fun createNewPage() {
        val pageNumber = getAndIncPageCount()

        argumentsNotNull.putInt(TAGS.CURRENT_PAGE, pageNumber)

        viewCurrentPage()
    }

    private fun viewCurrentPage() {
        val sectionTitle = argumentsNotNull.getCharSequence(TAGS.SECTION_TITLE)
            ?: throw IllegalArgumentException("I don't know section title")

        val pageNumber = getCurrentPageNotDefault()

        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_section_container,
                //FragmentPage.newInstance(sectionTitle, pageNumber)
                viewModel.fragmentMap.getOrPut(pageNumber) {
                    FragmentPage.newInstance(
                        sectionTitle,
                        pageNumber
                    )
                }
            )
        }
    }

    private fun getPageCount(): Int {
        return argumentsNotNull.getInt(TAGS.PAGE_COUNT, 0)
    }

    private fun getCurrentPageNotDefault(): Int {
        return argumentsNotNull.getInt(TAGS.CURRENT_PAGE, DEFAULT_VALUE_INT)
            .takeIf { it != DEFAULT_VALUE_INT }
            ?: throw IllegalArgumentException("I don't know current page")
    }

    private fun getAndIncPageCount(): Int {
        return (getPageCount() + 1).also { argumentsNotNull.putInt(TAGS.PAGE_COUNT, it) }
    }
}