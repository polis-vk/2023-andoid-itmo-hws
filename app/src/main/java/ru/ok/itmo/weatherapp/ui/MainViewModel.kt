package ru.ok.itmo.weatherapp.ui

import androidx.lifecycle.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.weatherapp.domain.WeatherUiState
import ru.ok.itmo.weatherapp.domain.WeatherUseCase
import ru.ok.itmo.weatherapp.network.dto.WeatherResponce
import timber.log.Timber

class MainViewModel constructor(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _uiStateLiveData = MutableLiveData<WeatherUiState>()
    val uiStateLiveData: LiveData<WeatherUiState>
        get() = _uiStateLiveData

    enum class AsyncType {
        Rx, Coroutines, Flow
    }
    var asyncType: AsyncType = AsyncType.Rx

    init {
        runRxFlatmapVersion()
    }

    fun runOneByOne() {
        Timber.d("asyncType: $asyncType")
        when (asyncType) {
            AsyncType.Rx -> runRxFlatmapVersion()
            AsyncType.Coroutines -> runCoroutinesVersion()
            AsyncType.Flow -> runFlowFlatmapVersion()
        }
    }

    fun runParallel() {
        Timber.d("asyncType: $asyncType")
        when (asyncType) {
            AsyncType.Rx -> runRxZipVersion()
            AsyncType.Coroutines -> runCoroutinesAsyncVersion()
            AsyncType.Flow -> runFlowZipVersion()
        }
    }

    private fun runCoroutinesVersion() {
        val startTime = System.currentTimeMillis()
        _uiStateLiveData.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                val items: List<WeatherResponce> = withContext(Dispatchers.IO) {
                    weatherUseCase.coroutineVersion()
                }
                handleResult(startTime, items)
            } catch (error: Throwable) {
                handleError(error)
            }
        }
    }

    private fun runCoroutinesAsyncVersion() {
        val startTime = System.currentTimeMillis()
        _uiStateLiveData.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                val items: List<WeatherResponce> = withContext(Dispatchers.IO) {
                    weatherUseCase.coroutineAsyncVersion()
                }
                handleResult(startTime, items)
            } catch (error: Throwable) {
                handleError(error)
            }
        }
    }

    private fun runRxFlatmapVersion() {
        val startTime = System.currentTimeMillis()
        val disposable = weatherUseCase.rxFlatmapVersion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _uiStateLiveData.value = WeatherUiState.Loading }
            .subscribe(
                { items ->
                    handleResult(startTime, items)
                },
                { error ->
                    handleError(error)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun runRxZipVersion() {
        val startTime = System.currentTimeMillis()
        val disposable = weatherUseCase.rxZipVersion()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _uiStateLiveData.value = WeatherUiState.Loading }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    handleResult(startTime, items)
                },
                { error ->
                    handleError(error)
                }
            )
        compositeDisposable.add(disposable)
    }


    private fun runFlowFlatmapVersion() {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            weatherUseCase.flowFlatMapVersion()
                .flowOn(Dispatchers.IO)
                .catch {
                    handleError(it)
                }
                .collect { items ->
                    handleResult(startTime, items)
                }
        }
    }

    private fun runFlowZipVersion() {
        val startTime = System.currentTimeMillis()
        viewModelScope.launch {
            weatherUseCase.flowZipVersion()
                .flowOn(Dispatchers.IO)
                .catch {
                    handleError(it)
                }
                .collect { items ->
                    handleResult(startTime, items)
                }
        }
    }

    private fun handleResult(
        startTime: Long,
        items: List<WeatherResponce>
    ) {
        val elapsedTime = System.currentTimeMillis() - startTime
        _uiStateLiveData.value = WeatherUiState.Data(items, elapsedTime)
    }

    private fun handleError(error: Throwable) {
        Timber.e("Error data fetching: $error")
        _uiStateLiveData.value = WeatherUiState.Error(error)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory internal constructor(
        private val weatherUseCase: WeatherUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(weatherUseCase) as T
        }
    }
}