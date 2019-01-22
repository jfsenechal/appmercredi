package be.marche.mercredi.tuteur.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.tuteur_detail_fragment.*
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.tuteur.TuteurViewModel
import be.marche.mercredi.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class TuteurDetailFragment : Fragment() {

    lateinit var tuteur: Tuteur
    val viewModelTuteur: TuteurViewModel by sharedViewModel()
    val userViewModel: UserViewModel by sharedViewModel()

    companion object {
        fun newInstance() = TuteurDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.tuteur_detail_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userViewModel.user?.observe(this, Observer {

            var token = it?.token
            //  token = null
            Timber.i("zeze token $token")
            if (token != null) {

            }
        })

        viewModelTuteur.tuteur = viewModelTuteur.getTuteurById(118)

        viewModelTuteur.tuteur?.observe(this, Observer { tuteur ->

            updateUi(tuteur)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnEdit -> {
                startEdit()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    private fun startEdit() {
        findNavController().navigate(R.id.action_tuteurDetailFragment_to_tuteurEditFragment)
    }

    private fun updateUi(tuteur: Tuteur?) {

        tuteur?.apply {
            tuteurDetailNomPrenom.text = "${tuteur.nom.toUpperCase()} ${tuteur.prenom}"
            tuteurDetailEmail.text = email
            tuteurDetailTelephone.text = telephone
            tuteurDetailRue.text = adresse
            tuteurDetailCpLocalite.text = "$codePostal $localite"
            tuteurDetailGsm.text = gsm
            tuteurDetailTelephoneBureau.text = telephoneBureau

            tuteurDetailConjointTelephone.text = tuteur.telephoneConjoint
            tuteurDetailConjointTelephoneBureau.text = telephoneBureauConjoint
            tuteurDetailConjointEmail.text = emailConjoint
            tuteurDetailConjointGsm.text = gsmConjoint
            tuteurDetailConjointNomPrenom.text = "$nomConjoint  $prenomConjoint"
        }
    }

}
