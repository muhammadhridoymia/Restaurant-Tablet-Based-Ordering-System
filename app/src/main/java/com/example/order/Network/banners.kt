import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.http.GET

data class banners(
    val active: Boolean,
    val img: String
)
data class BannerResponse(
    val success: Boolean,
    val banners: List<banners>
)
interface BannerApi {
    @GET("api/get/banners")
    suspend fun getBanners(): BannerResponse
}

class BannerViewModel : ViewModel(){

    val Bannerdata = mutableStateOf<List<banners>>(emptyList())
    val loading = mutableStateOf(true)
    fun fetchBanners(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.bannerapi.getBanners()
                if (response.success){
                    Bannerdata.value = response.banners
                    loading.value = false
                    println("Banners data: ${response.banners}")
                }
            }catch (e:Exception){
                println(e.message)
            }finally {
                loading.value = false
            }
        }
    }

}