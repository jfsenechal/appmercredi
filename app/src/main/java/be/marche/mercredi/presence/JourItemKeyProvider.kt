package be.marche.mercredi.presence

import androidx.recyclerview.selection.ItemKeyProvider
import be.marche.mercredi.entity.Jour
import timber.log.Timber

class JourItemKeyProvider(val jours: List<Jour>) : ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_MAPPED) {

    override fun getKey(position: Int): Long {
        val l = jours.get(position).id.toLong()
        Timber.i("zeze 1 " + l)
        return l
    }

    override fun getPosition(key: Long): Int {
        val t = jours.indexOf(jours.get(key.toInt()))
        Timber.i("zeze 2 " + t)
        return t
    }

}