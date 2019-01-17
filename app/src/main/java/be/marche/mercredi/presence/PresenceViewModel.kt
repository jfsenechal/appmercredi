package be.marche.mercredi.presence

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.Jour
import be.marche.mercredi.entity.Presence
import be.marche.mercredi.repository.MercrediRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PresenceViewModel(
    private val mercrediRepository: MercrediRepository,
    val presenceRepository: PresenceRepository
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
}
