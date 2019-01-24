package be.marche.mercredi.tuteur

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.Tuteur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TuteurViewModel(
    private val tuteurRepository: TuteurRepository
) :
    ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [TuteurViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getTuteurById(tuteurId: Int): LiveData<Tuteur> {
        return tuteurRepository.getTuteurById(tuteurId)
    }

    fun saveReal(tuteur: Tuteur) {
        viewModelScope.launch {
            tuteurRepository.saveReal(tuteur)
        }
    }

    fun save(tuteur: Tuteur) {
        viewModelScope.launch {
            tuteurRepository.update(tuteur)
        }
    }

    var tuteur: LiveData<Tuteur>? = null
}