package com.luisdavidvarela.todoapp.data.di

import android.content.Context
import androidx.room.Room
import com.luisdavidvarela.todoapp.data.RoomTaskLocalDataSource
import com.luisdavidvarela.todoapp.data.TaskDao
import com.luisdavidvarela.todoapp.data.TodoDatabase
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext
        context: Context,
    ): TodoDatabase  {
        return  Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        dataBase: TodoDatabase
    ) :TaskDao = dataBase.taskDao()

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        taskDao: TaskDao,
        dispatcher: CoroutineDispatcher
    ) : TaskLocalDataSource = RoomTaskLocalDataSource(taskDao, dispatcher)
}