package be.marche.mercredi.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    val userViewModel: UserViewModel by sharedViewModel()
    val loginViewModel: LoginViewModel by sharedViewModel()

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginViewModel.getState().observe(this, Observer { updateUi(it!!) })

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(username, password)
        }
    }

    private fun updateUi(state: LoginViewModelState) {
        return when (state) {
            LoginViewModelStateSuccess -> {
                errorMessageTextView.visibility = View.INVISIBLE
                loginButton.isEnabled = state.loginButtonEnabled
                Toast.makeText(context, "Logged! Loading next Activity...", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_loginFragment_to_menuFragment)
            }
            is LoginViewModelStateFailure -> {
                loginButton.isEnabled = state.loginButtonEnabled
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = state.errorMessage
            }
        }
    }

}