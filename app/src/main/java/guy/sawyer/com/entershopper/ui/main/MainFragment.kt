package guy.sawyer.com.entershopper.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import guy.sawyer.com.cms.model.City
import guy.sawyer.com.entershopper.MainActivity
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.MainFragmentBinding
import guy.sawyer.com.entershopper.ui.adapters.CitiesAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import guy.sawyer.com.cms.rest.CmsRepository

class MainFragment : Fragment(), CitiesAdapter.CityItemClickListener {

    private lateinit var viewModel: MainViewModel
    private var citiesAdapter: CitiesAdapter? = null
    private lateinit var dataBinding: MainFragmentBinding
    private lateinit var currentActivity: MainActivity
    private var cities: ArrayList<City> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bsavedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, bsavedInstanceState)

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        currentActivity = activity as MainActivity
        viewModel = ViewModelProviders.of(currentActivity).get(MainViewModel::class.java)
        setupRecyclerView(currentActivity)
        return dataBinding.root
    }

//    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCities().observe(this, Observer { cities: List<City>? ->
            cities?.forEach {
                if(it in this.cities) {
                    return@forEach
                }
                this.cities.add(it)
            }
            citiesAdapter?.notifyDataSetChanged()
        })

        viewModel.getCacheResponseSate().observe(this, Observer {state: Boolean ->
            if(state) {
                val toast = Toast.makeText(context, "Response is from Cache", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun setupRecyclerView(context: Context) {
        if (citiesAdapter == null) {
            citiesAdapter = CitiesAdapter(cities, this)
        }
        dataBinding.rvCities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = citiesAdapter
            itemAnimator =DefaultItemAnimator()
            isNestedScrollingEnabled = true
        }
        citiesAdapter!!.notifyDataSetChanged()
    }

    override fun onCityClicked(city: City) {


        val choices = arrayOf("View Malls", "View Shops")
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle("Would you like to view shops, or malls in ${city.name}")
            setItems(choices) { _, which ->
                when (which) {
                    0 -> {
                        val cityBundle = bundleOf("cityName" to city.name)
                        findNavController().navigate(R.id.action_mainFragment_to_mallsFragment, cityBundle)
                    }
                    else -> {
                        val cityBundle = bundleOf("cityName" to city.name)
                        findNavController().navigate(R.id.action_mainFragment_to_shopsFragment, cityBundle)
                    }
                }
            }

            setPositiveButton("Cancel", DialogInterface.OnClickListener(cancelButtonCLicked))
            show()
        }
    }

    private val cancelButtonCLicked = { _: DialogInterface, _: Int ->
    }
}
