package be.marche.mercredi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import be.marche.mercredi.enfant.EnfantRepository
import be.marche.mercredi.entity.AnneeScolaire
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.repository.MercrediRepository
import be.marche.mercredi.repository.MercrediService
import be.marche.mercredi.tuteur.TuteurRepository
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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

    var ecole: LiveData<Ecole>? = null
    val ecoles = mercrediRepository.getAllEcoles()

    val anneesScolaires = mercrediRepository.getAllAnneesScolaires()

    fun getEcoleById(ecoleId: Int): LiveData<Ecole> {
        return mercrediRepository.getEcoleById(ecoleId)
    }

    fun getAnneeScolaireByName(name: String): LiveData<AnneeScolaire> {
        return mercrediRepository.getAnneeScolaireByName(name)
    }

    fun uploadImage(imageName: String, requestBody: RequestBody) {
        viewModelScope.launch {

            val request = mercrediService.uploadImage(requestBody)
            request.enqueue(object : Callback<okhttp3.Response> {
                override fun onFailure(call: Call<okhttp3.Response>, t: Throwable) {

                }

                override fun onResponse(call: Call<okhttp3.Response>, response: Response<okhttp3.Response>) {
                    response.let {
                        val data = it.body()

                        Timber.i("zeze reponse ok ${response.body()}")


                    }
                }
            })
        }
    }
}