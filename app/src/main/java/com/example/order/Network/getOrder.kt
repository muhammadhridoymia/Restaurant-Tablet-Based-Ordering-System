import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Path


data class Item(
    val name: String,
    val price: Int,
    val quantity: Int,
    val received: Boolean,

)
data class Order(
    val _id: String,
    val status: String,
    val orderedAt: String,
    val message: String,
    val items: List<Item>
)


data class getOrderResponse(
    val success: Boolean,
    val order: Order?
)



interface getOrdersApi{
    @GET("api/get/order/in/mobile/{id}")
    suspend fun getOrders( @Path("id") id:String): getOrderResponse
}

class GetOrdersViewModel : ViewModel() {

    val order = mutableStateOf<Order?>(null)
    val loading = mutableStateOf(true)

    fun fetchOrder(userId: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.getorderapi.getOrders(userId)

                if (response.success) {
                    order.value = response.order
                    println("Order fetched successfully: ${response.order}")
                }
            } catch (e: Exception) {
                println("Order fetch error: ${e.message}")
            } finally {
                loading.value = false
            }
        }
    }
}
