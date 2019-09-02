package guy.sawyer.com.cms.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import guy.sawyer.com.cms.rest.CmsRepository
import okhttp3.Cache
import okhttp3.OkHttpClient

class NetworkUtils {

    companion object {
        const val cacheSize = (5 * 1024 * 1024).toLong() //Limit to 5MB
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun customOkHttp(context: Context): OkHttpClient {

        //Use cutom OkHttp implementation to allow caching of network data
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            // Specify the cache
            .cache(myCache)
            // Add an Interceptor to the OkHttpClient.
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!) {
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build() //Will discard cache if older than 5 seconds and hasNetwork Connection
                }
                else request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build() //If no network, will use cache up to 7 days old, otherwise discard and throw error
                chain.proceed(request)
            }
            .build()
    }
}