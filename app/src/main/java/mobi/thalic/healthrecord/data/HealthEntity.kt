package mobi.thalic.healthrecord.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName="health_record_table")
data class HealthEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    @ColumnInfo(name = "date_entry")
    var dateEntry : Date,
    @ColumnInfo(name = "blood_sugar")
    var bloodSugar : Int,
    @ColumnInfo(name = "weight")
    var weight : Float,
    @ColumnInfo(name = "diastolic")
    var diastolic: Int,
    @ColumnInfo(name = "systolic")
    var systolic : Int,
    @ColumnInfo(name = "heart_rate")
    var heartRate : Int
) {
}