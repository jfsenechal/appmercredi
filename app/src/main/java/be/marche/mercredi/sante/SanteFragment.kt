package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import be.marche.mercredi.entity.SanteQuestion
import kotlinx.android.synthetic.main.sante_tabbed.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class SanteFragment : Fragment() {

    val santeViewModel: SanteViewModel by inject()
    lateinit var questions: List<SanteQuestion>
    lateinit var santePagerAdapter: SantePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sante_tabbed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        santeViewModel.getAllQuestions().observe(this, Observer { questions ->
            for (question in questions) {
           //     Timber.i("zeze quest " + question.intitule)
            }
            santePagerAdapter = SantePagerAdapter(this.fragmentManager!!, questions)
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