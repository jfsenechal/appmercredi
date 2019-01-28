package be.marche.mercredi.sante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.marche.mercredi.R
import kotlinx.android.synthetic.main.sante_tabbed.*
import org.koin.android.ext.android.inject

class SanteFragment : Fragment() {

    val santeViewModel: SanteViewModel by inject()

    private var santePagerAdapter: SantePagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sante_tabbed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        santePagerAdapter = this.fragmentManager?.let { SantePagerAdapter(it) }
        santeViewPager.adapter = santePagerAdapter
    }

}