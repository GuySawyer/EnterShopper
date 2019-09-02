package guy.sawyer.com.entershopper

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import guy.sawyer.com.entershopper.ui.BaseActivity
import guy.sawyer.com.entershopper.ui.main.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navHostFragment: Fragment
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.init(this)

        showProgressDialog()
        viewModel.getCities().observe(this, Observer {
            dismissProgressDialog()
        })

        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment)!!
        setupSwipeRefreshView()
    }

    private fun setupSwipeRefreshView() {
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            showProgressDialog()
            viewModel.init(this)
            swipeRefresh.isRefreshing = false
        }
    }

    fun getCurrentFragment(): Fragment? {
        if (navHostFragment.childFragmentManager.fragments.size > 0) {
            return navHostFragment.childFragmentManager.fragments[0]
        }
        return null
    }
}