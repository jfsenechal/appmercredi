package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteFiche
import kotlinx.android.synthetic.main.sante_fiche_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SanteFicheFragment : Fragment() {

    val santeViewModel: SanteViewModel by sharedViewModel()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance(): SanteFicheFragment {
            return SanteFicheFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.sante_fiche_fragment, container, false)
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

    private fun updateUi(santeFiche: SanteFiche) {
        nomMedecinView.setText(santeFiche.medecinNom)
        nomMedecinView.setText(santeFiche.medecinTelephone)
        nomMedecinView.setText(santeFiche.medecinNom)
    }
}