package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import be.marche.mercredi.entity.Tuteur

@Dao
interface TuteurDao {

    @Query("SELECT * FROM tuteur")
    fun getAllTuteurs(): LiveData<List<Tuteur>>

    @Query("SELECT * FROM tuteur WHERE id = :id")
    fun getTuteurById(id: Int): LiveData<Tuteur>

    @Query("SELECT * FROM tuteur WHERE id = :id")
    fun getTuteurById2(id: Int): Tuteur

    @Insert
    fun insertTuteurs(tuteurs: List<Tuteur>)

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertTuteur(tuteur: Tuteur)

    @Update
    fun updateTuteurs(tuteurs: List<Tuteur>): Int
}