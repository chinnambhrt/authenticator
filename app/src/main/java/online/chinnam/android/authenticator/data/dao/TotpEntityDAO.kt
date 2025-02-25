package online.chinnam.android.authenticator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import online.chinnam.android.authenticator.entity.TotpEntity

@Dao
interface TotpEntityDAO {

    @Query("SELECT * FROM otp")
    fun getAll(): List<TotpEntity>

    @Insert
    fun insert(totp: TotpEntity): Long

    @Update
    fun update(totpEntity: TotpEntity): Int

    @Delete
    fun delete(totpEntity: TotpEntity): Int

    @Query("SELECT * FROM otp WHERE id = :id")
    fun getById(id: Int): TotpEntity

    @Query("UPDATE otp SET pinned = :pinned WHERE id = :id")
    fun updatePinned(id: Int, pinned: Boolean): Int
}