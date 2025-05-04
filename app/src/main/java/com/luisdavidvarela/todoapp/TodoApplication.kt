package com.luisdavidvarela.todoapp

import android.app.Application
import com.luisdavidvarela.todoapp.data.DataSourceFactory
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TodoApplication: Application() {
    val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO

    val dataSource: TaskLocalDataSource
        get() = DataSourceFactory.createDataSource(this, dispatcherIO)
}