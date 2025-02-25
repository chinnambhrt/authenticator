package online.chinnam.android.authenticator.repository

import android.app.Application
import online.chinnam.android.authenticator.data.AuthenticatorDatabase
import online.chinnam.android.authenticator.models.AuthenticatorSettings

class SettingsRepository(private val application: Application) {

    val database = AuthenticatorDatabase.getDatabase(application).settings()

    fun loadSettings(): AuthenticatorSettings {
        val settings = database.getSettings().first()
        return AuthenticatorSettings.from(settings.content)
    }

    fun saveSettings(settings: AuthenticatorSettings) {
        database.updateSettings(settings.json())
    }

}