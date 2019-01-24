package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var user: LiveData<User>? = null
    var userStatic: User? = null
    var token: String? = null

    fun token(): String? {
        return token
    }

    fun refreshToken(): String? {
        return "123456"
    }

    init {
        user = userRepository.getOneUser()
        userStatic = user!!.value
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getOneUser2(): User? {
        return userRepository.getOneUser2()
    }

    fun getUserById(userId: Int): LiveData<User> {
        return userRepository.getUserId(userId)
    }

    fun insertPresence(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}
