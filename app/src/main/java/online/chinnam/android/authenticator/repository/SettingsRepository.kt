package online.chinnam.android.authenticator.repository

import android.app.Application
import online.chinnam.android.authenticator.data.AuthenticatorDatabase
import online.chinnam.android.authenticator.entity.SettingsEntity
import online.chinnam.android.authenticator.models.AuthenticatorApplicationConfig

class SettingsRepository(private val application: Application) {

    val database = AuthenticatorDatabase.getDatabase(application).settings()

    fun get(): List<SettingsEntity> {
        return database.getSettings()
    }

    fun loadSettings(): AuthenticatorApplicationConfig {
        val settings = database.getSettings().firstOrNull()
        return AuthenticatorApplicationConfig.from(settings?.content?:"{}")
    }

    fun saveSettings(settings: AuthenticatorApplicationConfig) {
        database.updateSettings(settings.json())
    }

    fun insert(settings: AuthenticatorApplicationConfig) {
        database.insert(SettingsEntity(content = settings.json()))
    }

}