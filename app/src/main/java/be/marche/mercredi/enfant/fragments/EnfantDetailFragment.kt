package be.marche.mercredi.enfant.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.fragment_enfant_detail.*
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.Enfant
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class EnfantDetailFragment : Fragment() {

    val viewModelEnfant: EnfantViewModel by sharedViewModel()

    companion object {
        fun newInstance() = EnfantDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_enfant_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelEnfant.enfant?.observe(this, Observer { enfant -> updateUi(enfant!!) })
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
        findNavController().navigate(R.id.action_enfantDetailFragment_to_enfantEditFragment)
    }

    private fun updateUi(enfant: Enfant) {

        Picasso.get()
            .load(enfant.photoUrl)
            .placeholder(R.drawable.ic_image_black_24dp)
            .into(enfantPhoto)

        enfantNom.text = enfant.nom
        enfantPrenom.text = enfant.sexe
        enfantBirthday.text = enfant.birthday
        enfantNumeroNational.text = enfant.numeroNational
        enfantEcole.text = enfant.ecole
        enfantAnneeScolaire.text = enfant.anneeScolaire
        enfantRemarques.text = enfant.remarques


    }

}
