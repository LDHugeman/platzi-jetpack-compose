package com.luisdavidvarela.todoapp.presentation.screens.home

sealed class HomeScreenEvent {
    data object UpdatedTask: HomeScreenEvent()
    data object DeletedAllTasks: HomeScreenEvent()
    data object DeletedTask: HomeScreenEvent()
}