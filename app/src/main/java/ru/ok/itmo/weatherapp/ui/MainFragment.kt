package ru.ok.itmo.weatherapp.ui

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.ok.itmo.example.R
import ru.ok.itmo.weatherapp.alarmworker.AlarmWorker
import ru.ok.itmo.weatherapp.domain.WeatherUiState
import ru.ok.itmo.weatherapp.domain.WeatherUseCase
import ru.ok.itmo.weatherapp.network.RetrofitProvider
import ru.ok.itmo.weatherapp.network.WeatherApi
import timber.log.Timber
import java.util.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private val weatherUseCase by lazy {
        val retrofit = RetrofitProvider.retrofit
        val weatherApi = WeatherApi.provideWeatherApi(retrofit)
        WeatherUseCase(weatherApi)
    }

    private val adapter: WeatherAdapter by lazy { WeatherAdapter() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var radioFlatMapZip: RadioGroup
    private lateinit var radioTypes: RadioGroup
    private lateinit var tvTime: TextView
    private lateinit var progress: ContentLoadingProgressBar
    private lateinit var btnAlarm: Button
    private lateinit var btnOneByOne: RadioButton
    private lateinit var btnParallel: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, MainViewModel.Factory(
                weatherUseCase,
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initRecycler(view)
        viewModel.uiStateLiveData.observe(viewLifecycleOwner, this::render)

        btnAlarm.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun render(uiState: WeatherUiState) {
        Timber.d("render: ${uiState.javaClass.simpleName}")
        when (uiState) {
            WeatherUiState.Loading -> {
                tvTime.text = "Loadinig data..."
                progress.visibility = View.VISIBLE
            }
            is WeatherUiState.Data -> {
                progress.visibility = View.GONE
                adapter.submitList(uiState.list.shuffled())
                tvTime.text = "Time execution: ${uiState.timeExecution} ms"
                Timber.d("time: ${uiState.timeExecution}")
            }
            is WeatherUiState.Error -> {
                progress.visibility = View.GONE
                Snackbar.make(requireView(), "Error: ${uiState.throwable}", Snackbar.LENGTH_LONG).show()
                tvTime.text = "Error fetching data"
            }
        }
    }

    private fun initView(view: View) {
        btnAlarm = view.findViewById(R.id.btn_alarm)
        progress = view.findViewById(R.id.progress)
        tvTime = view.findViewById(R.id.tvTime)
        btnOneByOne = view.findViewById(R.id.radio_flat)
        btnParallel = view.findViewById(R.id.radio_zip)
        radioFlatMapZip = view.findViewById(R.id.radioGroup)
        radioTypes = view.findViewById(R.id.radioTypes)
        radioFlatMapZip.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_flat) {
                viewModel.runOneByOne()
            } else if (checkedId == R.id.radio_zip) {
                viewModel.runParallel()
            }
        }
        radioTypes.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_rx) {
                viewModel.asyncType = MainViewModel.AsyncType.Rx
            } else if (checkedId == R.id.radio_corut) {
                viewModel.asyncType = MainViewModel.AsyncType.Coroutines
            } else if (checkedId == R.id.radio_flow) {
                viewModel.asyncType = MainViewModel.AsyncType.Flow
            }
            btnOneByOne.isChecked = false
            btnParallel.isChecked = false
        }
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
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

        AlarmWorker.scheduleAlarmWork(requireContext(), alarmTime.timeInMillis)
    }
}