package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import be.marche.mercredi.R
import be.marche.mercredi.enfant.EnfantViewModel
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse
import kotlinx.android.synthetic.main.sante_question_edit_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class QuestionEditFragment : Fragment() {

    val santeViewModel: SanteViewModel by sharedViewModel()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    var santeReponses: List<SanteReponse>? = null
    var position: Int = 0
    var positionLiveData: MutableLiveData<Int> = MutableLiveData()

    companion object {

        const val KEY_POSITION = "position"

        fun newInstance(position: Int): QuestionEditFragment {
            val frag = QuestionEditFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            frag.setArguments(args);



            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        position = getArguments()!!.getInt(KEY_POSITION, 1)
        return inflater.inflate(R.layout.sante_question_edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position: Int = getArguments()!!.getInt(KEY_POSITION, 1)

        viewModelEnfant.enfant.observe(this, Observer { enfant ->
            santeViewModel.getSanteFicheByEnfantId(enfant.id).observe(this, Observer { santeFiche ->
                santeViewModel.getReponsesBySanteFicheId(santeFiche.id).observe(this, Observer { santeReponses ->
                    this.santeReponses = santeReponses
                    santeViewModel.getQuestionById(position).observe(this, Observer { santeQuestion ->
                        if (santeQuestion != null) {

                            val santeReponse = santeReponses.find {
                                it.questionId == santeQuestion.id
                            }

                            listenSwitch(santeQuestion, santeReponse)
                            updateUi(santeQuestion, santeReponse)
                        }
                    })
                })
            })
        })

    }

    fun testCode() {
        var santeReponse: SanteReponse? = null
        var santeFicheId: Int? = null

        positionLiveData.value = position

        santeViewModel.santeFiche?.observe(this, Observer {
            santeFicheId = it.id
        })

        val questionLiveData = Transformations.switchMap(positionLiveData) {
            santeViewModel.getQuestionById(it)
        }

        questionLiveData.observe(this, Observer { santeQuestion ->


        })


        santeViewModel.getQuestionById(position).observe(this, Observer { santeQuestion ->
            if (santeQuestion != null) {
                if (santeFicheId != null) {
                    santeReponse =
                        santeViewModel.getReponseBySanteFicheIdAndQuestionId(santeFicheId!!, santeQuestion.id).value
                    //            santeViewModel.loadReponse(santeQuestion.id, santeFicheId)
                }

                /*
                val santeReponse = santeReponses?.find {
                    it.questionId == santeQuestion.id
                }*/

                listenSwitch(santeQuestion, santeReponse)
                updateUi(santeQuestion, santeReponse)
            }
        })
    }

    private fun listenSwitch(santeQuestion: SanteQuestion, santeReponse: SanteReponse?) {

        monSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.VISIBLE)
                    complementEditTextView.setVisibility(View.VISIBLE)
                }
                monSwitch.text = getString(R.string.switch_oui)
            } else {
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.INVISIBLE)
                    complementEditTextView.setVisibility(View.INVISIBLE)
                }
                monSwitch.text = getString(R.string.switch_non)
            }
        }
    }

    private fun updateUi(santeQuestion: SanteQuestion, santeReponse: SanteReponse?) {

        questionIntituleView.text = santeQuestion.intitule
        labelQuestionComplementView.text = santeQuestion.complement_label
        labelQuestionComplementView.setVisibility(View.INVISIBLE)
        complementEditTextView.setVisibility(View.INVISIBLE)
        monSwitch.text = getString(R.string.switch_non)

        Timber.i("zeze reponse" + santeReponse)
        if (santeReponse != null) {
            if (santeReponse.reponse == "oui") {
                Timber.i("zeze reponse oui" + santeReponse)
                monSwitch.text = getString(R.string.switch_oui)
                monSwitch.setChecked(true)
            }
            if (santeReponse.remarque != null && santeReponse.remarque.length > 0) {
                complementEditTextView.setText(santeReponse.remarque, TextView.BufferType.EDITABLE)
                complementEditTextView.setVisibility(View.VISIBLE)
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.VISIBLE)
                }
            }
        }
    }
}