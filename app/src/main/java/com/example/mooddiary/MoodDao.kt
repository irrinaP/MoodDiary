package com.example.mooddiary

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoodDao {
    @Insert
    suspend fun insert(moodEntry: MoodEntry)

    @Update
    suspend fun update(moodEntry: MoodEntry)

    @Delete
    suspend fun delete(moodEntry: MoodEntry)

    @Query("SELECT * FROM mood_entries ORDER BY entry_date DESC, entry_time DESC")
    fun getAll(): LiveData<List<MoodEntry>>

    @Query("SELECT AVG(entry_mood) FROM mood_entries WHERE entry_date BETWEEN :startDate AND :endDate")
    LiveData<Float> getAverageMood(String startDate, String endDate)

    @Query("SELECT entry_date, AVG(entry_mood) AS avgMood FROM mood_entries GROUP BY entry_date ORDER BY entry_date")
    LiveData<List<MoodStatistics>> getMoodTrends()
}
