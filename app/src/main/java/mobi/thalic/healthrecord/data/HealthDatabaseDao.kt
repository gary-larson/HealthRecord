package mobi.thalic.healthrecord.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Date
import java.time.LocalDateTime

@Dao
interface HealthDatabaseDao {
    @Insert
    suspend fun insert(health: HealthEntity)

    @Update
    suspend fun update(health: HealthEntity)

    @Delete
    suspend fun delete(health: HealthEntity)

    @Query("SELECT * FROM health_record_table WHERE id = :id")
    suspend fun get(id: Long): HealthEntity

    @get:Query("SELECT * FROM health_record_table ORDER BY date_entry DESC")
    val allEntries: LiveData<List<HealthEntity>>

    @Query("SELECT id FROM health_record_table WHERE date_entry = :date")
    suspend fun getIdByDate(date : LocalDateTime): Long
}