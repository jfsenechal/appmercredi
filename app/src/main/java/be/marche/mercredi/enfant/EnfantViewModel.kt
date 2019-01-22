package be.marche.mercredi.enfant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.repository.MercrediService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class EnfantViewModel(
    private val enfantRepository: EnfantRepository,
    private val mercrediService: MercrediService
) : ViewModel() {

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

    fun saveReal(enfant: Enfant, token: String) {

        viewModelScope.launch {

            val request = mercrediService.updateEnfant(token, enfant.id, enfant)
            request.enqueue(object : Callback<Enfant> {
                override fun onFailure(call: Call<Enfant>, t: Throwable) {

                }

                override fun onResponse(call: Call<Enfant>, response: Response<Enfant>) {
                    response.let {
                        val user = it.body()
                        if (user != null) {
                            Timber.i("zeze reponse enfant ok ${response.body()}")
                            save(user)

                        } else {
                            Timber.i("zeze reponse enfant ko ${response.body()}")

                        }
                    }
                }
            })
        }

    }

}