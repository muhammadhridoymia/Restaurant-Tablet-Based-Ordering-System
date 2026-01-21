import RetrofitInstance.foodapi
import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch



import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class Food(
    @SerializedName("_id") val id: String,
    val name: String,
    val price: Int,
    val img: String,
    val display:Boolean,
    val popular: Boolean,
)
data class FoodResponse(
    val success: Boolean,
    val foods: List<Food>
)
interface FoodApiService {
    @GET("api/get/foods")
    suspend fun getFoods(): FoodResponse
}

class FoodViewModel : ViewModel() {
    var foods by mutableStateOf<List<Food>>(emptyList())
        private set
    var loading by mutableStateOf(true)
        private set

    fun fetchFoods() {
        viewModelScope.launch {
            try {
                val response = foodapi.getFoods()
                if (response.success) {
                    foods = response.foods
                    println("Foods: $response")
                }
            } catch (e: Exception) {
                Log.e("FOOD", e.message ?: "Error fetching foods")
            } finally {
                loading = false
            }
        }
    }
}
