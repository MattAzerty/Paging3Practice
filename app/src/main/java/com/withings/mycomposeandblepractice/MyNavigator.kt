package com.withings.mycomposeandblepractice

import androidx.compose.runtime.Stable
import androidx.navigation.NavOptionsBuilder
import com.withings.mycomposeandblepractice.utils.DestinationRouteString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class MyNavigator @Inject constructor() {

    private val _actions = MutableSharedFlow<Action>(
        replay = 0,
        extraBufferCapacity = 10
    )
    internal val actions: Flow<Action> = _actions

    fun navigate(destination: DestinationRouteString, navOptions: NavOptionsBuilder.() -> Unit = {}) {
        _actions.tryEmit(
            Action.Navigate(destination = destination, navOptions = navOptions)
        )
    }

    fun back() {
        _actions.tryEmit(Action.Back)
    }

    internal sealed class Action {
        data class Navigate(
            val destination: DestinationRouteString,
            val navOptions: NavOptionsBuilder.() -> Unit
        ) : Action()

        object Back : Action()
    }
}