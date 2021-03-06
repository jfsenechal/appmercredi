package be.marche.mercredi.sante

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.SanteDao
import be.marche.mercredi.entity.SanteFiche
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class SanteRepository(
    private val santeDao: SanteDao,
    private val mercrediService: MercrediService
) : KoinComponent {

    fun getAllQuestions(): LiveData<List<SanteQuestion>> {
        return santeDao.getAllQuestions()
    }

    fun getReponsesBySanteFicheId(santeFicheId: Int): LiveData<List<SanteReponse>> {
        return santeDao.getReponsesBySanteFicheId(santeFicheId)
    }

    fun getReponseBySanteFicheIdAndQuestionId(santeFicheId: Int, questionId: Int): LiveData<SanteReponse> {
        return santeDao.getReponseBySanteFicheIdAndQuestionId(santeFicheId, questionId)
    }

    fun getSanteFicheByEnfantId(enfantId: Int): LiveData<SanteFiche> {
        return santeDao.getSanteFicheByEnfantId(enfantId)
    }

    suspend fun insertQuestions(questions: List<SanteQuestion>) {
        withContext(Dispatchers.IO) {
            santeDao.insertQuestions(questions)
        }
    }

    suspend fun insertReponses(reponses: List<SanteReponse>?) {
        withContext(Dispatchers.IO) {
            santeDao.insertReponses(reponses)
        }
    }

    suspend fun insertReponse(reponse: SanteReponse) {
        withContext(Dispatchers.IO) {
            santeDao.insertReponse(reponse)
        }
    }

    suspend fun insertSanteFiches(santeFiches: List<SanteFiche>) {
        withContext(Dispatchers.IO) {
            santeDao.insertSanteFiches(santeFiches)
        }
    }

    fun getQuestionById(questionId: Int): LiveData<SanteQuestion> {
        return santeDao.getQuestionById(questionId)
    }

    suspend fun insertSanteFiche(santeFiche: SanteFiche) {
        withContext(Dispatchers.IO) {
            santeDao.insertSanteFiche(santeFiche)
        }
    }

}