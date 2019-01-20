package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.User
import be.marche.mercredi.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

sealed class LoginViewModelState(
    val errorMessage: String = "",
    val loginButtonEnabled: Boolean = false
)

object LoginViewModelStateSuccess : LoginViewModelState()
class LoginViewModelStateFailure(errorMessage: String) : LoginViewModelState(errorMessage, true)

class LoginViewModel(val userRepository: UserRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val state = MutableLiveData<LoginViewModelState>()
    fun getState(): LiveData<LoginViewModelState> = state

     fun login(username: String, password: String) {
         //todo post server
        if (username == "jf" && password == "homer") {
            val user = User(0, "senechal","jf","jf@marche.be","test_api_key")
            this.save((user))
            state.value = LoginViewModelStateSuccess
        } else {
            state.value = LoginViewModelStateFailure("Wrong username / password!")
        }
    }

    fun save(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
            //todo save to server
        }
    }

}