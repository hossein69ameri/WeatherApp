package ameri.hossein.weatherapp.ui.main

import ameri.hossein.weatherapp.R
import ameri.hossein.weatherapp.data.model.main.ResponseForecast
import ameri.hossein.weatherapp.databinding.FragmentMainBinding
import ameri.hossein.weatherapp.utils.base.BaseFragment
import ameri.hossein.weatherapp.utils.events.EventBus
import ameri.hossein.weatherapp.utils.events.Events
import ameri.hossein.weatherapp.utils.isVisible
import ameri.hossein.weatherapp.utils.network.NetworkRequest
import ameri.hossein.weatherapp.utils.onceObserve
import ameri.hossein.weatherapp.utils.setStatusBarIconsColor
import ameri.hossein.weatherapp.utils.setupRecyclerview
import ameri.hossein.weatherapp.utils.showSnackBar
import ameri.hossein.weatherapp.viewmodel.MainViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.github.matteobattilana.weather.PrecipType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    @Inject
    lateinit var forecastAdapter: ForecastAdapter

    //Other
    private val viewModel by viewModels<MainViewModel>()
    private val calendar by lazy { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Change status bar color
        requireActivity().setStatusBarIconsColor(false)
        //Cities list
        viewModel.callCitiesData()
        //InitViews
        binding.apply {
            menuImg.setOnClickListener { findNavController().navigate(R.id.actionToCitiesList) }
            addImg.setOnClickListener { findNavController().navigate(R.id.actionToAddCity) }
        }
        //Load data
        loadCitiesData()
        loadCurrentWeatherData()
        loadForecastData()
        //Event
        lifecycleScope.launch {
            EventBus.subscribe<Events.OnUpdateWeather> {
                viewModel.callCurrentWeatherApi(it.lat!!, it.lon!!)
                viewModel.callForecastApi(it.lat, it.lon)
            }
        }
    }

    private fun loadCitiesData() {
        binding.apply {
            viewModel.citiesData.onceObserve(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    //Visibility
                    emptyLay.isVisible = false
                    //Call api
                    viewModel.callCurrentWeatherApi(it[0].lat!!, it[0].lon!!)
                    viewModel.callForecastApi(it[0].lat!!, it[0].lon!!)
                } else {
                    emptyLay.isVisible = true
                    findNavController().navigate(R.id.actionToAddCity)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadCurrentWeatherData() {
        binding.apply {
            viewModel.currentWeatherData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loading.isVisible(true, container)
                    }

                    is NetworkRequest.Success -> {
                        loading.isVisible(false, container)
                        response.data?.let { data ->
                            //Info fragment
                            showAllTxt.setOnClickListener {
                                val direction = MainFragmentDirections.actionToInfo(data)
                                findNavController().navigate(direction)
                            }
                            //Name
                            cityName.text = data.name
                            //Weather
                            data.weather?.let { weather ->
                                if (weather.isNotEmpty()) {
                                    weather[0]?.let {
                                        emptyLay.isVisible = false
                                        //Info
                                        infoTxt.text = it.description
                                        //Bg
                                        bgImg.load(
                                            if (isNightNow()) R.drawable.bg_night
                                            else it.icon?.let { icon -> setDynamicallyWallpaper(icon) }
                                        ) {
                                            crossfade(true)
                                            crossfade(100)
                                        }
                                    }
                                }
                            }
                            //Main
                            data.main?.let { main ->
                                tempTxt.text = "${main.temp}${getString(R.string.degreeCelsius)}"
                                TempInfoTxt.text = "${main.tempMin}${getString(R.string.degree)}    " +
                                        "${main.tempMax}${getString(R.string.degree)}"
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        loading.isVisible(false, container)
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun setDynamicallyWallpaper(icon: String): Int {
        return when (icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_sun
            }

            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_cloud
            }

            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)
                R.drawable.bg_rain
            }

            "13" -> {
                initWeatherView(PrecipType.SNOW)
                R.drawable.bg_snow
            }

            "50" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_haze
            }

            else -> 0
        }
    }

    private fun initWeatherView(type: PrecipType) {
        binding.weatherView.apply {
            setWeatherData(type)
            angle = 20
            emissionRate = 100.0f
        }
    }

    private fun loadForecastData() {
        binding.apply {
            viewModel.forecastData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {}

                    is NetworkRequest.Success -> {
                        response.data?.let { data ->
                            if (data.list.isNotEmpty())
                                initRecyclerView(data.list)
                        }
                    }

                    is NetworkRequest.Error -> {
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(cities: List<ResponseForecast.Data>) {
        forecastAdapter.setData(cities)
        binding.forecastList.setupRecyclerview(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true), forecastAdapter
        )
    }
}