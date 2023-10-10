package task4


class Counter {
    private var value: Int = 0
    fun inc() {
        value++
    }
    fun get(): Int {
        return value
    }
}