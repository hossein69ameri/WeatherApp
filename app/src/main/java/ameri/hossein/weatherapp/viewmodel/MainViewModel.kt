package ameri.hossein.weatherapp.viewmodel

import ameri.hossein.weatherapp.data.database.CitiesEntity
import ameri.hossein.weatherapp.data.model.main.ResponseCurrentWeather
import ameri.hossein.weatherapp.data.model.main.ResponseForecast
import ameri.hossein.weatherapp.data.repository.MainRepository
import ameri.hossein.weatherapp.utils.network.NetworkRequest
import ameri.hossein.weatherapp.utils.network.NetworkResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    //Cities from database
    private val _citiesData = MutableLiveData<List<CitiesEntity>>()
    val citiesData: LiveData<List<CitiesEntity>> = _citiesData

    fun callCitiesData() = viewModelScope.launch {
        repository.getCities().collect {
            _citiesData.value = it
        }
    }

    //Api
    //Current weather
    private val _currentWeatherData = MutableLiveData<NetworkRequest<ResponseCurrentWeather>>()
    val currentWeatherData: LiveData<NetworkRequest<ResponseCurrentWeather>> = _currentWeatherData

    fun callCurrentWeatherApi(lat: Double, lon: Double) = viewModelScope.launch {
        _currentWeatherData.value = NetworkRequest.Loading()
        val response = repository.getCurrentWeather(lat, lon)
        _currentWeatherData.value = NetworkResponse(response).generateResponse()
    }

    //Forecast
    private val _forecastData = MutableLiveData<NetworkRequest<ResponseForecast>>()
    val forecastData: LiveData<NetworkRequest<ResponseForecast>> = _forecastData

    fun callForecastApi(lat: Double, lon: Double) = viewModelScope.launch {
        _forecastData.value = NetworkRequest.Loading()
        val response = repository.getForecast(lat, lon)
        _forecastData.value = NetworkResponse(response).generateResponse()
    }
}