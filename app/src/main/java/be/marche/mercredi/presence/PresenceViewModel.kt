package be.marche.mercredi.presence

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Jour
import be.marche.mercredi.entity.Presence
import be.marche.mercredi.entity.ResponseRetrofit
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PresenceViewModel(
    private val mercrediRepository: MercrediRepository,
    private val presenceRepository: PresenceRepository,
    private val mercrediService: MercrediService
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var jour: LiveData<Jour>? = null
    var presences: LiveData<List<Presence>>? = null

    init {
        presences = presenceRepository.getAllPresences()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val jours: LiveData<List<Jour>> = mercrediRepository.getAllJours()

    fun getJourById(jourId: Int): LiveData<Jour> {
        return mercrediRepository.getJourById(jourId)
    }

    fun insertPresence(presences: List<Presence>) {
        viewModelScope.launch {
            presenceRepository.insertPresences(presences)
        }
    }

    fun insertPresenceReal(enfant: Enfant, jours: List<Int>) {

        viewModelScope.launch {

            val request = mercrediService.newPresences( enfant.id, jours)
            request.enqueue(object : Callback<ResponseRetrofit> {
                override fun onFailure(call: Call<ResponseRetrofit>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseRetrofit>, response: Response<ResponseRetrofit>) {
                    response.let {
                        val presences = it.body()
                        if (presences != null) {
                            Timber.i("zeze reponse presence ok ${response.body()}")
                            //to refresh data
                        } else {
                            Timber.i("zeze reponse presence ko ${response.body()}")
                        }
                    }
                }
            })
        }
    }
}
