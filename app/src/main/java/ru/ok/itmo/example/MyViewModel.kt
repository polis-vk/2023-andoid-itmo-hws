import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {

    val progressLiveData = MutableLiveData<Int>()

    fun start() {
        viewModelScope.launch(Dispatchers.IO)  {
            var i = 0
            repeat(100) {
                delay(100)
                withContext(Dispatchers.Main) {
                    progressLiveData.value = i
                    i++
                }
            }
        }
    }
}
