package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import be.marche.mercredi.database.EnfantDao
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.repository.MercrediService
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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

    fun getEnfant(enfantId: Int): LiveData<Enfant> {
        val enfant: MutableLiveData<Enfant> = MutableLiveData()
        val call = mercrediService.getOneEnfant(enfantId, "12345")

        call.enqueue(object : Callback<Enfant> {
            override fun onResponse(call: Call<Enfant>, response: Response<Enfant>) {
                Timber.i("Current Thread: " + Thread.currentThread())

                response.body()?.let {
                    Timber.i("zeze requete ok ${response.body()}")
                    enfant.value = response.body()
                }
            }

            override fun onFailure(call: Call<Enfant>, t: Throwable) {
                Timber.i("zeze requete echouee ${t.toString()}")
            }

        })
        return enfant
    }

}