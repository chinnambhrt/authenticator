package online.chinnam.android.authenticator.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.iface.ILogger
import online.chinnam.android.authenticator.iface.IState

class AddTotpController(private val application: Application) : AndroidViewModel(application),
    IController, ILogger {

    private val state = MutableStateFlow(State())

    /**
     * Get the current state of the controller
     */
    override fun getState(): StateFlow<State> {
        return state.asStateFlow()
    }


    data class State(
        val error: String = "",
    ) : IState

}