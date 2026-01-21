import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Path


interface CategoryFoodApi{
    @GET("api/get/categoryfoods/{id}")
    suspend fun getFoodList( @Path("id") id:String): FoodResponse
}


class CategoryFoodListViewModel : ViewModel(){
    val FoodList = mutableStateOf<List<Food>>(emptyList())
    val loading = mutableStateOf(true)
    fun fetchFoodList(id:String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.categoryfoodlistapi.getFoodList(id)
                if (response.success){
                    FoodList.value = response.foods
                    println("Category data: ${response.foods}")
                }
            }catch (e:Exception){
                println(e.message)
            }finally {
                loading.value = false
            }
        }
    }
}