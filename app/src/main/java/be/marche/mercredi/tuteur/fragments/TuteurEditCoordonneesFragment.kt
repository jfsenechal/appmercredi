package be.marche.mercredi.tuteur.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.fragment_edit_tuteur_coordonnees.*
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.tuteur.TuteurViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TuteurEditCoordonneesFragment : Fragment() {

    val viewModelTuteur: TuteurViewModel by sharedViewModel()
    lateinit var tuteur: Tuteur

    companion object {
        fun newInstance() = TuteurEditCoordonneesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_edit_tuteur_coordonnees, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelTuteur.tuteur?.observe(this, Observer { tuteur ->
            updateUi(tuteur!!)
            this.tuteur = tuteur
        })
    }

    fun updateUi(tuteur: Tuteur) {

        tuteur.apply {
            tuteurEmail.setText(email, TextView.BufferType.EDITABLE)
            tuteurTelephone.setText(telephone, TextView.BufferType.EDITABLE)
            tuteurRue.setText(adresse, TextView.BufferType.EDITABLE)
            tuteurCodePostal.setText(codePostal, TextView.BufferType.EDITABLE)
            tuteurLocalite.setText(localite, TextView.BufferType.EDITABLE)
            tuteurGsm.setText(gsm, TextView.BufferType.EDITABLE)
            tuteurTelephoneBureau.setText(telephoneBureau, TextView.BufferType.EDITABLE)
        }
    }

}
