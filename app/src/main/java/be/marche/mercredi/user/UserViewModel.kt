package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import be.marche.mercredi.entity.User
import be.marche.mercredi.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var user: LiveData<User>? = null
    var userStatic: User? = null

    init {
        user = userRepository.getOneUser()
        userStatic = user!!.value
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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
