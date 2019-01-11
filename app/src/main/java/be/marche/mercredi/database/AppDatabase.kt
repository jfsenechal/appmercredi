package be.marche.mercredi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.marche.mercredi.entity.Ecole
import be.marche.mercredi.entity.Enfant
import be.marche.mercredi.entity.Jour
import be.marche.mercredi.entity.Tuteur

const val DATABASE_NAME = "mercredi"

@Database(entities = [Enfant::class, Tuteur::class, Ecole::class, Jour::class], version = 13)
abstract class AppDatabase : RoomDatabase() {
    abstract fun enfantDao(): EnfantDao
    abstract fun tuteurDao(): TuteurDao
    abstract fun mercrediDao(): MercrediDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
