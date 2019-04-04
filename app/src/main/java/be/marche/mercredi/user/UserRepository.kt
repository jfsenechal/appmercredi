package be.marche.mercredi.user

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.UserDao
import be.marche.mercredi.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class UserRepository(val userDao: UserDao) : KoinComponent {

    fun getOneUser(): LiveData<User> {
        return userDao.getOneUser()
    }

    fun getOneUser2(): User? {
        return userDao.getOneUser2()
    }

    fun getUserId(userId: Int): LiveData<User> {
        return userDao.getUserById(userId)
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }

}