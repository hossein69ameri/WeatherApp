package ameri.hossein.weatherapp.data.repository

import ameri.hossein.weatherapp.data.network.ApiServices
import javax.inject.Inject

class InfoRepository @Inject constructor(private val api: ApiServices) {
    suspend fun getPollution(lat: Double, lon: Double) = api.getPollution(lat, lon)
}