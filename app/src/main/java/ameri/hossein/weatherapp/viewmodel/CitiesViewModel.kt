package ameri.hossein.weatherapp.viewmodel

import ameri.hossein.weatherapp.data.database.CitiesEntity
import ameri.hossein.weatherapp.data.repository.CitiesRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val repository: CitiesRepository) : ViewModel() {
    //Load all cities
    private val _citiesData = MutableLiveData<List<CitiesEntity>>()
    val citiesData: LiveData<List<CitiesEntity>> = _citiesData

    fun callCitiesData() = viewModelScope.launch {
        repository.getCities().collect {
            _citiesData.value = it
        }
    }

    //Delete
    fun deleteCity(entity: CitiesEntity) = viewModelScope.launch {
        repository.deleteCity(entity)
    }
}