package guy.sawyer.com.entershopper.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import guy.sawyer.com.cms.model.Mall
import guy.sawyer.com.entershopper.MainActivity
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.MallsFragmentBinding
import guy.sawyer.com.entershopper.ui.adapters.MallsAdapter

class MallsFragment : Fragment(), MallsAdapter.MallItemClickListener  {

    private lateinit var viewModel: MainViewModel
    private var mallsAdapter: MallsAdapter? = null
    private lateinit var dataBinding: MallsFragmentBinding
    private lateinit var currentActivity: MainActivity
    private var malls: ArrayList<Mall> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bsavedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, bsavedInstanceState)

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.malls_fragment, container, false)
        currentActivity = activity as MainActivity
        currentActivity.title = "Malls"
        viewModel = ViewModelProviders.of(currentActivity).get(MainViewModel::class.java)
        setupRecyclerView(currentActivity)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let { bundle ->
            viewModel.getMallsInCity(bundle.getString("cityName"))?.forEach {
                if(it in this.malls) {
                    return@forEach
                }
                malls.add(it)
            }
            dataBinding.tvHeaderTitle.text = bundle.getString("cityName")?.toString()
        }

        dataBinding.apply {
            tvMallCountTitle.text = "No. Malls: "
            tvMallCount.text = malls.count().toString()

            tvShopCountTitle.text = "No. Shops: "
            var shopeCount = 0
            malls.forEach { shopeCount += it.shops.count() }
            tvShopCount.text = shopeCount.toString()
        }
        mallsAdapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView(context: Context) {
        if (mallsAdapter == null) {
            mallsAdapter = MallsAdapter(malls, this)
        }
        dataBinding.rvMalls.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mallsAdapter
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = true
        }
        mallsAdapter!!.notifyDataSetChanged()
    }

    override fun onCityClicked(mall: Mall) {
        val mallBundle = bundleOf("mallName" to mall.name)
        findNavController().navigate(R.id.action_mallsFragment_to_shopsFragment, mallBundle)
    }
}