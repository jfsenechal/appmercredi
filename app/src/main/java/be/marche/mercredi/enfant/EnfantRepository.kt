package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import be.marche.mercredi.database.EnfantDao
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class EnfantRepository(val enfantDao: EnfantDao) : KoinComponent {

    val mercrediService: MercrediService by inject()

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    fun getAllEnfants(): LiveData<List<Enfant>> {
        return enfantDao.getAllEnfants()
    }

    fun getEnfantById(enfantId: Int): LiveData<Enfant> {
        return enfantDao.getEnfantById(enfantId)
    }

    suspend fun insertEnfants(enfants: List<Enfant>) {
        withContext(Dispatchers.IO) {
            enfantDao.insertEnfants(enfants)
        }
    }

    suspend fun updateEnfant(enfant: Enfant) {
        withContext(Dispatchers.IO) {
            enfantDao.updateEnfants(listOf(enfant))
        }
    }
}