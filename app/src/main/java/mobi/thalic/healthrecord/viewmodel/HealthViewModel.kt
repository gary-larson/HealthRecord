package mobi.thalic.healthrecord.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobi.thalic.healthrecord.data.HealthDatabase
import mobi.thalic.healthrecord.data.HealthDatabaseDao
import mobi.thalic.healthrecord.data.HealthEntity

class HealthViewModel(application : Application) : AndroidViewModel(application) {
    private val healthDao : HealthDatabaseDao =
        HealthDatabase.getInstance(application).healthDatabaseDao()
    val healthRecordList : LiveData<List<HealthEntity>>

    init {
        healthRecordList = healthDao.allEntries
    }

    suspend fun insert(healthEntity: HealthEntity) {
        healthDao.insert(healthEntity)
    }

    suspend fun update(healthEntity: HealthEntity) {
        healthDao.update(healthEntity)
    }
}
