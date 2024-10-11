package ameri.hossein.weatherapp.data.network

import ameri.hossein.weatherapp.data.model.add_city.ResponseCitiesList
import ameri.hossein.weatherapp.data.model.info.ResponsePollution
import ameri.hossein.weatherapp.data.model.main.ResponseCurrentWeather
import ameri.hossein.weatherapp.data.model.main.ResponseForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("geo/1.0/direct")
    suspend fun getCitiesList(@Query("q") q: String, @Query("limit") limit: Int): Response<ResponseCitiesList>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("lang") lang: String, @Query("units") units: String,
    ): Response<ResponseCurrentWeather>

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("lang") lang: String, @Query("units") units: String,
    ): Response<ResponseForecast>

    @GET("data/2.5/air_pollution")
    suspend fun getPollution(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<ResponsePollution>
}