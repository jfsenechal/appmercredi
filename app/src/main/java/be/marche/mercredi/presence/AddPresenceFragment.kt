package be.marche.mercredi.presence

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Jour
import be.marche.mercredi.entity.Presence
import kotlinx.android.synthetic.main.jour_list_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class AddPresenceFragment : Fragment(), JourListAdapter.JourListAdapterListener {

    private var listener: JourListAdapter.JourListAdapterListener? = null
    private lateinit var jourListAdapter: JourListAdapter
    private lateinit var jours: MutableList<Jour>
    private var tracker: SelectionTracker<Long>? = null

    val viewModelPresence: PresenceViewModel by inject()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance() = AddPresenceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.jour_list_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
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

        recyclerViewJourList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jourListAdapter
            setHasFixedSize(true)
        }

        initTracker()

        viewModelPresence.jours.observe(this, Observer { newJours ->
            //this.jours = newJours
            UpdateUi(newJours)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnSave -> {
                savePresences()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
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
     * JourLookup => classe de recherche des détails de l'élément et une
     * StorageStrategy => stratégie de stockage
     * SelectionPredicates.createSelectAnything => méthode pour spécifier le nombre d’éléments
     * que vous souhaitez permettre à l’utilisateur de sélectionner
     *
     * JourItemKeyProvider(jours)
     */
    private fun initTracker() {
        tracker = SelectionTracker.Builder<Long>(
            "selection1",
            recyclerViewJourList,
            StableIdKeyProvider(recyclerViewJourList),
            JourLookup(recyclerViewJourList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    val nItems: Int? = tracker?.selection?.size()
                    if (nItems != null && nItems > 0) {
                        activity?.title = resources.getQuantityString(R.plurals.jours_selectionnees, nItems, nItems)
                    } else {
                        activity?.title = getResources().getString(R.string.app_name);
                    }
                }
            })

        jourListAdapter.setTracker(tracker)
    }

    private fun UpdateUi(newJours: List<Jour>) {
        jours.clear()
        jours.addAll(newJours)
        jourListAdapter.notifyDataSetChanged()
    }

    private fun savePresences() {
        val joursSelected = tracker?.selection
        val nbJours: Int? = tracker?.selection?.size()

        if (nbJours != null && nbJours > 0) {

            val presences = mutableListOf<Presence>()

            joursSelected?.forEach { position ->
                val jour: Jour = jours[position.toInt()]
                val presence = Presence(0, jour.date_jour, false, false)
                presences.add(presence)
                Timber.i("zeze position $presence")
            }

            //todo post server

            viewModelPresence.insertPresenceReal(enfant, presences,"123456")
            Toast.makeText(
                context,
                resources.getQuantityString(R.plurals.presence_ajoutees, nbJours, nbJours),
                Toast.LENGTH_LONG
            ).show()
        }

        findNavController().navigate(R.id.action_presenceFragment_pop)
    }

    override fun onJourSelected(jour: Jour) {

        Timber.i("zeze onjourSeleted")

    }

}
