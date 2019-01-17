package be.marche.mercredi.tuteur.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.conjoint_edit_coordonnees_fragment.*
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.tuteur.TuteurViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConjointEditCoordonneesFragment : Fragment() {

    val viewModelTuteur: TuteurViewModel by sharedViewModel()
    lateinit var tuteur: Tuteur

    companion object {
        fun newInstance() = ConjointEditCoordonneesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.conjoint_edit_coordonnees_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelTuteur.tuteur?.observe(this, Observer { tuteur ->
            this.tuteur = tuteur
            updateUi(tuteur!!)
        })
    }

    private fun updateUi(tuteur: Tuteur) {

        tuteur.apply {
            conjointTelephone.setText(telephoneConjoint, TextView.BufferType.EDITABLE)
            conjointTelephoneBureau.setText(telephoneBureauConjoint, TextView.BufferType.EDITABLE)
            conjointEmail.setText(emailConjoint, TextView.BufferType.EDITABLE)
            conjointGsm.setText(gsmConjoint, TextView.BufferType.EDITABLE)
            conjointNom.setText(nomConjoint, TextView.BufferType.EDITABLE)
            conjointPrenom.setText(telephoneConjoint, TextView.BufferType.EDITABLE)
        }

    }

}
