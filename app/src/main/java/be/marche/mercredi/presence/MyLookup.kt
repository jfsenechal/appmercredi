package be.marche.mercredi.presence

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

/**
 * Pour que l' RecyclerView Selectionaddon fonctionne correctement, chaque fois que l'utilisateur touche le  RecyclerView widget,
 * vous devez traduire les coordonnées du contact en un  ItemDetails objet.
 */
class MyLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    /**
     * Comme vous pouvez le voir dans le code ci-dessus, la getItemDetails() méthode reçoit un  MotionEvent objet.
     * En transmettant les coordonnées X et Y de l'événement à la  findChildViewUnder() méthode,
     * vous pouvez déterminer la vue associée à l'élément de liste touché par l'utilisateur.
     * Pour convertir l' Viewobjet en ItemDetails objet, il suffit d'appeler la getItemDetails() méthode
     */
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as JourViewHolder)
                .getItemDetails()
        }
        return null
    }
}