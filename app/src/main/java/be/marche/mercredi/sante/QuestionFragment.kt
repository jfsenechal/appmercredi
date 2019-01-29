package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import be.marche.mercredi.R
import be.marche.mercredi.entity.SanteQuestion
import kotlinx.android.synthetic.main.sante_question_fragment.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class QuestionFragment : Fragment() {

    val santeViewModel: SanteViewModel by inject()

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

        santeViewModel.getQuestionsById(position).observe(this, Observer {
            if (it != null) {
                updateUi(it)
                listenSwitch(it)
            }
        })
    }

    private fun listenSwitch(santeQuestion: SanteQuestion) {

        monSwitch.setOnClickListener {

            if (santeQuestion.complement == true) {

                if (monSwitch.isChecked) {
                    Timber.i("zeze switch 1on ")
                    labelQuestionComplementView.setVisibility(View.VISIBLE)
                    complementEditTextView.setVisibility(View.VISIBLE)
                } else {
                    Timber.i("zeze switch 1off")
                    labelQuestionComplementView.setVisibility(View.INVISIBLE)
                    complementEditTextView.setVisibility(View.INVISIBLE)
                }
            }
        }
    }

    private fun updateUi(santeQuestion: SanteQuestion) {
        questionIntituleView.text = santeQuestion.intitule
        labelQuestionComplementView.text = santeQuestion.complement_label
        labelQuestionComplementView.setVisibility(View.INVISIBLE)
        complementEditTextView.setVisibility(View.INVISIBLE)
    }

}