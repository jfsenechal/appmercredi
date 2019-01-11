package be.marche.mercredi.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import be.marche.mercredi.database.MercrediDao
import be.marche.mercredi.entity.Ecole
import org.koin.standalone.KoinComponent

class MercrediRepository(val mercrediDao: MercrediDao) : KoinComponent {

    fun getAllEcoles(): LiveData<List<Ecole>> {
        return mercrediDao.getAllEcoles()
    }

    fun getEcoleById(ecoldeId: Int): LiveData<Ecole> {
        return mercrediDao.getEcoleById(ecoldeId)
    }

    suspend fun insertEcoles(ecoles: List<Ecole>) {
        withContext(Dispatchers.IO) {
            mercrediDao.insertEcoless(ecoles)
        }
    }

}