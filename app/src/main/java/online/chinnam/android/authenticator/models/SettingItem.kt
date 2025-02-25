package online.chinnam.android.authenticator.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItem(
    val key: String,
    val title: String,
    val description: String,
    val value: String = "",
    val type: SettingsType = SettingsType.REGULAR,
    val icon: ImageVector? = null,
    val onClick: (() -> Unit)? = null
)
