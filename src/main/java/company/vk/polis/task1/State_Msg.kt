package company.vk.polis.task1

class State_Msg(msg_state: State_Msg_Enum, private var id: Int?) {
    private var _state: State_Msg_Enum;
    init {
        _state = msg_state
    }
    fun get_state() :State_Msg_Enum {
        return _state;
    }
}