package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.User
import be.marche.mercredi.repository.MercrediService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import timber.log.Timber
import retrofit2.Callback
import retrofit2.Response

sealed class LoginViewModelState(
    val errorMessage: String = "",
    val loginButtonEnabled: Boolean = false
)

object LoginViewModelStateSuccess : LoginViewModelState()
class LoginViewModelStateFailure(errorMessage: String) : LoginViewModelState(errorMessage, true)

class LoginViewModel(
    private val userRepository: UserRepository,
    private val mercrediService: MercrediService
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val state = MutableLiveData<LoginViewModelState>()

    fun getState(): LiveData<LoginViewModelState> = state

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun loginReal(username: String, password: String) {

        viewModelScope.launch {

            val request = mercrediService.login(username, password)
            request.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    state.value = LoginViewModelStateFailure("Crash system")
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.let {
                        val user = it.body()
                        if (user != null) {
                            Timber.i("zeze reponse ok ${response.body()}")
                            insertUser(user)
                            state.value = LoginViewModelStateSuccess
                        } else {
                            Timber.i("zeze reponse ko ${response.body()}")
                            state.value = LoginViewModelStateFailure("Wrong username / password!")
                        }
                    }
                }
            })
        }
    }

}