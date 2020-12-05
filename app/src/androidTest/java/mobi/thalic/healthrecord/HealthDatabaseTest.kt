package mobi.thalic.healthrecord

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import mobi.thalic.healthrecord.data.HealthDatabase
import mobi.thalic.healthrecord.data.HealthDatabaseDao
import mobi.thalic.healthrecord.data.HealthEntity
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class HealthDatabaseTest {
    // Declare Constants
    val ID_VALUE = 0L
    val DATE_ENTRY_VALUE = "2020-12-11 08:30"
    val BLOOD_SUGAR_VALUE = 120
    val WEIGHT_VALUE = 265.5F
    val DIASTOLIC_VALUE = 120
    val SYSTOLIC_VALUE = 80
    val HEART_RATE_VALUE = 64

    // create rule
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    // declare variables
    private lateinit var healthDao: HealthDatabaseDao
    private lateinit var db: HealthDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, HealthDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        healthDao = db.healthDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertGetIdByDateAndGetHealth() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(DATE_ENTRY_VALUE, formatter)
        val health = HealthEntity(ID_VALUE, dateTime, BLOOD_SUGAR_VALUE, WEIGHT_VALUE,
            DIASTOLIC_VALUE, SYSTOLIC_VALUE, HEART_RATE_VALUE)
        healthDao.insert(health)
        val id = healthDao.getIdByDate(dateTime)
        val healthEntry = healthDao.get(id)
        Assert.assertEquals(healthEntry.bloodSugar, BLOOD_SUGAR_VALUE)
        Assert.assertEquals(healthEntry.diastolic, DIASTOLIC_VALUE)
        Assert.assertEquals(healthEntry.heartRate, HEART_RATE_VALUE)
        Assert.assertEquals(healthEntry.systolic, SYSTOLIC_VALUE)
        Assert.assertEquals(healthEntry.weight, WEIGHT_VALUE)
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testInsertGetIdByDateAndUpdate() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(DATE_ENTRY_VALUE, formatter)
        val health = HealthEntity(ID_VALUE, dateTime, BLOOD_SUGAR_VALUE, WEIGHT_VALUE,
            DIASTOLIC_VALUE, SYSTOLIC_VALUE, HEART_RATE_VALUE)
        healthDao.insert(health)
        val id = healthDao.getIdByDate(dateTime)
        health.id = id
        health.weight = 215.8F
        healthDao.update(health)
        val health1 = healthDao.get(id)
        Assert.assertEquals(health1.weight, 215.8F)
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testInsertGetIdByDateAndDelete() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(DATE_ENTRY_VALUE, formatter)
        val health = HealthEntity(ID_VALUE, dateTime, BLOOD_SUGAR_VALUE, WEIGHT_VALUE,
            DIASTOLIC_VALUE, SYSTOLIC_VALUE, HEART_RATE_VALUE)
        healthDao.insert(health)
        val id = healthDao.getIdByDate(dateTime)
        health.id = id
        healthDao.delete(health)
        val health1 = healthDao.get(id)
        Assert.assertNull(health1)
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun testInsertAndGetAllEntries() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(DATE_ENTRY_VALUE, formatter)
        val health = HealthEntity(ID_VALUE, dateTime, BLOOD_SUGAR_VALUE, WEIGHT_VALUE,
            DIASTOLIC_VALUE, SYSTOLIC_VALUE, HEART_RATE_VALUE)
        healthDao.insert(health)
        val dateTime1 = LocalDateTime.parse("2020-12-25 12:00", formatter)
        val health1 = HealthEntity(ID_VALUE, dateTime1, 88, 221.6F, 166,
            102, 110)
        healthDao.insert(health1)
        val healthList = healthDao.getAllEntries().blockingObserve()
        Assert.assertEquals(healthList?.size, 2)
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}