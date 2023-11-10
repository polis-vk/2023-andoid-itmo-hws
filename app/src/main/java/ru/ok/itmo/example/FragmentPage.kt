package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

class FragmentPage : Fragment(R.layout.fragment_page) {
    companion object {

        object TAGS {
            const val SECTION_TITLE = "sectionTitle"
            const val PAGE_NUMBER = "pageNumber"
            const val RANDOM_NUMBER = "randomNumber"
            const val CREATE_TIME = "createTime"
        }

        private const val UPDATE_INTERVAL = 100
        private const val DATE_FORMAT_PATTERN = "HH:mm:ss.S"

        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L

        fun newInstance(sectionTitle: CharSequence) = FragmentPage().apply {
            arguments = bundleOf(
                TAGS.SECTION_TITLE to sectionTitle, TAGS.CREATE_TIME to System.currentTimeMillis()
            )
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("SimpleDateFormat")
    private val timeFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
    private lateinit var currentTimeTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
            ?: throw IllegalArgumentException("Please, use newInstance for create Fragment")

        val sectionTitle = arguments.getCharSequence(TAGS.SECTION_TITLE)
            ?: throw IllegalArgumentException("I don't know section title")

        val createTime = arguments.getLong(TAGS.CREATE_TIME, DEFAULT_VALUE_LONG)
            .takeIf { it != DEFAULT_VALUE_LONG }
            ?: throw IllegalArgumentException("I don't know create time")

        val randomNumber = arguments.getInt(TAGS.RANDOM_NUMBER, DEFAULT_VALUE_INT)
            .takeIf { it != DEFAULT_VALUE_INT } ?: (1..50).random()
            .also { arguments.putInt(TAGS.RANDOM_NUMBER, it) }

        view.findViewById<TextView>(R.id.section_title_value).text = sectionTitle.toString()
        view.findViewById<TextView>(R.id.page_value).text = "???"
        view.findViewById<TextView>(R.id.random_number_value).text = randomNumber.toString()
        view.findViewById<TextView>(R.id.creation_time_value).text = timeFormat.format(createTime)

        currentTimeTextView = view.findViewById(R.id.current_time_value)
    }

    override fun onResume() {
        super.onResume()
        updateCurrentTime()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateCurrentTime() {
        val currentTime = System.currentTimeMillis()
        val formattedTime = timeFormat.format(currentTime)
        currentTimeTextView.text = formattedTime

        handler.postDelayed({ updateCurrentTime() }, UPDATE_INTERVAL.toLong())
    }
}