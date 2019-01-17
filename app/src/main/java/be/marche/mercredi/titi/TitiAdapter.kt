package be.marche.mercredi.titi

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import be.marche.mercredi.R
import timber.log.Timber

class TitiAdapter(
    private val listItems: List<Person>,
    private val context: Context
) : RecyclerView.Adapter<MyViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.list_item_jf, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = listItems[position].name
        holder.phone.text = listItems[position].phone

        val parent = holder.name.parent as LinearLayout

        if (tracker!!.isSelected(position.toLong())) {
            parent.background = ColorDrawable(
                Color.parseColor("#80deea")
            )
        } else {
            Timber.i("zeze ${tracker!!.selection}")
            // Reset color to white if not selected
            parent.background = ColorDrawable(Color.WHITE)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
    }

}