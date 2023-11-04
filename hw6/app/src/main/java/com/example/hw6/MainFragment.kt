package com.example.hw6

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private lateinit var progress: ProgressBar
    private lateinit var btnAlarm: Button
    private lateinit var workMode: RadioGroup
    private var mode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, MainViewModel.Factory(
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, this::render)
        btnAlarm.setOnClickListener {
            if (mode == 2) {
                showTimePickerDialog()
            } else {
                viewModel.countdown(mode)
            }
        }
        workMode.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.corut) {
                mode = 0
            } else if (checkedId == R.id.flow) {
                mode = 1
            } else if (checkedId == R.id.ala) {
                mode = 2
            }
        }
    }
    private fun render(timeState : TimerUiState) {
        when (timeState) {
            is TimerUiState.Data -> {
                progress.progress = timeState.timeExecution
            }
        }
    }
    private fun initView(view : View) {
        progress = view.findViewById(R.id.progress)
        btnAlarm = view.findViewById(R.id.start_button)
        workMode = view.findViewById(R.id.radioTypes)
    }

    private var hourOfDay = 0
    private var minute = 0

    private fun showTimePickerDialog() {
        val c = Calendar.getInstance()
        hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hourOfDay: Int, minute: Int ->
                setAlarm(hourOfDay, minute)
            },
            hourOfDay,
            minute,
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
            alarmTime.add(Calendar.DAY_OF_MONTH, 1)
        }

        viewModel.makeAlarm(requireContext(), alarmTime.timeInMillis)
    }
}