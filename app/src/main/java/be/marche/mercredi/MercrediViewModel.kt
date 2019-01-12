package be.marche.mercredi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.tuteur.TuteurRepository

class MercrediViewModel(
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

    val ecoles = mercrediRepository.getAllEcoles()

    val anneesScolaires = mercrediRepository.getAllAnneesScolaires()


    fun getAllEcoles(): LiveData<List<Ecole>> {
        return mercrediRepository.getAllEcoles()
    }

}