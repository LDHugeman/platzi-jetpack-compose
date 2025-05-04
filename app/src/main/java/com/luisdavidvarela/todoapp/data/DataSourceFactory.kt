package com.luisdavidvarela.todoapp.data

import android.content.Context
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher

object DataSourceFactory {
    fun createDataSource(
        context: Context,
        dispatcher: CoroutineDispatcher
    ) : TaskLocalDataSource {
        val dataBase = TodoDatabase.getDatabase(context)
        return RoomTaskLocalDataSource(dataBase.taskDao(), dispatcher)
    }
}