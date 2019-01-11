package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import be.marche.mercredi.entity.Enfant

@Dao
interface EnfantDao {

    @Query("SELECT * FROM enfant")
    fun getAllEnfants(): LiveData<List<Enfant>>

    @Query("SELECT * FROM enfant WHERE id = :id")
    fun getEnfantById(id: Int): LiveData<Enfant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEnfants(enfants: List<Enfant>)

    @Update
    fun updateEnfants(enfants: List<Enfant>): Int
}