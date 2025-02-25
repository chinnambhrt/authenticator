package online.chinnam.android.authenticator.controllers

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.iface.ILogger
import online.chinnam.android.authenticator.iface.IState
import online.chinnam.android.authenticator.models.AuthenticatorSettings

class SettingsController(private val application: Application) : AndroidViewModel(application),
    IController, ILogger {

    private val state: MutableStateFlow<State> = MutableStateFlow(State())

    private val preferences: MutableStateFlow<AuthenticatorSettings> =
        MutableStateFlow(AuthenticatorSettings())

    fun updatePreferences(settings: AuthenticatorSettings) {
        log("Updating preferences: $settings")
        state.value = state.value.copy(preference = settings)
    }

    override fun getState(): StateFlow<State> {
        return state.asStateFlow()
    }

    fun proFeature(s: String, settings: AuthenticatorSettings) {
        log("Pro feature: $s")
        if(!state.value.proEnabled) {
            Toast.makeText(application, "Update to pro to access this feature", Toast.LENGTH_SHORT)
                .show()
            return
        }
        updatePreferences(settings)
    }

    data class State(
        val isLoading: Boolean = false,
        val preference: AuthenticatorSettings = AuthenticatorSettings(),
        val proEnabled: Boolean = false
    ) : IState
}