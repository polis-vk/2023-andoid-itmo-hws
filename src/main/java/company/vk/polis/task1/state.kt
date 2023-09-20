package company.vk.polis.task1

interface State
object READ : State
object UNREAD : State
data class DELETED(val userId: Int) : State
