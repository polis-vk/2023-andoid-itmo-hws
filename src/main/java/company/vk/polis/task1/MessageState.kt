package company.vk.polis.task1

class MessageState(state: MessageStateEnum) {
    private var deletedBy: Int? = null
    private var state: MessageStateEnum

    init {
        this.state = state
    }

    constructor(state: MessageStateEnum, deletedBy: Int?) : this(state) {
        if (state != MessageStateEnum.DELETED && deletedBy != null) {
            throw java.lang.IllegalArgumentException("Illegal non-DELETED MessageState with non-null deletedBy")
        }
        this.deletedBy = deletedBy
    }

    fun getState(): MessageStateEnum = state

    fun getDeletedBy(): Int? = deletedBy

}

enum class MessageStateEnum {
    READ, UNREAD, DELETED;
}