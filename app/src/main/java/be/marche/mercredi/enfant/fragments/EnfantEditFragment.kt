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
import kotlinx.android.synthetic.main.fragment_enfant_edit.*
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnfantEditFragment : Fragment() {

    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    val mercrediViewModel: MercrediViewModel by inject()

    lateinit var enfant: Enfant
    lateinit var spinnerSexe: Spinner
    lateinit var spinnerAnneeScolaire: Spinner
    lateinit var spinnerEcoles: Spinner
    lateinit var sexeAdapter: ArrayAdapter<Any>
    lateinit var ecoleAdapter: ArrayAdapter<Any>
    lateinit var anneeScolaireAdapter: ArrayAdapter<Any>
    lateinit var sexes: Array<String>

    companion object {
        fun newInstance() = EnfantEditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_enfant_edit, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelEnfant.enfant?.observe(this, Observer { enfant ->
            updateUi(enfant!!)
            this.enfant = enfant
        })

        initAdapters()

        mercrediViewModel.ecoles.observe(this, Observer {
            it.forEach() { ecole ->
                ecoleAdapter.add(ecole)
            }
        })

        mercrediViewModel.anneesScolaires.observe(this, Observer {
            it?.forEach() { anneeScolaire ->
                anneeScolaireAdapter.add(anneeScolaire)
            }
        })

        initSpinner()
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

    private fun initAdapters() {
        sexeAdapter = ArrayAdapter<Any>(
            context,
            android.R.layout.simple_spinner_item
        )

        ecoleAdapter = ArrayAdapter<Any>(
            context,
            android.R.layout.simple_spinner_item
        )

        anneeScolaireAdapter = ArrayAdapter<Any>(
            context,
            android.R.layout.simple_spinner_item
        )
    }

    private fun initSpinner() {
        sexes = resources.getStringArray(R.array.sexes)

        spinnerSexe = spinnerSexeView
        spinnerEcoles = spinnerEcoleView
        spinnerAnneeScolaire = spinnerAnneeScolaireView

        spinnerEcoles.adapter = ecoleAdapter
        sexeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerAnneeScolaire.adapter = anneeScolaireAdapter
        sexeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        ArrayAdapter.createFromResource(
            context,
            R.array.sexes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSexe.adapter = adapter
        }
    }

    private fun updateUi(enfant: Enfant) {
        //  enfantBirthday.text = enfant.birthday

        if (enfant.sexe == sexes.get(1))
            spinnerSexe.setSelection(1)
        else if (enfant.sexe == sexes.get(2))
            spinnerSexe.setSelection(2)

      //  spinnerEcoles.setSelection(ecols)

        enfantNumeroNational.setText(enfant.numeroNational, TextView.BufferType.EDITABLE)
        //   enfantRemarques.text = enfant.remarques
    }

    private fun updateObjectByUi(enfant: Enfant) {

        enfant.apply {
            numeroNational = enfantNumeroNational.text.toString()
            sexe = spinnerSexeView.selectedItem.toString()
            ecole = spinnerEcoleView.selectedItem.toString()
            anneeScolaire = spinnerAnneeScolaireView.selectedItem.toString()
        }

        viewModelEnfant.save(enfant)

        Toast.makeText(context, "Sauvegardé", Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_enfantEditFragment_pop)
    }

}
