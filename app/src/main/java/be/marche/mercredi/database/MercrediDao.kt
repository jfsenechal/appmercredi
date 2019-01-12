package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.marche.mercredi.entity.AnneeScolaire
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Jour

@Dao
interface MercrediDao {

    /**
     * Ecole
     */
    @Query("SELECT * FROM ecole")
    fun getAllEcoles(): LiveData<List<Ecole>>

    @Query("SELECT * FROM ecole WHERE id = :ecoleId")
    fun getEcoleById(ecoleId: Int): LiveData<Ecole>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEcoless(ecoles: List<Ecole>)

    /**
     * Jours de garde
     */
    @Query("SELECT * FROM jour")
    fun getAllJours(): LiveData<List<Jour>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJours(jours: List<Jour>)

    @Query("SELECT * FROM jour WHERE id = :jourId")
    fun getJourById(jourId: Int): LiveData<Jour>

    /**
     * Annees scolaires
     */
    @Query("SELECT * FROM anneescolaire")
    fun getAllAnneesScolaires(): LiveData<List<AnneeScolaire>>

    @Query("SELECT * FROM anneescolaire WHERE id = :anneeId")
    fun getAnneeScolaireById(anneeId: Int): LiveData<AnneeScolaire>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAneesScolaires(annees: List<AnneeScolaire>)


}