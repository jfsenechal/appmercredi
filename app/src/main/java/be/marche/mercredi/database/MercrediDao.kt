package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Jour

@Dao
interface MercrediDao {

    @Query("SELECT * FROM ecole")
    fun getAllEcoles(): LiveData<List<Ecole>>

    @Query("SELECT * FROM ecole WHERE id = :id")
    fun getEcoleById(id: Int): LiveData<Ecole>

    @Query("SELECT * FROM jour")
    fun getAllJours(): LiveData<Jour>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEcoless(ecoles: List<Ecole>)

}