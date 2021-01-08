package mobi.thalic.healthrecord.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import mobi.thalic.healthrecord.data.HealthDatabase
import mobi.thalic.healthrecord.data.HealthDatabaseDao
import mobi.thalic.healthrecord.data.HealthEntity

class HealthViewModel(application : Application) : AndroidViewModel(application) {

    private val healthDao : HealthDatabaseDao =
        HealthDatabase.getInstance(application).healthDatabaseDao()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val healthRecordList : LiveData<List<HealthEntity>>

    init {
        healthRecordList = healthDao.allEntries
    }

    fun insertHealthRecord(healthEntity: HealthEntity) {
        uiScope.launch {
            insert(healthEntity)
        }
    }

    private suspend fun insert(healthEntity: HealthEntity) {
        withContext(Dispatchers.IO) {
            healthDao.insert(healthEntity)
        }
    }

    fun updateHealthRecord(healthEntity: HealthEntity) {
        viewModelScope.launch {
            updateHealthRecord(healthEntity)
        }
    }

    private suspend fun update(healthEntity: HealthEntity) {
        healthDao.update(healthEntity)
    }
}
