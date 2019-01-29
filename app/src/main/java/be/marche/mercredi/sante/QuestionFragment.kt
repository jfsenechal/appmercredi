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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class QuestionFragment : Fragment() {

    val santeViewModel: SanteViewModel by inject()
    val viewModelEnfant: EnfantViewModel by sharedViewModel()
    lateinit var santeReponses: List<SanteReponse>

    companion object {

        const val KEY_POSITION = "position"

        fun newInstance(position: Int): QuestionFragment {
            val frag = QuestionFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            frag.setArguments(args);

            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sante_question_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position: Int = getArguments()!!.getInt(KEY_POSITION, 1)

        viewModelEnfant.enfant?.observe(this, Observer { enfant ->
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

    private fun listenSwitch(santeQuestion: SanteQuestion, santeReponse: SanteReponse?) {

        monSwitch.setOnClickListener {

            if (monSwitch.isChecked) {
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.VISIBLE)
                    complementEditTextView.setVisibility(View.VISIBLE)
                }
                monSwitch.text = getString(R.string.switch_oui)
                Timber.i("zeze listener checked on")
            } else {
                if (santeQuestion.complement == true) {
                    labelQuestionComplementView.setVisibility(View.INVISIBLE)
                    complementEditTextView.setVisibility(View.INVISIBLE)
                }
                monSwitch.text = getString(R.string.switch_non)
                Timber.i("zeze listener checked off")
            }
        }
    }

    private fun updateUi(santeQuestion: SanteQuestion, santeReponse: SanteReponse?) {

        questionIntituleView.text = santeQuestion.intitule
        labelQuestionComplementView.text = santeQuestion.complement_label
        labelQuestionComplementView.setVisibility(View.INVISIBLE)
        complementEditTextView.setVisibility(View.INVISIBLE)
        monSwitch.text = getString(R.string.switch_non)

        if (santeReponse != null) {
            if (santeReponse.reponse == "oui") {
                monSwitch.text = getString(R.string.switch_oui)
                monSwitch.setChecked(true)
                Timber.i("zeze update ui reponse oui")
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