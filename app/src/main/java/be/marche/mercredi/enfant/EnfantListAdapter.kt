package be.marche.mercredi.enfant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import com.squareup.picasso.Picasso
import be.marche.mercredi.entity.Enfant

class EnfantListAdapter(
    private val enfants: List<Enfant>,
    private val listener: EnfantListAdapterListener?
) : RecyclerView.Adapter<EnfantListAdapter.ViewHolder>(), View.OnClickListener {

    interface EnfantListAdapterListener {
        fun onEnfantSelected(enfant: Enfant)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.cardView)!!
        val enfantPhoto = itemView.findViewById<ImageView>(R.id.enfantPhoto)!!
        val enfantNom = itemView.findViewById<TextView>(R.id.enfantNom)!!
        val enfantAnneeScolaire = itemView.findViewById<TextView>(R.id.enfantAnneeScolaire)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_enfant, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return enfants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val enfant = enfants[position]

        with(holder) {
            cardView.setOnClickListener(this@EnfantListAdapter)
            cardView.tag = enfant
            enfantNom.text = enfant.nom + " " + enfant.prenom

            Picasso.get()
                .load(enfant.photoUrl)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(enfantPhoto)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cardView -> listener?.onEnfantSelected(view.tag as Enfant)
        }
    }
}