package online.chinnam.android.authenticator.models

data class AuthenticatorSettings(
    val darkMode: Boolean = false,
    val unlockToOpen: Boolean = false,
    val autoLock: Boolean = false,
    val autoLockTime: Int = 0,
    val tapToCopy: Boolean = false,
    val exportData: Boolean = false,
    val importData: Boolean = false,
    val allowEdits: Boolean = false,
)