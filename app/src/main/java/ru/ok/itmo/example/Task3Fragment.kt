package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.AlarmWorker.Companion.setAlarm
import java.util.Calendar


class Task3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_3, container, false)

        val setAlarmButton: Button = view.findViewById(R.id.setAlarmButton)
        setAlarmButton.setOnClickListener {
            showTimePickerDialog()
        }

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, min ->
            setAlarm(requireContext(), hourOfDay, min)
        }, hour, minute, true)

        timePickerDialog.show()
    }
}
