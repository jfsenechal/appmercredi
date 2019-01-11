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
import kotlinx.android.synthetic.main.fragment_enfant_edit.*
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnfantEditFragment : Fragment() {

    lateinit var enfant: Enfant
    val viewModelEnfant: EnfantViewModel by sharedViewModel()

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

        val spinner: Spinner = enfantSexe
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.sexes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
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

    private fun updateUi(enfant: Enfant) {
        //  enfantBirthday.text = enfant.birthday
        enfantNumeroNational.setText(enfant.numeroNational, TextView.BufferType.EDITABLE)
        //  enfantEcole.text = enfant.ecole
        // enfantAnneeScolaire.text = enfant.anneeScolaire
        //   enfantRemarques.text = enfant.remarques

    }

    private fun updateObjectByUi(enfant: Enfant) {

        enfant.apply {
            numeroNational = enfantNumeroNational.text.toString()
            sexe = enfantSexe.selectedItem.toString()
        }

        viewModelEnfant.save(enfant)

        Toast.makeText(context, "Sauvegardé", Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_enfantEditFragment_pop)
    }

}
