package guy.sawyer.com.entershopper.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import guy.sawyer.com.cms.model.City
import guy.sawyer.com.cms.model.Mall
import guy.sawyer.com.cms.model.Shop
import guy.sawyer.com.cms.rest.CmsRepository

class MainViewModel : ViewModel() {

    private lateinit var mutableLiveData: MutableLiveData<List<City>>
    private lateinit var isResponseStale: MutableLiveData<Boolean>
    private var cmsRepo = CmsRepository()

    fun init(context: Context) {
        mutableLiveData = cmsRepo.connectAndGetApiData(context)
        isResponseStale = cmsRepo.isStaleResponse
    }

    fun getCacheResponseSate(): LiveData<Boolean> = isResponseStale

    fun getCities(): LiveData<List<City>> = cmsRepo.provideCities()

    fun getCity(cityName: String): City? = cmsRepo.provideCityByName(cityName)

    fun getMallsInCity(cityName: String?): List<Mall>? = cmsRepo.provideMallsByCity(cityName)

    fun getMallInCity(cityName: String, mallName: String): Mall? = cmsRepo.provideMallInCity(cityName, mallName)

    fun getShopsInMall(mallName: String?): List<Shop>? = cmsRepo.provideShopsInMall(mallName)

    fun getShopInMall(mallName: String, shopName: String): Shop? = cmsRepo.provideSingleShopInMall(mallName, shopName)

    fun getShopsInCity(cityName: String): List<Shop>? = cmsRepo.provideShopsInCity(cityName)
}
