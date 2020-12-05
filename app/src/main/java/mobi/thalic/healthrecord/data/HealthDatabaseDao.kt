package mobi.thalic.healthrecord.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Date
import java.time.LocalDateTime

@Dao
interface HealthDatabaseDao {
    @Insert
    fun insert(health: HealthEntity)

    @Update
    fun update(health: HealthEntity)

    @Delete
    fun delete(health: HealthEntity)

    @Query("SELECT * FROM health_record_table WHERE id = :id")
    fun get(id: Long): HealthEntity

    @Query("SELECT * FROM health_record_table ORDER BY date_entry DESC")
    fun getAllEntries(): LiveData<List<HealthEntity>>

    @Query("SELECT id FROM health_record_table WHERE date_entry = :date")
    fun getIdByDate(date : LocalDateTime): Long
}