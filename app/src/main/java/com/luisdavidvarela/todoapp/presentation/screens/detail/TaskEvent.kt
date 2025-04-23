package com.luisdavidvarela.todoapp.presentation.screens.detail

sealed interface TaskEvent {
    data object TaskCreated : TaskEvent
}