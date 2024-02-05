package com.projects.moviemanager.features.home.events

sealed class HomeEvent {
    data object LoadHome : HomeEvent()
    data object OnError : HomeEvent()
}
