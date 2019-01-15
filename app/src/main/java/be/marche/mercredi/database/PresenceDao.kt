package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import be.marche.mercredi.entity.Presence

@Dao
interface PresenceDao {

    @Query("SELECT * FROM presence")
    fun getAllPresences(): LiveData<List<Presence>>

    @Query("SELECT * FROM presence WHERE id = :id")
    fun getPresenceById(id: Int): LiveData<Presence>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPresences(presences: List<Presence>)

    @Update
    fun updatePresences(presences: List<Presence>): Int
}