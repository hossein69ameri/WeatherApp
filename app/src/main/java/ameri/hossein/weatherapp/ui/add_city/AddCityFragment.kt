package ameri.hossein.weatherapp.ui.add_city

import ameri.hossein.weatherapp.R
import ameri.hossein.weatherapp.data.database.CitiesEntity
import ameri.hossein.weatherapp.data.model.add_city.ResponseCitiesList.ResponseCitiesListItem
import ameri.hossein.weatherapp.databinding.FragmentAddCityBinding
import ameri.hossein.weatherapp.utils.base.BaseBottomSheetFragment
import ameri.hossein.weatherapp.utils.events.EventBus
import ameri.hossein.weatherapp.utils.events.Events
import ameri.hossein.weatherapp.utils.network.NetworkRequest
import ameri.hossein.weatherapp.utils.setupRecyclerview
import ameri.hossein.weatherapp.utils.showSnackBar
import ameri.hossein.weatherapp.viewmodel.AddCityViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddCityFragment : BaseBottomSheetFragment<FragmentAddCityBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentAddCityBinding
        get() = FragmentAddCityBinding::inflate

    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    @Inject
    lateinit var cityEntity: CitiesEntity

    //Other
    private val viewModel by viewModels<AddCityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            searchInpLay.setEndIconOnClickListener {
                val search = searchEdt.text.toString()
                if (isNetworkAvailable) {
                    if (search.isNotEmpty())
                        viewModel.callCitiesApi(search)
                    else
                        root.showSnackBar(getString(R.string.searchCanNotBeEmpty))
                }
            }
        }
        //Load data
        loadSearchCityData()
    }

    private fun loadSearchCityData() {
        binding.apply {
            viewModel.citiesData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loading.isVisible = true
                    }

                    is NetworkRequest.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                initRecyclerView(data)
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(list: List<ResponseCitiesListItem>) {
        citiesAdapter.setData(list)
        binding.citiesList.setupRecyclerview(LinearLayoutManager(requireContext()), citiesAdapter)
        //Click
        citiesAdapter.setOnItemClickListener {
            //Set data into entity
            cityEntity.lat = it.lat
            cityEntity.lon = it.lon
            if (it.localNames?.fa != null)
                cityEntity.name = it.localNames.fa
            else
                cityEntity.name = it.name
            //Save city
            viewModel.saveCity(cityEntity)
            //Update event
            lifecycleScope.launch {
                EventBus.publish(Events.OnUpdateWeather(cityEntity.name, it.lat, it.lon))
            }
            //Close dialog
            this@AddCityFragment.dismiss()
        }
    }
}