package be.marche.mercredi.sync

import androidx.lifecycle.ViewModel
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.tuteur.TuteurRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class SyncViewModel(
    val mercrediService: MercrediService,
    val enfantRepository: EnfantRepository,
    val tuteurRepository: TuteurRepository,
    val mercrediRepository: MercrediRepository
) :
    ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshData() {

        viewModelScope.launch {

            Timber.i("Current Thread: %s", Thread.currentThread())
            val request = mercrediService.getAllData()
            val response = request.await()

            response.let {
                Timber.i("Current Thread: %s", Thread.currentThread())

                Timber.i("all data ${it}")

                enfantRepository.insertEnfants(it.enfants)
                tuteurRepository.insert(it.tuteur)
                mercrediRepository.insertEcoles(it.ecoles)
                mercrediRepository.insertAnneesScolaires(it.annees)
                mercrediRepository.insertJours(it.jours)
            }
        }


    }

}