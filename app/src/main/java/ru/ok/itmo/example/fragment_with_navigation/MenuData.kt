package ru.ok.itmo.example.fragment_with_navigation

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize
import ru.ok.itmo.example.R

@Parcelize
class MenuData(
    val numberOfSections: Int,
    val sectionsData: Array<SectionData>
) : Parcelable {
    @Parcelize
    data class SectionData(
        val groupId: Int,
        val itemId: Int,
        val order: Int,
        val title: CharSequence,
        val iconRes: Int
    ) : Parcelable

    companion object {
        private fun createSectionsData(numberOfSections: Int): Array<SectionData> {
            return Array(numberOfSections) { index ->
                SectionData(
                    0,
                    View.generateViewId(),
                    0,
                    "Sec: ${index + 1}",
                    R.drawable.ic_launcher_foreground
                )
            }
        }
    }

    constructor(numberOfSections: Int) : this(
        numberOfSections,
        createSectionsData(numberOfSections)
    )
}