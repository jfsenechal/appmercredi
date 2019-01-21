package be.marche.mercredi.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.marche.mercredi.R
import be.marche.mercredi.sync.SyncViewModel
import be.marche.mercredi.user.UserViewModel
import be.marche.mercredi.utils.ConnectivityLiveData
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MenuFragment : Fragment(), MenuListAdapter.MenuAdapterListener {

    companion object {
        fun newInstance() = MenuFragment()
    }

    val syncViewModel: SyncViewModel by viewModel()
    val userViewModel: UserViewModel by viewModel()

    private var listener: MenuListAdapter.MenuAdapterListener? = null
    private lateinit var menuListAdapter: MenuListAdapter
    private lateinit var items: MutableList<MenuItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //todo observe token ?
        userViewModel.user?.observe(this, Observer {
            Timber.i("zeze sync ${it.token}")
            syncViewModel.token = it.token
            if (it.token.isNotEmpty()) {
                refreshDataBase()
            }
        })

        if (!::items.isInitialized) {
            items = mutableListOf()
        }

        listener = this
        menuListAdapter = MenuListAdapter(items, listener)

        menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuListAdapter
        }

        initializeData()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onItemSelected(menuItem: MenuItem) {
        val controller = findNavController()

        when (menuItem.id) {
            0 -> controller.navigate(R.id.action_menuFragment_to_enfantListFragment)
            1 -> controller.navigate(R.id.action_menuFragment_to_tuteurDetailFragment)
        }
    }

    private fun initializeData() {
        // Get the resources from the XML file.
        val sportsList = resources
            .getStringArray(R.array.menus_titles)
        val sportsInfo = resources
            .getStringArray(R.array.menus_info)
        val sportsImageResources = resources
            .obtainTypedArray(R.array.menu_images)

        // Clear the existing data (to avoid duplication).
        items.clear()

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (i in sportsList.indices) {
            items.add(
                MenuItem(
                    i,
                    sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)
                )
            )
        }

        // Recycle the typed array.
        sportsImageResources.recycle()

        // Notify the adapter of the change.
        menuListAdapter.notifyDataSetChanged()
    }

    private fun refreshDataBase() {
        ConnectivityLiveData(activity?.application).observe(this, Observer { connected ->
            when (connected) {
                true -> {
                    syncViewModel.refreshData()
                    //  infoMessage.visibility = View.INVISIBLE
                }
                false -> {
                    //  infoMessage.visibility = View.VISIBLE
                    //  infoMessage.text = getString(R.string.message_no_connectivity)
                }
            }
        })
    }


}
