package ru.ok.itmo.example.fragment_section

import androidx.fragment.app.Fragment
import java.util.Stack

data class SectionData(
    val backStack: Stack<Int> = Stack(),
    val fragmentMap: MutableMap<Int, Fragment> = mutableMapOf()
)