package ru.ok.itmo.example

import androidx.lifecycle.ViewModel
import ru.ok.itmo.example.fragment_section.SectionData

class SharedViewModel : ViewModel() {
    val sectionDataMap: MutableMap<String, SectionData> = mutableMapOf()
}