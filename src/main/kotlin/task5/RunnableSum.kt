package task5

class RunnableSum(private val array: List<Int>) : Runnable {
    private var result: Int = 0
    override fun run() {
        result = 0
        for (element in array) {
            result += element
        }
    }

    fun getResult(): Int
    {
        return result
    }
}