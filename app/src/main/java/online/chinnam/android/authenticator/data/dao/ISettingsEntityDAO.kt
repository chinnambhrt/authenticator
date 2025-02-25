package online.chinnam.android.authenticator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import online.chinnam.android.authenticator.entity.SettingsEntity

@Dao
interface ISettingsEntityDAO {

    @Query("SELECT * FROM settings")
    fun getSettings(): List<SettingsEntity>

    @Query("UPDATE settings SET content = :content WHERE id = 1")
    fun updateSettings(content: String)

    @Insert
    fun insert(settings: SettingsEntity): Long


}