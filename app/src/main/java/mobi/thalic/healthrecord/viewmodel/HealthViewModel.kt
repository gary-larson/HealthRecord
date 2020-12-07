package mobi.thalic.healthrecord.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobi.thalic.healthrecord.data.HealthEntity

class HealthViewModel : ViewModel() {
    private val healthRecords: MutableLiveData<List<HealthEntity>> by lazy {
        MutableLiveData<List<HealthEntity>>().also {
            loadHealthRecords()
        }
    }

    fun getHealthRecords(): LiveData<List<HealthEntity>> {
        return healthRecords
    }

    private fun loadHealthRecords() {
        // Do an asynchronous operation to fetch users.
    }
}
