package ru.ok.itmo.example.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import ru.ok.itmo.example.R


class TabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val tabs: MutableList<AppCompatButton> = ArrayList()
    private var onTabSelectedListener: (String, Int) -> Unit = { _: String, _: Int -> }
    var selectedTabPos = -1

    fun addTab(name: String) {
        val but = createButton(name, tabs.size)
        val isEmpty = tabs.isEmpty()
        addView(but)
        tabs.add(but)
        if (isEmpty) {
            selectedTabPos = 0
            but.callOnClick()
        }
    }

    fun addTabsWithClear(selected: Int, names: ArrayList<String>) {
        removeAllTabs()
        for (name in names) {
            val but = createButton(name, tabs.size)
            addView(but)
            tabs.add(but)
        }
        unselectAll()
        tabs[selected].setBackgroundColor(Color.GREEN)
        selectedTabPos = selected
    }

    fun tabCount(): Int {
        return tabs.size
    }

    fun removeAllTabs() {
        removeAllViews()
        tabs.clear()
        selectedTabPos = -1
    }

    private fun createButton(text: String, index: Int): AppCompatButton {
        val button = inflate(context, R.layout.menu_button, null) as AppCompatButton
        button.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT,
            1f
        )
        button.text = text
        button.setBackgroundColor(Color.GRAY)
        button.setOnClickListener {
            selectedTabPos = index
            unselectAll()
            button.setBackgroundColor(Color.GREEN)
            onTabSelectedListener(text, index)
        }
        return button
    }

    fun getTextAt(i: Int): String {
        return tabs[i].text.toString()
    }

    private fun unselectAll() {
        for (but in tabs) {
            but.setBackgroundColor(Color.GRAY)
        }
    }

    fun setOnTabSelectedListener(listener: (String, Int) -> Unit) {
        onTabSelectedListener = listener
    }

    fun selectTab(i: Int) {
        tabs[i].callOnClick()
    }
}