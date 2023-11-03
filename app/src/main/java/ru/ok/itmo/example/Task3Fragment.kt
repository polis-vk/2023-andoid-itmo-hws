package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.ok.itmo.example.databinding.FragmentTask3Binding
import java.util.Calendar

class Task3Fragment : Fragment() {
    private var _binding: FragmentTask3Binding? = null
    private val binding: FragmentTask3Binding
        get() = _binding ?: throw RuntimeException("FragmentTask3Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTask3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scheduleAlarm.setOnClickListener {
            showTimePickerDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireActivity(), { timePicker: TimePicker, hourOfDay: Int, minute: Int ->
                setAlarm(hourOfDay, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance()
        alarmTime[Calendar.HOUR_OF_DAY] = hour
        alarmTime[Calendar.MINUTE] = minute

        if (alarmTime.before(now)) {
            Toast.makeText(
                this.requireContext(),
                requireContext().getText(R.string.past_date),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            AlarmWorker.scheduleAlarmWork(requireContext(), alarmTime.timeInMillis)
            Toast.makeText(
                this.requireContext(),
                requireContext().getText(R.string.scheduled_alarm), Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        fun getTask3FragmentInstance(): Task3Fragment {
            return Task3Fragment()
        }
    }
}