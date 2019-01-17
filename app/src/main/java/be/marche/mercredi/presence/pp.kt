package be.marche.mercredi.presence

import androidx.recyclerview.selection.ItemKeyProvider
import be.marche.mercredi.entity.Jour
import java.util.HashMap

class pp {
}

 class PokemonItemKeyProvider(private val mPokemonList: List<Jour>) :
    ItemKeyProvider<String>(ItemKeyProvider.SCOPE_CACHED) {

    private val mKeyToPosition: MutableMap<String, Int>

    init {

        mKeyToPosition = HashMap(mPokemonList.size)
        var i = 0
        for (pokemon in mPokemonList) {
            mKeyToPosition[pokemon.date] = i
            i++
        }
    }

    override fun getKey(i: Int): String? {
        return mPokemonList[i].date// directly from position to key id
    }

    override fun getPosition(s: String): Int {
        return mKeyToPosition[s]!!
    }
}









