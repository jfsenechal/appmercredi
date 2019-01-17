package be.marche.mercredi.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import com.squareup.picasso.Picasso

class MenuListAdapter(
    private val items: List<MenuItem>,
    private val listener: MenuAdapterListener?
) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>(), View.OnClickListener {

    interface MenuAdapterListener {
        fun onItemSelected(menuItem: MenuItem)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.menuItemCardView)!!
        val menuImage = itemView.findViewById<ImageView>(R.id.menuImage)!!
        val menuTitle = itemView.findViewById<TextView>(R.id.menuTitle)!!
        val menuIntro = itemView.findViewById<TextView>(R.id.menuIntro)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = items[position]

        with(holder) {
            cardView.setOnClickListener(this@MenuListAdapter)
            cardView.tag = menuItem
            menuTitle.text = menuItem.titre
            menuIntro.text = menuItem.intro

            Picasso.get()
                .load(menuItem.image)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(menuImage)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.menuItemCardView -> {
                listener?.onItemSelected(view.tag as MenuItem)
            }
        }

    }
}