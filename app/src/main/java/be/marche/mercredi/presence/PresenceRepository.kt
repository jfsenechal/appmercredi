package be.marche.mercredi.presence

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.PresenceDao
import be.marche.mercredi.entity.Presence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent

class PresenceRepository(val presenceDao: PresenceDao) : KoinComponent {

    fun getAllPresences(): LiveData<List<Presence>> {
        return presenceDao.getAllPresences()
    }

    fun getPresenceById(presenceId: Int): LiveData<Presence> {
        return presenceDao.getPresenceById(presenceId)
    }

    suspend fun insertPresences(presences: List<Presence>) {
        withContext(Dispatchers.IO) {
            presenceDao.insertPresences(presences)
        }
    }

    suspend fun updatePresence(presence: Presence) {
        withContext(Dispatchers.IO) {
            presenceDao.updatePresences(listOf(presence))
        }
    }
}