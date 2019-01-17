package be.marche.mercredi.titi

import android.view.View
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_jf.view.*

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.list_item_name
    val phone: TextView = view.list_item_phone

    // More code here
    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getSelectionKey(): Long? = itemId

            override fun getPosition(): Int = adapterPosition

        }

}