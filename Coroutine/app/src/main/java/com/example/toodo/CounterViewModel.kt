import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CounterViewModel : ViewModel() {
    // Private mutable state
    private val _counter = MutableStateFlow(0)
    // Publicly exposed immutable state
    val counter: StateFlow<Int> = _counter

    // Function to increment the counter
    fun increment() {
        _counter.value++
    }
}
