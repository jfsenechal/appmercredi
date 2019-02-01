package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteFiche
import kotlinx.android.synthetic.main.sante_fiche_edit_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

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

        viewModelEnfant.enfant.observe(this, Observer { enfant ->

            Timber.i("zeze enfant"+enfant.id)

            santeViewModel.getSanteFicheByEnfantId(enfant.id).observe(this, Observer { santeFiche ->

                Timber.i("zeze fiche " + santeFiche)
                if (santeFiche != null) {
                    updateUi(santeFiche)
                }
            })

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