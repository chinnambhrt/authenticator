package online.chinnam.android.authenticator.entity

import android.net.Uri
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.LocaleList
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "otp")
data class TotpEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val display: String,

    val issuer: String,

    val accountName: String,

    val secret: String,

    val digits: Int = 6,

    val period: Int = 30,

    val algorithm: String = "SHA1",

    val type: String = "TOTP",

    val pinned: Boolean = false,

    val createdAt: Long = Instant.now().epochSecond,

    val updatedAt: Long = Instant.now().epochSecond
){
    companion object {
        /**
         * Gets an empty TotpEntity
         */
        val EMPTY = TotpEntity(
            display = "",
            issuer = "",
            accountName = "",
            secret = ""
        )

        fun fromUrl(urlString: String): TotpEntity{
            val uri = Uri.parse(urlString)
            val label = uri.pathSegments[0] ?: ""
            val secretKey = uri.getQueryParameter("secret") ?: ""
            val interval = uri.getQueryParameter("period")?.toIntOrNull() ?: 30
            val digits = uri.getQueryParameter("digits")?.toIntOrNull() ?: 6
            val alg = uri.getQueryParameter("algorithm") ?: "SHA1"
            val issuer = uri.getQueryParameter("issuer") ?: "Unnamed"
            val timeOffset = uri.getQueryParameter("timeOffset")?.toLongOrNull() ?: 0
            var title = issuer
            if (label.isNotEmpty()) {
                title = if (label.contains(issuer, ignoreCase = true)) {
                    label
                } else {
                    "$issuer: $label".capitalize(LocaleList("en"))
                }
            }
            val algorithm = when(alg.lowercase()){
                "sha1" -> "HmacSHA1"
                "sha256" ->"HmacSHA256"
                "sha512" -> "HmacSHA512"
                else -> "HmacSHA1"
            }
            return TotpEntity(
                display = title,
                issuer = issuer,
                accountName = label,
                secret = secretKey,
                digits = digits,
                period = interval,
                algorithm = algorithm
            )
        }
    }
}
