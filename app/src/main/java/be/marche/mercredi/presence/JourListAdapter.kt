package be.marche.mercredi.presence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import be.marche.mercredi.entity.Jour

class JourListAdapter(
    private val jours: List<Jour>,
    private val listener: JourListAdapterListener?
) : RecyclerView.Adapter<JourListAdapter.ViewHolderJour>(), View.OnClickListener {

    interface JourListAdapterListener {
        fun onJourSelected(jour: Jour)
    }

    class ViewHolderJour(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewJour = itemView.findViewById<CardView>(R.id.cardViewJour)
        val jourDateView = itemView.findViewById<TextView>(R.id.jourDateView)
        val typeJourView = itemView.findViewById<TextView>(R.id.typeJourView)

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getSelectionKey(): Long? = itemId

                override fun getPosition(): Int = adapterPosition

                // More code here

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJour {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jour, parent, false)
        return ViewHolderJour(viewItem)
    }

    override fun getItemCount(): Int {
        return jours.count()
    }

    override fun onBindViewHolder(holder: ViewHolderJour, position: Int) {
        val jour = jours[position]

        with(holder) {
            cardViewJour.setOnClickListener(this@JourListAdapter)
            cardViewJour.tag = jour
            jourDateView.text = jour.date
            //typeJourView.text = jour.remarque
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cardViewJour -> listener?.onJourSelected(view.tag as Jour)
        }
    }

}