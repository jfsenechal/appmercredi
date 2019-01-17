package be.marche.mercredi.enfant.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.MercrediViewModel
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.AnneeScolaire
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Enfant
import kotlinx.android.synthetic.main.enfant_edit_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnfantEditFragment : Fragment() {

    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    val mercrediViewModel: MercrediViewModel by sharedViewModel()

    lateinit var enfant: Enfant
    lateinit var ecoles: List<Ecole>
    lateinit var anneesScolaires: List<AnneeScolaire>
    lateinit var sexes: Array<String>

    lateinit var spinnerSexe: Spinner
    lateinit var spinnerAnneeScolaire: Spinner
    lateinit var spinnerEcoles: Spinner
    lateinit var sexeAdapter: ArrayAdapter<Any>
    lateinit var anneeScolaireAdapter: ArrayAdapter<Any>
    lateinit var ecoleAdapter: ArrayAdapter<Any>

    companion object {
        fun newInstance() = EnfantEditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.enfant_edit_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initSpinnerView()

        viewModelEnfant.enfant?.observe(this, Observer { enfant ->
            this.enfant = enfant
            updateUi(enfant)
            initSpinners()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnSave -> {
                updateObjectByUi(enfant)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    private fun initSpinnerView() {
        spinnerSexe = spinnerSexeView
        spinnerEcoles = spinnerEcoleView
        spinnerAnneeScolaire = spinnerAnneeScolaireView
    }

    private fun initSpinnerSexe() {
        sexes = resources.getStringArray(R.array.sexes)
        sexeAdapter = ArrayAdapter<Any>(
            context,
            android.R.layout.simple_spinner_item,
            sexes
        )
        sexeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSexe.adapter = sexeAdapter
        val position = sexeAdapter.getPosition(enfant.sexe)
        spinnerSexe.setSelection(position)
    }

    private fun initSpinners() {

        initSpinnerSexe()

        mercrediViewModel.ecoles.observe(this, Observer {
            this.ecoles = it
            val nomEcole = it.find { x -> x.id == this.enfant.ecoleId }
            ecoleAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
            val position = ecoleAdapter.getPosition(nomEcole)
            spinnerEcoles.adapter = ecoleAdapter
            spinnerEcoles.setSelection(position)
        })

        mercrediViewModel.anneesScolaires.observe(this, Observer {
            this.anneesScolaires = it
            val nomAnneeScolaire = it.find { x -> x.id == this.enfant.anneeScolaireId }
            anneeScolaireAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
            val position = anneeScolaireAdapter.getPosition(nomAnneeScolaire)
            spinnerAnneeScolaire.adapter = anneeScolaireAdapter
            spinnerAnneeScolaire.setSelection(position)
        })
    }

    private fun updateUi(enfant: Enfant) {
        enfantNom.setText(enfant.nom, TextView.BufferType.EDITABLE)
        enfantPrenom.setText(enfant.prenom, TextView.BufferType.EDITABLE)
        enfantNumeroNational.setText(enfant.numeroNational, TextView.BufferType.EDITABLE)
    }

    private fun updateObjectByUi(enfant: Enfant) {

        enfant.apply {
            nom = enfantNom.text.toString()
            prenom = enfantPrenom.text.toString()
            numeroNational = enfantPrenom.text.toString()
            sexe = spinnerSexeView.selectedItem.toString()
            val nomEcoleSelected = spinnerEcoleView.selectedItem.toString()
            val ecole = ecoles.find { x -> x.nom == nomEcoleSelected }
            ecoleId = ecole!!.id

            val nomAnneeScolaireSelected = spinnerAnneeScolaire.selectedItem.toString()
            val anneeScolaire = anneesScolaires.find { x -> x.nom == nomAnneeScolaireSelected }
            anneeScolaireId = anneeScolaire!!.id
        }

        viewModelEnfant.save(enfant)

        Toast.makeText(context, "Sauvegardé", Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_enfantEditFragment_pop)
    }

}
