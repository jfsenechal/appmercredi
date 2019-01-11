package be.marche.mercredi.tuteur.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.activity_tabbed.*
import kotlinx.android.synthetic.main.fragment_edit_conjoint_coordonnees.*
import kotlinx.android.synthetic.main.fragment_edit_tuteur_coordonnees.*
import be.marche.mercredi.entity.Tuteur
import be.marche.mercredi.tuteur.TuteurPagerAdapter
import be.marche.mercredi.tuteur.TuteurViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TabEditFragment : Fragment() {

    val viewModelTuteur: TuteurViewModel by sharedViewModel()
    lateinit var tuteur: Tuteur

    private var tuteurPagerAdapter: TuteurPagerAdapter? = null

    interface TabHomeListener {
        fun onRefresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_tabbed, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tuteurPagerAdapter = this.fragmentManager?.let { TuteurPagerAdapter(it) }

        mypager.adapter = tuteurPagerAdapter
        tabLayout.setupWithViewPager(mypager)
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

        viewModelTuteur.save(tuteur)
        Toast.makeText(context, "Sauvegardé", Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.action_tuteurEditFragment_pop)
    }
}