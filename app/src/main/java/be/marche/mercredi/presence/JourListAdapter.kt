package be.marche.mercredi.presence

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

        Timber.i("position $position")

        with(holder) {
            cardViewJour.setOnClickListener(this@JourListAdapter)
            cardViewJour.tag = jour
            jourDateView.text = jour.date
            //typeJourView.text = jour.remarque
        }

        val parent = holder.cardViewJour.parent as LinearLayout

        Timber.i("zeze onbind $tracker")
        if (tracker!!.isSelected(position.toLong())) {
            holder.jourDateView.setTextColor(Color.GREEN)
            parent.background = ColorDrawable(
                Color.parseColor("#80deea")
            )
            holder.cardViewJour.setCardBackgroundColor(Color.BLUE)
        } else {
            holder.jourDateView.setTextColor(Color.RED)
            // Reset color to white if not selected
            parent.background = ColorDrawable(Color.WHITE)
        }
    }

    override fun onClick(view: View) {

        Timber.i("zeze" + tracker?.selection)
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