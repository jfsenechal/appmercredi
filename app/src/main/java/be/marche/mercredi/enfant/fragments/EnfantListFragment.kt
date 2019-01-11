package be.marche.mercredi.enfant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.fragment_enfant_list.*
import be.marche.mercredi.enfant.EnfantListAdapter
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnfantListFragment : Fragment(), EnfantListAdapter.EnfantListAdapterListener {

    val viewModel: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance() = EnfantListFragment()
    }

    private var listener: EnfantListAdapter.EnfantListAdapterListener? = null
    private lateinit var enfantListAdapter: EnfantListAdapter
    private lateinit var enfants: MutableList<Enfant>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_enfant_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!::enfants.isInitialized) {
            enfants = mutableListOf()
        }

        listener = this
        enfantListAdapter = EnfantListAdapter(enfants, listener)

        recyclerViewEnfantList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = enfantListAdapter
        }

        viewModel.enfants.observe(this, Observer { newEnfants -> UpdateUi(newEnfants!!) })
    }

    override fun onEnfantSelected(enfant: Enfant) {
        viewModel.enfant = viewModel.getEnfantById(enfant.id)
        findNavController().navigate(R.id.action_enfantListFragment_to_enfantDetailFragment)
    }

    private fun UpdateUi(newEnfants: List<Enfant>) {
        enfants.clear()
        enfants.addAll(newEnfants)
        enfantListAdapter.notifyDataSetChanged()
    }
}

