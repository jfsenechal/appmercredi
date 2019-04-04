package be.marche.mercredi.sante

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Switch
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteFiche
import kotlinx.android.synthetic.main.sante_edit_tabbed.*
import kotlinx.android.synthetic.main.sante_fiche_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SanteFragment : Fragment() {

    val santeViewModel: SanteViewModel by sharedViewModel()
    val enfantViewModel: EnfantViewModel by sharedViewModel()
    lateinit var santePagerAdapter: SantePagerAdapter
    lateinit var santeFiche: SanteFiche

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sante_edit_tabbed, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnEdit -> {
                findNavController().navigate(R.id.action_santeFragment_to_santeEditFragment)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enfantLiveData = enfantViewModel.enfant

        val santeFicheLiveData = Transformations.switchMap(enfantLiveData) {
            santeViewModel.getSanteFicheByEnfantId(it.id)
        }

        santeFicheLiveData.observe(this, Observer {
            santeFiche = it
        })

        santeViewModel.santeQuestions?.observe(this, Observer { questions ->
            santePagerAdapter = SantePagerAdapter(this.fragmentManager!!, questions, false)
            santeViewPager.setCurrentItem(1)
            santeViewPager.adapter = santePagerAdapter
        })

        btnNextView.setOnClickListener {
            val currentItem = santeViewPager.currentItem
            santeViewPager.setCurrentItem(currentItem + 1)
        }

        btnPreView.setOnClickListener {
            val currentItem = santeViewPager.currentItem
            santeViewPager.setCurrentItem(currentItem - 1)
        }
    }
}