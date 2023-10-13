

fun main() {
    val locker1 = Any()
    val locker2 = Any()

    val thread1 = Thread {
        synchronized(locker1) {
            println("Поток 1: Начал удерживать замок 1")
            Thread.sleep(1000) // задержка дает гарантию взятия второго замка
            synchronized(locker2) {
                println("Поток 1: Начал удерживать замок 2")
            }
        }
    }

    val thread2 = Thread {
        synchronized(locker2) {
            println("Поток 2: Начал удерживать замок 2")
            Thread.sleep(1000) // задержка дает гарантию взятия второго замка
            synchronized(locker1) {
                println("Поток 2: Начал удерживать замок 1")
            }
        }
    }

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println("Оба потока завершены")
}

// Поток 1: Начал удерживать замок 1
// Поток 2: Начал удерживать замок 2

// Пояснение:
// Мы ждем 1 секунду, чтобы два потока взяли 1 и 2 замок.
// Затем потоки берут замки друг друга и впадают в бесконечно ожидание