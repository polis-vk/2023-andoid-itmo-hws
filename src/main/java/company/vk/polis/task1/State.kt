package company.vk.polis.task1

sealed class State {
    class Read : State(){ //Я хотел использовать data object вместо class, но java не понимает, что это такое
        override fun toString(): String {
            return "READ"
        }
    }
    class Unread : State(){
        override fun toString(): String {
            return "UNREAD"
        }
    }
    data class Deleted(val id: Int?): State(){
        override fun toString(): String {
            return "DELETED"
        }
    }
}