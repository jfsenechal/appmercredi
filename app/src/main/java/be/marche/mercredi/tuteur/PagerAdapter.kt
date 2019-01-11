package be.marche.mercredi.tuteur

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import be.marche.mercredi.tuteur.fragments.ConjointEditCoordonneesFragment
import be.marche.mercredi.tuteur.fragments.TuteurEditCoordonneesFragment

class TuteurPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val titles: List<String> = listOf("Coordonnées", "Conjoint")

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> TuteurEditCoordonneesFragment.newInstance()
            else -> ConjointEditCoordonneesFragment.newInstance()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
