package be.marche.mercredi.repository

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.MercrediDao
import be.marche.mercredi.entity.AnneeScolaire
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Jour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class MercrediRepository(val mercrediDao: MercrediDao) : KoinComponent {

    fun getAllEcoles(): LiveData<List<Ecole>> {
        return mercrediDao.getAllEcoles()
    }

    fun getEcoleById(ecoldeId: Int): LiveData<Ecole> {
        return mercrediDao.getEcoleById(ecoldeId)
    }

    fun getAllAnneesScolaires(): LiveData<List<AnneeScolaire>> {
        return mercrediDao.getAllAnneesScolaires()
    }

    fun getAnneeScolaireByName(name: String): LiveData<AnneeScolaire> {
        return mercrediDao.getAnneeScolaireByName(name)
    }

    fun getJoursByEnfantId(enfantId: Int): LiveData<List<Jour>> {
        return mercrediDao.getJoursByEnfantId(enfantId)
    }

    fun getJourById(jourId: Int): LiveData<Jour> {
        return mercrediDao.getJourById(jourId)
    }

    suspend fun insertEcoles(ecoles: List<Ecole>) {
        withContext(Dispatchers.IO) {
            mercrediDao.insertEcoless(ecoles)
        }
    }

    suspend fun insertAnneesScolaires(annees: List<AnneeScolaire>) {
        withContext(Dispatchers.IO) {
            mercrediDao.insertAneesScolaires(annees)
        }
    }

    suspend fun insertJours(jours: List<Jour>) {
        withContext(Dispatchers.IO) {
            mercrediDao.insertJours(jours)
        }
    }

}