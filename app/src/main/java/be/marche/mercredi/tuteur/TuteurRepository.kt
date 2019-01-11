package be.marche.mercredi.tuteur

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import be.marche.mercredi.database.TuteurDao
import be.marche.mercredi.entity.Tuteur

class TuteurRepository(private val tuteurDao: TuteurDao) {

    suspend fun update(tuteur: Tuteur) {
        withContext(Dispatchers.IO) {
            tuteurDao.updateTuteurs(listOf(tuteur))
        }
    }

    suspend fun insert(tuteur: Tuteur) {
        withContext(Dispatchers.IO) {
            tuteurDao.insertTuteur(tuteur)
        }
    }

    fun getTuteurById(tuteurId: Int): LiveData<Tuteur> {
       return tuteurDao.getTuteurById(tuteurId)
    }

}