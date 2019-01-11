package be.marche.mercredi.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.MainViewModel
import be.marche.mercredi.R
import be.marche.mercredi.utils.ConnectivityLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    val mainViewModel: MainViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ConnectivityLiveData(activity?.application).observe(this, Observer { connected ->
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
    }
}