package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import be.marche.mercredi.database.EnfantDao
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class EnfantRepository(
    private val enfantDao: EnfantDao,
    private val mercrediService: MercrediService
) : KoinComponent {

    // val mercrediService: MercrediService by inject()

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    fun saveReal(enfant: Enfant) {

        var ok: Boolean = false
        viewModelScope.launch {

            val request = mercrediService.updateEnfant(enfant.id, enfant)
            request.enqueue(object : Callback<Enfant> {
                override fun onFailure(call: Call<Enfant>, t: Throwable) {

                }

                override fun onResponse(call: Call<Enfant>, response: Response<Enfant>) {
                    response.let {
                        val enfant = it.body()
                        if (enfant != null) {
                            Timber.i("zeze reponse enfant ok ${response.body()}")
                            ok = true
                        } else {
                            Timber.i("zeze reponse enfant ko ${response.body()}")
                        }
                    }
                }
            })
            if (ok)
                updateEnfant(enfant)
        }

    }
}