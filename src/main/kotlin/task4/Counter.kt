package task4

data class Counter(var count: Int = 0) {
    companion object {
        const val NUMBER_THREADS = 3
        const val DESIRED_NUMBER = 1000
    }
}