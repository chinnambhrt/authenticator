package online.chinnam.android.authenticator.controllers

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import online.chinnam.android.authenticator.activity.AddTotpActivity
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.iface.ILogger
import online.chinnam.android.authenticator.iface.IState
import online.chinnam.android.authenticator.models.AuthenticatorApplicationConfig
import online.chinnam.android.authenticator.repository.SettingsRepository

class SettingsController(private val application: Application) : AndroidViewModel(application),
    IController, ILogger {


    private val settingsRepository = SettingsRepository(application)

    private val state: MutableStateFlow<State> =
        MutableStateFlow(State())

    init {
        loadApplicationConfiguration()
    }


    /**
     * Load the application configuration
     */
    private fun loadApplicationConfiguration(){
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settingsRepository.loadSettings()
            state.value = state.value.copy(preference = settings)
        }
    }


    /**
     * Update the preferences
     */
    fun updatePreferences(settings: AuthenticatorApplicationConfig) {
        log("Updating preferences: $settings")
        state.value = state.value.copy(preference = settings)
        saveSettings(settings)
    }

    /**
     * Save the settings to the database
     */
    private fun saveSettings(settings: AuthenticatorApplicationConfig) {
        log("Saving settings: $settings")
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveSettings(settings)
        }
    }

    override fun getState(): StateFlow<State> {
        return state.asStateFlow()
    }

    /**
     * Access a pro feature, when pro feature is not enabled, show a toast
     */
    fun proFeature(s: String, settings: AuthenticatorApplicationConfig) {
        log("Pro feature: $s")
        if (!state.value.proEnabled) {
            Toast.makeText(application, "Update to pro to access this feature", Toast.LENGTH_SHORT)
                .show()
            return
        }
        updatePreferences(settings)
    }

    /**
     * Add an account to the list
     */
    fun addAccount() {
        val intent = Intent(application, AddTotpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }

    data class State(
        val isLoading: Boolean = false,
        val preference: AuthenticatorApplicationConfig = AuthenticatorApplicationConfig(),
        val proEnabled: Boolean = false
    ) : IState
}