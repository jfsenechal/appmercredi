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
import be.marche.mercredi.entity.SanteQuestion
import be.marche.mercredi.entity.SanteReponse
import kotlinx.android.synthetic.main.sante_question_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QuestionFragment : Fragment() {

    val santeViewModel: SanteViewModel by sharedViewModel()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    var santeReponses: List<SanteReponse>? = null
    var position: Int = 0

    companion object {

        const val KEY_POSITION = "position"

        fun newInstance(position: Int): QuestionFragment {
            val frag = QuestionFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            frag.setArguments(args)

            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        position = getArguments()!!.getInt(KEY_POSITION, 1)
        return inflater.inflate(R.layout.sante_question_fragment, container, false)
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
                            updateUi(santeQuestion, santeReponse)
                        }
                    })
                })
            })
        })
    }

    private fun updateUi(santeQuestion: SanteQuestion, santeReponse: SanteReponse?) {

        questionIntituleView.text = santeQuestion.intitule
        labelQuestionComplementView.text = santeQuestion.complement_label
        labelQuestionComplementView.setVisibility(View.INVISIBLE)
        questionRemarqueView.setVisibility(View.INVISIBLE)

        if (santeReponse != null) {
            if (santeReponse.reponse == "oui") {
                questionReponseView.text = getString(R.string.switch_oui)
            }
            if (santeReponse.remarque != null && santeReponse.remarque!!.isNotEmpty()) {
                questionRemarqueView.setText(santeReponse.remarque, TextView.BufferType.EDITABLE)
                questionRemarqueView.setVisibility(View.VISIBLE)
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.VISIBLE)
                }
            }
        }
    }
}