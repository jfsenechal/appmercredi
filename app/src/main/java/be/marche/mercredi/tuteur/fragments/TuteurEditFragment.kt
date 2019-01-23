package be.marche.mercredi.tuteur.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.activity_tabbed.*
import kotlinx.android.synthetic.main.conjoint_edit_coordonnees_fragment.*
import kotlinx.android.synthetic.main.tuteur_edit_coordonnees_fragment.*
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.tuteur.TuteurPagerAdapter
import be.marche.mercredi.tuteur.TuteurViewModel
import be.marche.mercredi.user.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TuteurEditFragment : Fragment() {

    val viewModelTuteur: TuteurViewModel by sharedViewModel()
    val userViewModel: UserViewModel by sharedViewModel()
    lateinit var tuteur: Tuteur
    lateinit var token: String

    private var tuteurPagerAdapter: TuteurPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_tabbed, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.user?.observe(this, Observer {
            token = it.token
        })

        tuteurPagerAdapter = this.fragmentManager?.let { TuteurPagerAdapter(it) }

        tuteurViewPager.adapter = tuteurPagerAdapter
        tabLayout.setupWithViewPager(tuteurViewPager)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelTuteur.tuteur?.observe(this, Observer { tuteur ->

            this.tuteur = tuteur
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnSave -> {
                updateObjectByUi(tuteur)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    private fun updateObjectByUi(tuteur: Tuteur) {

        tuteur.apply {
            adresse = tuteurRue.text.toString()
            email = tuteurEmail.text.toString()
            telephone = tuteurTelephone.text.toString()
            telephoneBureau = tuteurTelephoneBureau.text.toString()
            gsm = tuteurGsm.text.toString()
            /**
             * conjoint
             */
            nomConjoint = conjointNom.text.toString()
            prenomConjoint = conjointPrenom.text.toString()
            emailConjoint = conjointEmail.text.toString()
            telephoneConjoint = conjointTelephone.text.toString()
            gsmConjoint = conjointGsm.text.toString()
            telephoneBureauConjoint = conjointTelephoneBureau.text.toString()
        }

        viewModelTuteur.saveReal(tuteur, token)
        viewModelTuteur.save(tuteur)
        Toast.makeText(context, "Sauvegardé", Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_tuteurEditFragment_pop)
    }
}