package com.luisdavidvarela.todoapp.data

import com.luisdavidvarela.todoapp.domain.Task
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomTaskLocalDataSource @Inject constructor(
    private val taskDao: TaskDao,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
) : TaskLocalDataSource{
    override val taskFlow: Flow<List<Task>>
        get() = taskDao.getAllTasks().map {
            it.map {taskEntity -> taskEntity.toTask() }
        }.flowOn(dispatcherIO)

    override suspend fun addTask(task: Task) = withContext(dispatcherIO) {
        taskDao.upsertTask(TaskEntity.fromTask(task))
    }

    override suspend fun updateTask(updatedTask: Task) = withContext(dispatcherIO) {
        taskDao.upsertTask(TaskEntity.fromTask(updatedTask))
    }

    override suspend fun removeTask(task: Task) = withContext(dispatcherIO) {
        taskDao.deleteTaskById(task.id)
    }

    override suspend fun removeAllTasks() = withContext(dispatcherIO) {
        taskDao.deleteAllTasks()
    }

    override suspend fun getTaskById(id: String): Task? = withContext(dispatcherIO) {
        taskDao.getTaskById(id)?.toTask()
    }

}