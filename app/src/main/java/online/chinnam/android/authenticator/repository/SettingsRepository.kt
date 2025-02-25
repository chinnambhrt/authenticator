package online.chinnam.android.authenticator.repository

import android.app.Application
import online.chinnam.android.authenticator.data.AuthenticatorDatabase
import online.chinnam.android.authenticator.entity.SettingsEntity
import online.chinnam.android.authenticator.models.AuthenticatorSettings

class SettingsRepository(private val application: Application) {

    val database = AuthenticatorDatabase.getDatabase(application).settings()

    fun get(): List<SettingsEntity> {
        return database.getSettings()
    }

    fun loadSettings(): AuthenticatorSettings {
        val settings = database.getSettings().firstOrNull()
        return AuthenticatorSettings.from(settings?.content?:"{}")
    }

    fun saveSettings(settings: AuthenticatorSettings) {
        database.updateSettings(settings.json())
    }

    fun insert(settings: AuthenticatorSettings) {
        database.insert(SettingsEntity(content = settings.json()))
    }

}