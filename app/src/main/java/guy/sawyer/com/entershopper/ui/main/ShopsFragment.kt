package guy.sawyer.com.entershopper.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import guy.sawyer.com.cms.model.Shop
import guy.sawyer.com.entershopper.MainActivity
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.MallsFragmentBinding
import guy.sawyer.com.entershopper.databinding.ShopsFragmentBinding
import guy.sawyer.com.entershopper.ui.adapters.MallsAdapter
import guy.sawyer.com.entershopper.ui.adapters.ShopsAdapter

class ShopsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var shopsAdapter: ShopsAdapter? = null
    private lateinit var dataBinding: ShopsFragmentBinding
    private lateinit var currentActivity: MainActivity
    private var shops: ArrayList<Shop> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bsavedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, bsavedInstanceState)

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.shops_fragment, container, false)

        currentActivity = activity as MainActivity
        currentActivity.title = "Shops"
        viewModel = ViewModelProviders.of(currentActivity).get(MainViewModel::class.java)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(currentActivity)
        arguments?.let { bundle ->

            if (bundle.containsKey("mallName")) {
                viewModel.getShopsInMall(bundle.getString("mallName"))?.forEach {
                    shops.add(it)
                }
                dataBinding.tvHeaderTitle.text = bundle.getString("mallName")
            } else {
                viewModel.getShopsInCity(bundle.getString("cityName")!!)?.forEach {
                    shops.add(it)
                }
                dataBinding.tvHeaderTitle.text = bundle.getString("cityName")
            }
        }
        shopsAdapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView(context: Context) {
        if (shopsAdapter == null) {
            shopsAdapter = ShopsAdapter(shops)
        }
        dataBinding.rvShops.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopsAdapter
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = true
        }
    }
}