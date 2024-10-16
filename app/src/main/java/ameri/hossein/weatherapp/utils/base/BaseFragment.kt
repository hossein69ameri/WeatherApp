package ameri.hossein.weatherapp.utils.base

import ameri.hossein.weatherapp.R
import ameri.hossein.weatherapp.utils.network.NetworkChecker
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    //Binding
    protected abstract val bindingInflater: (inflater: LayoutInflater) -> T
    private var _binding: T? = null
    protected val binding: T get() = requireNotNull(_binding)

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    var isNetworkAvailable = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Check network
        lifecycleScope.launch {
            networkChecker.checkNetwork().collect {
                isNetworkAvailable = it
                if (!it) {
                    Toast.makeText(requireContext(), R.string.checkYourNetwork, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}