package com.luisdavidvarela.todoapp.presentation.screens.home

sealed class HomeScreenEvent {
    data object UpdatedTasks: HomeScreenEvent()
    data object DeletedAllTasks: HomeScreenEvent()
    data object DeletedTask: HomeScreenEvent()
}