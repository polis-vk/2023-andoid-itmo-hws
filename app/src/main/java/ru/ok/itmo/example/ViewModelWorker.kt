package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelWorker: Worker<Unit>, ViewModel()
{
    abstract val liveData: MutableLiveData<Counter>
    abstract val active: MutableLiveData<Boolean>
}