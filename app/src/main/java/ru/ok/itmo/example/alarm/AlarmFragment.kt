package ru.ok.itmo.example.alarm

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.ok.itmo.example.R
import ru.ok.itmo.example.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {
    private val viewModel by viewModels<AlarmViewModel>()

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            { _: TimePicker, hours: Int, minutes: Int ->
                onTimePicked(hours, minutes)
            },
            hourOfDay,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun onTimePicked(hours: Int, minutes: Int) {
        AlarmWorker.scheduleAlarmWork(
            requireContext(),
            viewModel.getPeriod(hours, minutes),
            resources.getString(R.string.alarm_title),
            resources.getString(R.string.alarm_text)
        )
    }
}