package online.chinnam.android.authenticator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import online.chinnam.android.authenticator.data.dao.ISettingsEntityDAO
import online.chinnam.android.authenticator.entity.SettingsEntity
import online.chinnam.android.authenticator.data.dao.TotpEntityDAO
import online.chinnam.android.authenticator.entity.TotpEntity

@Database(
    version = 2,
    entities = [TotpEntity::class, SettingsEntity::class],
    exportSchema = false
)
abstract class AuthenticatorDatabase: RoomDatabase() {

    abstract fun totp(): TotpEntityDAO

    abstract fun settings(): ISettingsEntityDAO

    companion object {
        @Volatile
        private var INSTANCE: AuthenticatorDatabase? = null

        fun getDatabase(context: Context): AuthenticatorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AuthenticatorDatabase::class.java,
                    "authenticator_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}