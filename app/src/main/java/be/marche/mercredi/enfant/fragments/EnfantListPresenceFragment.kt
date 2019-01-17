package be.marche.mercredi.enfant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantPresenceListAdapter
import be.marche.mercredi.entity.Presence
import be.marche.mercredi.presence.PresenceViewModel
import kotlinx.android.synthetic.main.enfant_presences_fragment.*
import org.koin.android.ext.android.inject

class EnfantListPresenceFragment : Fragment(), EnfantPresenceListAdapter.EnfantPresenceListAdapterListener {

    val presenceViewModel: PresenceViewModel by inject()

    companion object {
        fun newInstance() = EnfantListFragment()
    }

    private var listener: EnfantPresenceListAdapter.EnfantPresenceListAdapterListener? = null
    private lateinit var enfantPresenceListAdapter: EnfantPresenceListAdapter
    private lateinit var presences: MutableList<Presence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.enfant_presences_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!::presences.isInitialized) {
            presences = mutableListOf()
        }

        listener = this
        enfantPresenceListAdapter = EnfantPresenceListAdapter(presences, listener)

        enfantPresencesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = enfantPresenceListAdapter
        }

        presenceViewModel.presences.observe(this, Observer { newPresences ->
            updateUi(newPresences)
        })
    }

    override fun onPresenceSelected(presence: Presence) {

    }

    private fun updateUi(newPresences: List<Presence>) {
        presences.clear()
        presences.addAll(newPresences)
        enfantPresenceListAdapter.notifyDataSetChanged()
    }
}