package company.vk.polis.task1

interface MessageState

class Read : MessageState
class Unread : MessageState
class Deleted(val userId: Int) : MessageState
