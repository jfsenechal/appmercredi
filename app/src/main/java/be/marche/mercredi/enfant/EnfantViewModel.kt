package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.Enfant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EnfantViewModel(
    private val enfantRepository: EnfantRepository
) : ViewModel() {

    private val viewModelJob = Job()
    //Dispatchers.Main : informer le système Android que nous devrons mettre à jour le thread d'interface utilisateur
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    lateinit var enfant: LiveData<Enfant>
    var enfantPhoto: MutableLiveData<String>? = null

    init {

    }

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
        }
    }

    fun saveReal(enfant: Enfant) {
        viewModelScope.launch {
            enfantRepository.saveReal(enfant)
        }
    }

}