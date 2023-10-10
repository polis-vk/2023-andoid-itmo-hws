package ru.ok.itmo.example.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import ru.ok.itmo.example.R

class TabLayout : LinearLayout {
    private val tabs: MutableList<AppCompatButton> = ArrayList()
    private var onTabSelectedListener: (String, Int) -> Unit = { _: String, _: Int -> }
    var selectedTabPos = -1

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    fun addTab(name: String) {
        val but = createButton(name, tabs.size)
        if (tabs.isEmpty()) {
            addView(but)
            tabs.add(but)
            selectedTabPos = 0
            but.callOnClick()
        } else {
            addView(but)
            tabs.add(but)
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