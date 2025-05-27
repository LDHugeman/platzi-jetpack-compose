package com.luisdavidvarela.todoapp

import android.content.Context
import androidx.room.Room
import com.luisdavidvarela.todoapp.data.RoomTaskLocalDataSource
import com.luisdavidvarela.todoapp.data.TaskDao
import com.luisdavidvarela.todoapp.data.TodoDatabase
import com.luisdavidvarela.todoapp.data.di.DataModule
import com.luisdavidvarela.todoapp.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataModule::class])
class TestDataModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext
        context: Context,
    ): TodoDatabase {
        return  Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        dataBase: TodoDatabase
    ) : TaskDao = dataBase.taskDao()

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        taskDao: TaskDao,
        dispatcher: CoroutineDispatcher
    ) : TaskLocalDataSource = RoomTaskLocalDataSource(taskDao, dispatcher)
}