package company.vk.polis.task1

interface State {
    class UNREADED : State
    class READED : State
    class DELETED(var id: Int) : State
}