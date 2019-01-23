package be.marche.mercredi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.marche.mercredi.entity.*

const val DATABASE_NAME = "mercredi"

@Database(
    entities = [Enfant::class, Tuteur::class, Ecole::class, Jour::class, AnneeScolaire::class, Presence::class, User::class],
    version = 25
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun enfantDao(): EnfantDao
    abstract fun tuteurDao(): TuteurDao
    abstract fun mercrediDao(): MercrediDao
    abstract fun presenceDao(): PresenceDao
    abstract fun userDao(): UserDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
