package guy.sawyer.com.cms.rest

import guy.sawyer.com.cms.model.CmsResponse
import retrofit2.Call
import retrofit2.http.GET

internal interface CmsApiService {

    @GET("v2/5b7e8bc03000005c0084c210")
    fun getCmsData() : Call<CmsResponse>
}