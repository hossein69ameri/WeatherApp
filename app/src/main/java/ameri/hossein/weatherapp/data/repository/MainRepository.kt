package ameri.hossein.weatherapp.data.repository

import ameri.hossein.weatherapp.data.database.CitiesDao
import ameri.hossein.weatherapp.data.network.ApiServices
import ameri.hossein.weatherapp.utils.FA
import ameri.hossein.weatherapp.utils.METRIC
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: CitiesDao, private val api: ApiServices) {
    //Database
    fun getCities() = dao.loadCities()

    //Api
    suspend fun getCurrentWeather(lat: Double, lon: Double) = api.getCurrentWeather(lat, lon, FA, METRIC)
    suspend fun getForecast(lat: Double, lon: Double) = api.getForecast(lat, lon, FA, METRIC)
}