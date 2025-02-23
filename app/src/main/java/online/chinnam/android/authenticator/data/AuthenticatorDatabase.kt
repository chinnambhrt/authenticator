package online.chinnam.android.authenticator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import online.chinnam.android.authenticator.data.dao.TotpEntityDAO
import online.chinnam.android.authenticator.entity.TotpEntity

@Database(
    version = 1,
    entities = [TotpEntity::class],
    exportSchema = false
)
abstract class AuthenticatorDatabase: RoomDatabase() {

    abstract fun totp(): TotpEntityDAO

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