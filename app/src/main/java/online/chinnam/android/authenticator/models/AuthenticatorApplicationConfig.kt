package online.chinnam.android.authenticator.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class AuthenticatorApplicationConfig(
    val darkMode: Boolean = false,
    val unlockToOpen: Boolean = false,
    val autoLock: Boolean = false,
    val autoLockTime: Int = 0,
    val tapToCopy: Boolean = false,
    val exportData: Boolean = false,
    val importData: Boolean = false,
    val allowEdits: Boolean = false,
) {
    fun json(): String {
        return Json.encodeToString(serializer(), this)
    }

    companion object {

        fun from(json: String): AuthenticatorApplicationConfig {
            return Json.decodeFromString<AuthenticatorApplicationConfig>(json)
        }
    }
}