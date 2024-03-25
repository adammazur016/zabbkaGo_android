import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface authApiService {
    @POST("login")
    suspend fun getTodo(
        @Query("user") user: String,
        @Query("password") password: String
    ): Response<Auth> // Ensure Auth is correctly defined to match the expected response
}