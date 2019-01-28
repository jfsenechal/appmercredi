package be.marche.mercredi.sante

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import be.marche.mercredi.entity.SanteQuestion

class SantePagerAdapter(fm: FragmentManager, val questions: List<SanteQuestion>) : FragmentStatePagerAdapter(fm) {

    val titles: List<String> = listOf("Coordonnées", "Conjoint")

    override fun getItem(position: Int): Fragment {
        return  QuestionFragment.newInstance(position)
    }

    override fun getCount(): Int = questions.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
