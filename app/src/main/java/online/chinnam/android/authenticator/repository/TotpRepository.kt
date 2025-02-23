package online.chinnam.android.authenticator.repository

import android.app.Application
import online.chinnam.android.authenticator.data.AuthenticatorDatabase
import online.chinnam.android.authenticator.entity.TotpEntity

class TotpRepository(private val application: Application) {

    val database = AuthenticatorDatabase.getDatabase(application).totp()

    /**
     * Fetch all the TOTP entities from the database
     */
    fun all(): List<TotpEntity> {
        return database.getAll()
    }

    /**
     * Fetch a TOTP entity by its ID
     */
    fun byId(id: Int): TotpEntity {
        return database.getById(id)
    }

    /**
     * Insert a TOTP entity into the database
     */
    fun insert(totpEntity: TotpEntity): Long {
       return database.insert(totpEntity)
    }

    /**
     * Delete a TOTP entity from the database
     */
    fun delete(totpEntity: TotpEntity): Int {
        return database.delete(totpEntity)
    }

    /**
     * Update a TOTP entity in the database
     */
    fun update(totpEntity: TotpEntity): Int {
        return database.update(totpEntity)
    }

}