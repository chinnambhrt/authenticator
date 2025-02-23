package online.chinnam.android.authenticator.entity

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

    val createdAt: Long = Instant.now().epochSecond,

    val updatedAt: Long = Instant.now().epochSecond
)
