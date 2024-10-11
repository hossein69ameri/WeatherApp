package ameri.hossein.weatherapp.data.repository

import ameri.hossein.weatherapp.data.network.ApiServices
import ameri.hossein.weatherapp.utils.CITIES_LIMIT
import javax.inject.Inject

class AddCityRepository @Inject constructor(private val api: ApiServices) {
    //API
    suspend fun getCitiesList(q: String) = api.getCitiesList(q, CITIES_LIMIT)
}