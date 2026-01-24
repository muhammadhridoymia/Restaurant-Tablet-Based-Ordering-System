import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.LoginDataStore
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.POST
import androidx.compose.ui.platform.LocalContext





data class OrderItem(
    val foodId: String,
    val quantity: Int,
)

data class OrderRequest(
    val userId: String = "",
    val name: String,
    val items: List<OrderItem>,
)

data class OrderResponse(
    val success: Boolean,
    val message: String
)


interface OrderApi {
    @POST("api/order/submit")
    suspend fun submitOrder(@Body orderRequest: OrderRequest): OrderResponse
}


class OrderViewModel : ViewModel() {

    val loading = mutableStateOf(false)
    val popupMessage = mutableStateOf<String?>(null)
    val success = mutableStateOf(false)

    fun resetPopup() {
        popupMessage.value = null
        success.value = false
    }


    fun submitOrder(foodId: String, quantity: Int,userId:String,name: String) {

        viewModelScope.launch {
            loading.value = true
            try {
                val order = OrderRequest(
                    userId = userId,
                    name = name,
                    items = listOf(
                        OrderItem(foodId, quantity)
                    )

                )

                val response = RetrofitInstance.orderapi.submitOrder(order)

                if (response.success) {
                    popupMessage.value = response.message
                    success.value = true
                } else {
                    popupMessage.value = response.message
                    success.value = false
                }
            } catch (e: Exception) {
                popupMessage.value = e.message ?: "Error"
            } finally {
                loading.value = false
            }
        }
    }
}
