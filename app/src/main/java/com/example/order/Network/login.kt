import retrofit2.http.Body
import retrofit2.http.POST
import com.google.gson.annotations.SerializedName
import retrofit2.http.Headers


data class LoginRequest(
    val phone: String,
    val password: String
)
data class User(
    val id: String,
    val name: String,
    val img: String
)

data class LoginResponse(
     val success: Boolean,
     val message: String? = null,
     val user: User
)


interface LoginApiService {
    @POST("api/login/user")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
