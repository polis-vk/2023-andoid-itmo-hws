package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

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

    private lateinit var sectionTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionTitle = requireArguments().getCharSequence(TAGS.SECTION_TITLE)?.toString()
            ?: throw IllegalArgumentException("I don't know section title")

        if (getPageCount() == 0) {
            newPageTransaction().commit()
        }

        val button = view.findViewById<Button>(R.id.button_new_page)
        button.setOnClickListener {
            createNewPage()
        }
    }

    private fun createNewPage() {
        newPageTransaction()
            .addToBackStack(null)
            .commit()
    }

    private fun newPageTransaction(): FragmentTransaction {
        val pageNumber = getAndIncPageCount()

        return childFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_section_container,
                FragmentPage.newInstance(sectionTitle, pageNumber),
            )
        }
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