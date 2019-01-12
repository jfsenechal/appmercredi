package be.marche.mercredi.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import be.marche.mercredi.MainViewModel
import be.marche.mercredi.R
import be.marche.mercredi.utils.ConnectivityLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            ConnectivityLiveData(application).observe(this, Observer { connected ->
                when (connected) {
                    true -> {
                        mainViewModel.refreshData()
                        //  infoMessage.visibility = View.INVISIBLE
                    }
                    false -> {
                        //  infoMessage.visibility = View.VISIBLE
                        //  infoMessage.text = getString(R.string.message_no_connectivity)
                    }
                }
            })

            val host = NavHostFragment.create(R.navigation.navigation)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, host)
                .setPrimaryNavigationFragment(host).commit()
        }
    }
}
