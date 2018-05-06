package io.github.aleksandersh.simpleasync

import java.util.*

class TasksBuffer(private val bufferSize: Int) {

    private val tasks: LinkedList<TaskSession> = LinkedList()

    fun add(task: TaskSession) {
        if (tasks.size >= bufferSize) {
            tasks.removeFirst().cancel()
        }
        tasks.addLast(task)
    }

    fun cancel() {
        tasks.forEach { it.cancel() }
        tasks.clear()
    }
}

fun TaskSession.putInBuffer(buffer: TasksBuffer) {
    buffer.add(this)
}
