package be.marche.mercredi.presence

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.PresenceDao
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Presence
import be.marche.mercredi.entity.ResponseRetrofit
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PresenceRepository(
    private val presenceDao: PresenceDao,
    private val mercrediService: MercrediService
) : KoinComponent {

    fun getAllPresences(): LiveData<List<Presence>> {
        return presenceDao.getAllPresences()
    }

    fun getPresenceByEnfantId(enfantId: Int): LiveData<List<Presence>> {
        return presenceDao.getPresenceByEnfantId(enfantId)
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

    fun insertPresenceReal(enfant: Enfant, jours: List<Int>) {

        var ok: Boolean = false

        val request = mercrediService.newPresences(enfant.id, jours)
        request.enqueue(object : Callback<ResponseRetrofit> {
            override fun onFailure(call: Call<ResponseRetrofit>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseRetrofit>, response: Response<ResponseRetrofit>) {
                response.let {
                    val presences = it.body()
                    if (presences != null) {
                        Timber.i("zeze reponse presence ok ${response.body()}")
                        ok = true
                        //to refresh data
                    } else {
                        Timber.i("zeze reponse presence ko ${response.body()}")
                    }
                }
            }
        })

    }

}