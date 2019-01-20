package be.marche.mercredi.repository

import androidx.lifecycle.LiveData
import be.marche.mercredi.database.UserDao
import be.marche.mercredi.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent

class UserRepository(val userDao: UserDao) : KoinComponent {

    fun getAllUser(): LiveData<List<User>> {
        return userDao.getAllUser()
    }

    fun getOneUser(): LiveData<User> {
        return userDao.getOneUser()
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