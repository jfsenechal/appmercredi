package be.marche.mercredi.enfant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import be.marche.mercredi.entity.Presence

class EnfantPresenceListAdapter(
    private val presences: List<Presence>,
    private val listener: EnfantPresenceListAdapterListener?
) : RecyclerView.Adapter<EnfantPresenceListAdapter.ViewHolder>(), View.OnClickListener {

    interface EnfantPresenceListAdapterListener {
        fun onPresenceSelected(presence: Presence)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val presenceDateView = itemView.findViewById<TextView>(R.id.presenceDateView)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.enfant_presence_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return presences.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presence = presences[position]

        with(holder) {
            presenceDateView.setOnClickListener(this@EnfantPresenceListAdapter)
            presenceDateView.tag = presence
            presenceDateView.text = presence.date
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cardView -> listener?.onPresenceSelected(view.tag as Presence)
        }
    }
}