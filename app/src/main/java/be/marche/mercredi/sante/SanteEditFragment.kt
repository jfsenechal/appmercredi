package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.viewpager.widget.ViewPager
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteFiche
import kotlinx.android.synthetic.main.sante_edit_tabbed.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SanteEditFragment : Fragment(), SantePagerAdapter.QuestionListener {

    var questionPosition: Int = 0
    val santeViewModel: SanteViewModel by sharedViewModel()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    lateinit var santePagerAdapter: SantePagerAdapter
    var previousView: View? = null
    lateinit var santeFiche: SanteFiche

    override fun onQuestionChanged(position: Int) {
        questionPosition = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sante_edit_tabbed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enfantLiveData = viewModelEnfant.enfant

        val santeFicheLiveData = Transformations.switchMap(enfantLiveData) {
            santeViewModel.getSanteFicheByEnfantId(it.id)
        }

        santeFicheLiveData.observe(this, Observer {
            santeFiche = it
        })

        santeViewModel.santeQuestions?.observe(this, Observer { questions ->
            santePagerAdapter = SantePagerAdapter(this.fragmentManager!!, questions)
            santeViewPager.setCurrentItem(1)
            santeViewPager.adapter = santePagerAdapter
        })

        santeViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

            override fun onPageSelected(position: Int) {

                var previousPosition = position

                if (previousPosition > 0) {
                    previousPosition -= 1
                }

                val fragment = santePagerAdapter.getItem(previousPosition)

                previousView = santeViewPager.get(0)

                if (fragment is QuestionEditFragment) {
                    traitementQuestionEditFragment(previousPosition)
                    return
                }

                if (fragment is SanteFicheEditFragment) {
                    traitementFicheEditFragement()
                    return
                }
            }
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

    private fun traitementFicheEditFragement() {

        val medecinNomEditTextView = previousView?.findViewById<EditText>(R.id.medecinNomEditTextView)
        val medecinTelephoneditTextView = previousView?.findViewById<EditText>(R.id.medecinTelephoneditTextView)
        val personnesUrgencesEditTextView = previousView?.findViewById<EditText>(R.id.personnesUrgencesEditTextView)

        santeFiche.apply {
            medecinNom = medecinNomEditTextView?.text.toString()
            medecinTelephone = medecinTelephoneditTextView?.text.toString()
            personneUrgence = personnesUrgencesEditTextView?.text.toString()
        }

        santeViewModel.insertSanteFiche(santeFiche)
    }

    private fun traitementQuestionEditFragment(questionId: Int) {

        val complementTextView = previousView?.findViewById<EditText>(R.id.complementEditTextView)
        val switchView = previousView?.findViewById<Switch>(R.id.monSwitch)

        val newReponse: String = switchView?.text.toString()
        val newComplement: String = complementTextView?.text.toString()

        santeViewModel.getReponseBySanteFicheIdAndQuestionId(santeFiche.id, questionId)
            .observe(this@SanteEditFragment, Observer { santeReponse ->
                if (santeReponse != null) {
                    var change = false
                    if (newReponse != santeReponse.reponse) {
                        santeReponse.reponse = newReponse.toString()
                        change = true
                    }
                    if (newComplement != santeReponse.remarque) {
                        santeReponse.remarque = newComplement
                        change = true
                    }

                    if (change) {
                        santeViewModel.insertReponse(santeReponse)
                    }
                }
            })
    }

}