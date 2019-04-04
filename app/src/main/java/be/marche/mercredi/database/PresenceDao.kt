package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import be.marche.mercredi.entity.Presence
import java.util.*

@Dao
interface PresenceDao {

    @Query("SELECT * FROM presence")
    fun getAllPresences(): LiveData<List<Presence>>

    @Query("SELECT * FROM presence WHERE enfantId = :enfantId ORDER BY date DESC")
    fun getPresenceByEnfantId(enfantId: Int): LiveData<List<Presence>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPresences(presences: List<Presence>)

    @Update
    fun updatePresences(presences: List<Presence>): Int

    @Query("SELECT * FROM presence WHERE date BETWEEN :from AND :to")
    fun findUsersBornBetweenDates(from: Date, to: Date): List<Presence>
}