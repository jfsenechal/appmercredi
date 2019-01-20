package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class LoginViewModelState(
    val errorMessage: String = "",
    val loginButtonEnabled: Boolean = false
)

object LoginViewModelStateSuccess : LoginViewModelState()
class LoginViewModelStateFailure(errorMessage: String) : LoginViewModelState(errorMessage, true)

class LoginViewModel : ViewModel() {

    private val state = MutableLiveData<LoginViewModelState>()
    fun getState(): LiveData<LoginViewModelState> = state

    fun login(username: String, password: String) {
        if (username == "kotlin" && password == "rocks") {
            state.value = LoginViewModelStateSuccess
        } else {
            state.value = LoginViewModelStateFailure("Wrong username / password!")
        }
    }

}