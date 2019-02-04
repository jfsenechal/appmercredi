package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteFiche
import kotlinx.android.synthetic.main.sante_fiche_edit_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SanteFicheEditFragment : Fragment() {

    val santeViewModel: SanteViewModel by sharedViewModel()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance(): SanteFicheEditFragment {
            return SanteFicheEditFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.sante_fiche_edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enfantLiveData = viewModelEnfant.enfant

        val santeFicheLiveData = Transformations.switchMap(enfantLiveData) {
            santeViewModel.getSanteFicheByEnfantId(it.id)
        }

        santeFicheLiveData.observe(this, Observer {
            updateUi(it)
        })
    }

    private fun updateUi(santeFiche: SanteFiche?) {

        if (santeFiche != null) {
            medecinNomEditTextView.setText(santeFiche.medecinNom, TextView.BufferType.EDITABLE)
            medecinTelephoneditTextView.setText(santeFiche.medecinTelephone, TextView.BufferType.EDITABLE)
            personnesUrgencesEditTextView.setText(santeFiche.personneUrgence, TextView.BufferType.EDITABLE)
        }
    }
}