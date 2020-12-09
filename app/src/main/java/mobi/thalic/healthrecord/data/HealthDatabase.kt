package mobi.thalic.healthrecord.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [HealthEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDatabaseDao() : HealthDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getInstance(context: Context) : HealthDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HealthDatabase::class.java,
                        "health_record_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}