package guy.sawyer.com.cms.rest

import android.content.Context
import androidx.lifecycle.MutableLiveData
import guy.sawyer.com.cms.model.City
import guy.sawyer.com.cms.model.CmsResponse
import guy.sawyer.com.cms.model.Mall
import guy.sawyer.com.cms.model.Shop
import guy.sawyer.com.cms.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CmsRepository {

    companion object {
        const val BASE_URL = "http://www.mocky.io/"
    }

    private var retrofit: Retrofit? = null
    var cities = MutableLiveData<List<City>>()
    var isStaleResponse = MutableLiveData<Boolean>()

    fun connectAndGetApiData(context: Context): MutableLiveData<List<City>>  {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkUtils().customOkHttp(context))
                .build()
        }

        val cmsApiService: CmsApiService = retrofit!!.create(CmsApiService::class.java)

        val call: Call<CmsResponse> = cmsApiService.getCmsData()
        call.enqueue(object: Callback<CmsResponse> {
            override fun onResponse(call: Call<CmsResponse>, response: Response<CmsResponse>) {
                if (response.isSuccessful) {
                    isStaleResponse.value = response.raw().networkResponse() == null
                    cities.value = response.body()?.cities
                }
            }
            override fun onFailure(call: Call<CmsResponse>, throwable: Throwable) {
                cities.value = emptyList()
            }
        })
        return cities
    }

    fun provideCities() = cities

    fun provideCityByName(name: String): City? {
        return cities.value?.find {
            it.name == name
        }
    }

    fun provideMallsByCity(cityName: String?): List<Mall>? {
        return cities.value?.find {
            it.name == cityName
        }?.malls
    }

    fun provideMallInCity(cityName: String, mallName: String): Mall? {
        return cities.value?.find {
            it.name == cityName
        }?.malls?.find {
            it.name == mallName
        }
    }

    fun provideShopsInMall(mallName: String?): List<Shop>? {
        var malls: List<Mall> = emptyList()
        cities.value?.forEach { city ->
            malls += city.malls
        }

        return malls.find { mall ->
            mall.name == mallName
        }?.shops
    }

    fun provideSingleShopInMall(mallName: String, shopName: String): Shop? {
        var malls: List<Mall> = emptyList()
        cities.value?.forEach { city ->
            malls += city.malls
        }

        return malls.find { mall ->
            mall.name == mallName
        }?.shops?.find { shop ->
            shop.name == shopName
        }
    }

    fun provideShopsInCity(cityName: String?): List<Shop>? {
        var malls: List<Mall> = emptyList()
        malls = cities.value?.find { city ->
            city.name == cityName
        }!!.malls

        var shops: List<Shop> = emptyList()
        malls.forEach { mall ->
            mall.shops.forEach {shop ->
                shops += shop
            }
        }
        return shops
    }
}