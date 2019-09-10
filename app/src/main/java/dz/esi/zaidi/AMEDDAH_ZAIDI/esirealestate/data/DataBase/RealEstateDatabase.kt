package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost

@Database(entities = [RealEstatePost::class] , version = 1)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract fun realEstatePostDao() : RealEstatePostDAO

    companion object {
        private var INSTANCE: RealEstateDatabase? = null
        fun getInstance(context: Context): RealEstateDatabase? {
            synchronized(RealEstateDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateDatabase::class.java,
                        "real_estate_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }

        }

    }
}