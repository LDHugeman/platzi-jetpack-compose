package com.luisdavidvarela.todoapp.data

import com.luisdavidvarela.todoapp.domain.Task
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import com.luisdavidvarela.todoapp.presentation.screens.home.providers.completedTask
import com.luisdavidvarela.todoapp.presentation.screens.home.providers.pendingTask
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object FakeTaskLocalDataSource: TaskLocalDataSource {

    private val _taskFlow = MutableStateFlow<List<Task>>(emptyList())

    init {
        _taskFlow.value = completedTask + pendingTask
    }

    override val taskFlow: Flow<List<Task>>
        get() = _taskFlow

    override suspend fun addTask(task: Task) {
        val tasks =  _taskFlow.value.toMutableList()
        tasks.add(task)
        delay(100)
        _taskFlow.value = tasks
    }

    override suspend fun updateTask(updatedTask: Task) {
        val tasks = _taskFlow.value.toMutableList()
        val taskIndex = tasks.indexOfFirst {  it.id == updatedTask.id }
        if (taskIndex != -1) {
            tasks[taskIndex] = updatedTask
            delay(100)
            _taskFlow.value = tasks
        }
    }

    override suspend fun removeTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        tasks.remove(task)
        delay(100)
        _taskFlow.value = tasks
    }

    override suspend fun removeAllTasks() {
        _taskFlow.value = emptyList()
    }

    override suspend fun getTaskById(id: String): Task? {
        return _taskFlow.value.firstOrNull{ it.id == id}
    }

}