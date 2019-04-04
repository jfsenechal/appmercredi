package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.marche.mercredi.entity.User
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

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

    fun getOneUser2(): User? {
        return userRepository.getOneUser2()
    }

    fun getUserById(userId: Int): LiveData<User> {
        return userRepository.getUserId(userId)
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}
