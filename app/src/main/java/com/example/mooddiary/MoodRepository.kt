package com.example.mooddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MoodRepository(private val moodDao: MoodDao) {
    val allMoods: LiveData<List<MoodEntry>> = moodDao.getAll()

    suspend fun insert(moodEntry: MoodEntry) {
        moodDao.insert(moodEntry)
    }

    suspend fun update(moodEntry: MoodEntry) {
        moodDao.update(moodEntry)
    }

    suspend fun delete(moodEntry: MoodEntry) {
        moodDao.delete(moodEntry)
    }

    fun getAverageMood(startDate: String, endDate: String): LiveData<Float> {
        return moodDao.getAverageMood(startDate, endDate)
    }

    fun getMoodTrends(): LiveData<List<MoodStatistics>> {
        return moodDao.getMoodTrends()
    }

}
