package company.vk.polis.task1

sealed class State(private val stateName: String) {
    fun getState(): String {
        return stateName
    }

    data object Read : State("READ")
    data object Unread : State("UNREAD")
    data class Deleted(val userId: Int?) : State("DELETED")
}

class StateContext(defaultState: State) {

    private var currentState: State = defaultState
    fun setState(state: State) {
        currentState = state
    }

    fun getState(): String {
        return currentState.getState()
    }
}

