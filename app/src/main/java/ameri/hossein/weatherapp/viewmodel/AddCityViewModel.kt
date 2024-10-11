package ameri.hossein.weatherapp.viewmodel

import ameri.hossein.weatherapp.data.database.CitiesDao
import ameri.hossein.weatherapp.data.database.CitiesEntity
import ameri.hossein.weatherapp.data.model.add_city.ResponseCitiesList
import ameri.hossein.weatherapp.data.repository.AddCityRepository
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
class AddCityViewModel @Inject constructor(private val repository: AddCityRepository, private val dao: CitiesDao) :
    ViewModel() {
    //Cities
    private val _citiesData = MutableLiveData<NetworkRequest<ResponseCitiesList>>()
    val citiesData: LiveData<NetworkRequest<ResponseCitiesList>> = _citiesData

    fun callCitiesApi(q: String) = viewModelScope.launch {
        _citiesData.value = NetworkRequest.Loading()
        val response = repository.getCitiesList(q)
        _citiesData.value = NetworkResponse(response).generateResponse()
    }

    //Save city
    fun saveCity(entity: CitiesEntity) = viewModelScope.launch {
        dao.saveCity(entity)
    }
}