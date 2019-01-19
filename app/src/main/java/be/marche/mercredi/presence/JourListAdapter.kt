package be.marche.mercredi.presence

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import be.marche.mercredi.entity.Jour
import timber.log.Timber

class JourListAdapter(
    private val jours: List<Jour>,
    private val listener: JourListAdapterListener?
) : RecyclerView.Adapter<JourViewHolder>(), View.OnClickListener {

    /**
     * indiquer explicitement que chaque élément de cet adaptateur aura un identificateur stable unique de type Long
     */
    init {
        setHasStableIds(true)
    }

    private var tracker: SelectionTracker<Long>? = null

    interface JourListAdapterListener {
        fun onJourSelected(jour: Jour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jour, parent, false)
        return JourViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return jours.count()
    }

    /**
     * pour pouvoir utiliser la position d'un élément en tant qu'identificateur unique
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: JourViewHolder, position: Int) {
        val jour = jours[position]

        with(holder) {
            // cardViewJour.setOnClickListener(this@JourListAdapter)
            cardViewJour.setOnClickListener {
                if (tracker!!.isSelected(position.toLong())) {
                    holder.jourcheckBoxView.setChecked(true);
                } else {
                    holder.jourcheckBoxView.setChecked(false);
                }
            }
            cardViewJour.tag = jour
            jourDateView.text = jour.date_jour
            //typeJourView.text = jour.remarque
        }


    }

    override fun onClick(view: View) {

        Timber.i("zeze onClick" + tracker?.selection)
        when (view.id) {
            R.id.cardViewJour -> {
                Timber.i("zeze" + this.tracker)
                listener?.onJourSelected(view.tag as Jour)
            }
        }
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

}