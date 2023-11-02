package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import java.util.Calendar

class Task3Fragment : Fragment() {

    private lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)

        button = view.findViewById(R.id.button2)

        button.setOnClickListener {
            showTimePickerDialog()
        }

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()

        TimePickerDialog(
            requireActivity(),
            { _, hourOfDay, minute ->
                setAlarm(hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setAlarm(hourOfDay: Int, minute: Int) {
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
        calendar[Calendar.MINUTE] = minute

        if (calendar.before(now)) {
            calendar.add(Calendar.DATE, 1)
        }

        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        AlarmWorker.scheduleAlarmWork(requireContext(), calendar.timeInMillis)
    }
}