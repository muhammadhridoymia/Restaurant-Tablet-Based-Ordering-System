
import RetrofitInstance.foodcategoryapi
import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class FoodCategory(
    @SerializedName("_id") val id: String,
    val name: String,
    val display: Boolean,
    val img: String
)
data class FoodCategoryResponse(
    val success: Boolean,
    val categories: List<FoodCategory>
)
interface FoodCategoryApiService {
    @GET("api/get/categories")
    suspend fun getFoodCategories(): FoodCategoryResponse
}
class FoodCategoryViewModel : ViewModel() {
    var categories by mutableStateOf<List<FoodCategory>>(emptyList())
        private set
    var loading by mutableStateOf(true)
        private set

    fun fetchfoodcategory() {
        viewModelScope.launch {
            try {
                val response = foodcategoryapi.getFoodCategories()
                if (response.success) {
                    categories = response.categories
                }
            } catch (e: Exception) {
                Log.e("Food Category", e.message ?: "Error fetching foods")
            } finally {
                loading = false
            }
        }
    }
}
