package be.marche.mercredi.presence

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R

class JourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cardViewJour = itemView.findViewById<CardView>(R.id.cardViewJour)
    val jourDateView = itemView.findViewById<TextView>(R.id.jourDateView)
    val typeJourView = itemView.findViewById<TextView>(R.id.typeJourView)
    val jourcheckBoxView = itemView.findViewById<CheckBox>(R.id.jourcheckBoxView)

    /**
     * a besoin d'une méthode qu'il peut appeler pour identifier de manière unique les éléments de liste sélectionnés
     */
    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {

            /**
             * Cette méthode doit renvoyer une clé pouvant être utilisée pour identifier de manière unique un élément de la liste
             */
            override fun getSelectionKey(): Long? = itemId

            /**
             *adapterPosition propriété n'est généralement rien d'autre que l'index d'un élément de la liste.
             */
            override fun getPosition(): Int = adapterPosition
        }
}
