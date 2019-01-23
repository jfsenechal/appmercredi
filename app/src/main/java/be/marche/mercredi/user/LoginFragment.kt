package be.marche.mercredi.user

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    val userViewModel: UserViewModel by sharedViewModel()
    val loginViewModel: LoginViewModel by sharedViewModel()
    lateinit var accountManager: AccountManager

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(be.marche.mercredi.R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        accountManager = AccountManager.get(context) // "this" references the current Context

        userViewModel.user?.observe(this, Observer {
            //todo token toujours valide ?
            val token = it?.token
            //  token = null
            if (token != null) {
                findNavController().navigate(be.marche.mercredi.R.id.action_loginFragment_to_menuFragment)
            }
        })

        loginViewModel.getState().observe(this, Observer { updateUi(it!!) })

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.loginReal(username, password)
        }
    }

    private fun updateUi(state: LoginViewModelState) {
        return when (state) {
            LoginViewModelStateSuccess -> {
                errorMessageTextView.visibility = View.INVISIBLE
                loginButton.isEnabled = state.loginButtonEnabled

                findNavController().navigate(be.marche.mercredi.R.id.action_loginFragment_to_menuFragment)
            }
            is LoginViewModelStateFailure -> {
                loginButton.isEnabled = state.loginButtonEnabled
                errorMessageTextView.visibility = View.VISIBLE
                errorMessageTextView.text = state.errorMessage
            }
        }
    }

    private fun createAccount(mUsername: String, your_account_type: String, mPassword: String) {
        val accounts = accountManager.getAccountsByType("com.google")

        val account = Account(mUsername, your_account_type)
      //  accountManager.addAccountExplicitly(account, mPassword, null)
    }

}