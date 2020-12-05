package mobi.thalic.healthrecord.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.sql.Date

@Dao
interface HealthDatabaseDao {
    @Insert
    fun insert(health: HealthEntity)

    @Update
    fun update(health: HealthEntity)

    @Query("SELECT * FROM health_record_table WHERE id = :id")
    fun get(id: Long): HealthEntity

    @Query("SELECT * FROM health_record_table ORDER BY date_entry DESC")
    fun getAllEntries(): LiveData<List<HealthEntity>>

    @Query("SELECT id FROM health_record_table WHERE date_entry = :date")
    fun getIdByDate(date : Date): Long
}