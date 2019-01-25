package be.marche.mercredi.sante

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.SanteFiche
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SanteViewModel(
    private val santeRepository: SanteRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var santeReponses: LiveData<List<SanteReponse>>? = null
    var santeQuestions: LiveData<List<SanteQuestion>>? = null

    init {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getAllQuestions(): LiveData<List<SanteQuestion>> {
        return santeRepository.getAllQuestions()
    }

    fun getReponsesBySanteFicheId(enfantId: Int): LiveData<List<SanteReponse>> {
        return santeRepository.getReponsesBySanteFicheId(enfantId)
    }

    fun getSanteFicheByEnfantId(enfantId: Int): LiveData<SanteFiche> {
        return santeRepository.getSanteFicheByEnfantId(enfantId)
    }

    fun insertSanteQuestions(questions: List<SanteQuestion>) {
        viewModelScope.launch {
            santeRepository.insertQuestions(questions)
        }
    }

    fun insertReponses(reponses: List<SanteReponse>) {
        viewModelScope.launch {
            santeRepository.insertReponses(reponses)
        }
    }

    fun insertSanteFiches(santeFiches: List<SanteFiche>) {
        viewModelScope.launch {
            santeRepository.insertSanteFiches(santeFiches)
        }
    }

}
