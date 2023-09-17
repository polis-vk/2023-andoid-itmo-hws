package company.vk.polis.task1.models

class DeleteState(val id: Int) : State {
    override val state: StateType = StateType.DELETED
}