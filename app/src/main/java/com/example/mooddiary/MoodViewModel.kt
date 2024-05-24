package com.example.mooddiary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.example.mooddiary.MoodStatistics


class MoodViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MoodRepository
    val allMoods: LiveData<List<MoodEntry>>

    init {
        // Получаем DAO для работы с базой данных и создаем репозиторий
        val moodDao = MoodDatabase.getDatabase(application).moodDao()
        repository = MoodRepository(moodDao)

        // Получаем список всех заметок из репозитория
        allMoods = repository.allMoods
    }
    fun insert(moodEntry: MoodEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(moodEntry)
    }
    fun update(moodEntry: MoodEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(moodEntry)
    }
    fun delete(moodEntry: MoodEntry) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(moodEntry)
    }
    fun getMoodStatisticsByDateRange(startDate: Date, endDate: Date): LiveData<List<MoodStatistics>> {
        val moodStatistics = MutableLiveData<List<MoodStatistics>>()

        viewModelScope.launch(Dispatchers.IO) {
            val statisticsList = repository.getMoodStatistics(startDate, endDate)
            moodStatistics.postValue(statisticsList)
        }

        return moodStatistics
    }
    fun getAverageMood(startDate: String, endDate: String): LiveData<Float> {
        return repository.getAverageMood(startDate, endDate)
    }

    fun getMoodTrends(): LiveData<List<MoodStatistics>> {
        return repository.getMoodTrends()
    }

    fun getMoodStatisticsByDate(startDate: Date, endDate: Date): LiveData<List<MoodStatistics>> {
        val moodStatistics = MutableLiveData<List<MoodStatistics>>()

        viewModelScope.launch(Dispatchers.IO) {
            val statisticsList = repository.getMoodStatistics(startDate, endDate)
            moodStatistics.postValue(statisticsList)
        }

        return moodStatistics
    }
}
