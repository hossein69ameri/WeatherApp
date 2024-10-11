package ameri.hossein.weatherapp.ui.cities

import ameri.hossein.weatherapp.data.database.CitiesEntity
import ameri.hossein.weatherapp.databinding.FragmentCitiesListBinding
import ameri.hossein.weatherapp.utils.base.BaseBottomSheetFragment
import ameri.hossein.weatherapp.utils.events.EventBus
import ameri.hossein.weatherapp.utils.events.Events
import ameri.hossein.weatherapp.utils.other.CityClickTypes
import ameri.hossein.weatherapp.utils.setupRecyclerview
import ameri.hossein.weatherapp.viewmodel.CitiesViewModel
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
class CitiesListFragment : BaseBottomSheetFragment<FragmentCitiesListBinding>() {
    //Binding
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentCitiesListBinding
        get() = FragmentCitiesListBinding::inflate

    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    //Other
    private val viewModel by viewModels<CitiesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Cities list
        viewModel.callCitiesData()
        //Load data
        loadCitiesData()
    }

    private fun loadCitiesData() {
        binding.apply {
            viewModel.citiesData.observe(viewLifecycleOwner) { cities ->
                //Visibility
                visibilityView(cities.isEmpty())
                //Fill recyclerview
                if (cities.isNotEmpty()) {
                    initRecyclerView(cities)
                }
            }
        }
    }

    private fun initRecyclerView(cities: List<CitiesEntity>) {
        citiesAdapter.setData(cities)
        binding.citiesList.setupRecyclerview(LinearLayoutManager(requireContext()), citiesAdapter)
        //Click
        citiesAdapter.setOnItemClickListener { data, type ->
            if (type == CityClickTypes.SELECT) {
                //Update event
                lifecycleScope.launch {
                    EventBus.publish(Events.OnUpdateWeather(data.name, data.lat, data.lon))
                }
                //Close dialog
                this@CitiesListFragment.dismiss()
            } else {
                viewModel.deleteCity(data)
            }
        }
    }

    private fun visibilityView(isEmpty: Boolean) {
        binding.apply {
            emptyLay.isVisible = isEmpty
            containerGroup.isVisible = isEmpty.not()
        }
    }
}