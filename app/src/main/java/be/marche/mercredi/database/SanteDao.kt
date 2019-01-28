package be.marche.mercredi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.marche.mercredi.entity.SanteFiche
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse

@Dao
interface SanteDao {

    @Query("SELECT * FROM santequestion")
    fun getAllQuestions(): LiveData<List<SanteQuestion>>

    @Query("SELECT * FROM SanteReponse WHERE santeFicheId = :santeFicheId")
    fun getReponsesBySanteFicheId(santeFicheId: Int): LiveData<List<SanteReponse>>

    @Query("SELECT * FROM SanteFiche WHERE enfantId = :enfantId LIMIT 0,1")
    fun getSanteFicheByEnfantId(enfantId: Int): LiveData<SanteFiche>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions: List<SanteQuestion>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReponses(santeReponses: List<SanteReponse>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSanteFiches(santeFiches: List<SanteFiche>)

    @Query("SELECT * FROM santequestion WHERE id = :questionId")
    fun getQuestionById(questionId: Int): LiveData<SanteQuestion>
}