package be.marche.mercredi.presence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Jour
import kotlinx.android.synthetic.main.fragment_jour_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PresenceFragment : Fragment(), JourListAdapter.JourListAdapterListener {

    private var listener: JourListAdapter.JourListAdapterListener? = null
    private lateinit var jourListAdapter: JourListAdapter
    private lateinit var jours: MutableList<Jour>
    private var tracker: SelectionTracker<Long>? = null

    val viewModelPresence: PresenceViewModel by inject()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance() = PresenceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jour_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!::jours.isInitialized) {
            jours = mutableListOf()
        }

        listener = this
        jourListAdapter = JourListAdapter(jours, listener)

        recyclerViewJourList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jourListAdapter
        }

        viewModelPresence.jours.observe(this, Observer { newJours -> UpdateUi(newJours) })

    }

    private fun UpdateUi(newJours: List<Jour>) {
        jours.clear()
        jours.addAll(newJours)
        jourListAdapter.notifyDataSetChanged()
    }

    override fun onJourSelected(jour: Jour) {

    }

}
