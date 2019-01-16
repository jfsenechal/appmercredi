package be.marche.mercredi.presence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
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

        /**
         * Pour tirer le meilleur parti de la stratégie de stockage, vous devez toujours essayer de restaurer l'état du suivi
         */
        if (savedInstanceState != null)
            tracker?.onRestoreInstanceState(savedInstanceState)

        if (!::jours.isInitialized) {
            jours = mutableListOf()
        }

        listener = this
        jourListAdapter = JourListAdapter(jours, listener)

        tracker = SelectionTracker.Builder<Long>(
            "selection1",
            recyclerViewJourList,
            StableIdKeyProvider(recyclerViewJourList),
            MyLookup(recyclerViewJourList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        //jourListAdapter.setTracker(tracker)

        recyclerViewJourList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jourListAdapter
            setHasFixedSize(true)
        }

        viewModelPresence.jours.observe(this, Observer { newJours -> UpdateUi(newJours) })
    }

    /**
     * assurer de sauvegarder l'état du suivi
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    /**
     * SelectionTracker => sélection multi-éléments
     * StableIdKeyProvider => fournisseur de clefs
     * MyLookup => classe de recherche des détails de l'élément et une
     * StorageStrategy => stratégie de stockage
     * SelectionPredicates.createSelectAnything => méthode pour spécifier le nombre d’éléments
     * que vous souhaitez permettre à l’utilisateur de sélectionner
     */
    private fun initTracker() {


    }

    private fun UpdateUi(newJours: List<Jour>) {
        jours.clear()
        jours.addAll(newJours)
        jourListAdapter.notifyDataSetChanged()
    }

    override fun onJourSelected(jour: Jour) {

    }

}
