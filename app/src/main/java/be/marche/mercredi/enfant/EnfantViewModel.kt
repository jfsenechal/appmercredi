package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import be.marche.mercredi.entity.Enfant

class EnfantViewModel(private val enfantRepository: EnfantRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var enfant: LiveData<Enfant>? = null
    var enfantPhoto: MutableLiveData<String>? = null

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val enfants: LiveData<List<Enfant>> = enfantRepository.getAllEnfants()

    fun getEnfantById(enfantId: Int): LiveData<Enfant> {
        return enfantRepository.getEnfantById(enfantId)
    }

    fun save(enfant: Enfant) {
        viewModelScope.launch {
            enfantRepository.updateEnfant(enfant)
            //todo save to server
        }
    }

}