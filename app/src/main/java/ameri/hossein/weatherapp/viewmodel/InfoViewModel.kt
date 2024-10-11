package ameri.hossein.weatherapp.viewmodel

import ameri.hossein.weatherapp.data.model.info.ResponsePollution
import ameri.hossein.weatherapp.data.repository.InfoRepository
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
class InfoViewModel @Inject constructor(private val repository: InfoRepository) :
    ViewModel() {
    //Cities
    private val _pollutionData = MutableLiveData<NetworkRequest<ResponsePollution>>()
    val pollutionData: LiveData<NetworkRequest<ResponsePollution>> = _pollutionData

    fun callPollutionApi(lat: Double, lon: Double) = viewModelScope.launch {
        _pollutionData.value = NetworkRequest.Loading()
        val response = repository.getPollution(lat, lon)
        _pollutionData.value = NetworkResponse(response).generateResponse()
    }
}