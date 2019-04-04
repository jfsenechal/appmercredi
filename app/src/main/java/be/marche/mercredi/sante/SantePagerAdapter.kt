package be.marche.mercredi.sante

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import be.marche.mercredi.entity.SanteQuestion

class SantePagerAdapter(fm: FragmentManager, val questions: List<SanteQuestion>, val edit: Boolean) : FragmentStatePagerAdapter(fm) {

    val titles: List<String> = listOf("Coordonnées", "Conjoint")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> if(edit) return SanteFicheEditFragment() else return SanteFicheFragment()
            else -> return if(edit) QuestionEditFragment.newInstance(position) else QuestionFragment.newInstance(position)
        }
    }

    override fun getCount(): Int = questions.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
