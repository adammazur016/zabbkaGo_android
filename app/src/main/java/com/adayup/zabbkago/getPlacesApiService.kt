import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

public interface getPlacesApiService {
    @POST("test")
    suspend fun GetPlaces(
        @Query("api_key") apiKey: String,
    ): Response<List<Place>> // Adjusted to expect a List<Place> directly
}