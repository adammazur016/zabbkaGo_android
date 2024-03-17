import retrofit2.Call
import retrofit2.http.GET

interface TodoApiService {
    @GET("todo1") // Replace "your_endpoint" with the actual endpoint path
    fun getTodo(): Call<Todo>
}