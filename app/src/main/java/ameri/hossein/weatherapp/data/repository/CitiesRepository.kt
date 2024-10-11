package ameri.hossein.weatherapp.data.repository

import ameri.hossein.weatherapp.data.database.CitiesDao
import ameri.hossein.weatherapp.data.database.CitiesEntity
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val dao: CitiesDao) {

    fun getCities() = dao.loadCities()
    suspend fun deleteCity(entity: CitiesEntity) = dao.deleteCity(entity)
}