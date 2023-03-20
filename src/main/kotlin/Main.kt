package indigo
import kotlin.system.exitProcess


fun List<Card>.print() = println(joinToString(" "))
fun ask(message: String) = println(message).run {
    val q = readln()
    if (q == "exit") {
        println("Game Over")
        exitProcess(0)
    }
    return@run q
}

fun main() {
    Game()
}