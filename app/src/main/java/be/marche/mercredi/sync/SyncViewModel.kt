package be.marche.mercredi.sync

import androidx.lifecycle.ViewModel
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.presence.PresenceRepository
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.tuteur.TuteurRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SyncViewModel(
    val mercrediService: MercrediService,
    val enfantRepository: EnfantRepository,
    val tuteurRepository: TuteurRepository,
    val mercrediRepository: MercrediRepository,
    val presenceRepository: PresenceRepository
) :
    ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var token: String? = null

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshData() {

        viewModelScope.launch {

            val request = mercrediService.getAllData()

            val response = request.await()

            response.let {
                mercrediRepository.insertEcoles(it.ecoles)
                mercrediRepository.insertAnneesScolaires(it.annees)
                mercrediRepository.insertJours(it.jours)
                enfantRepository.insertEnfants(it.enfants)
                tuteurRepository.insertTuteurs(it.tuteur)
                presenceRepository.insertPresences(it.presences)
            }

        }


    }

}