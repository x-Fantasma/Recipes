package co.recipes.domain.repository.auth.login

import android.util.Log
import co.recipes.core.Result
import co.recipes.core.exceptions.LoginException
import co.recipes.core.exceptions.RegisterException
import co.recipes.data.model.user.User
import co.recipes.data.remote.user.UserDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val userDao: UserDao): LoginRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(user: User, password: String): Result<User?> = try {
        auth.signInWithEmailAndPassword(user.email, password).addOnSuccessListener{}.await()
        userDao.getUserById(auth.currentUser!!.uid)
    }catch (e: Exception) {
        throw LoginException("Algo salio mal al logearte")
    }
}