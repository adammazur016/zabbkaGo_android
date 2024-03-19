import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface authApiService {
    @GET("login/{param1}/{param2}")
    suspend fun getTodo(
        @Path("param1") param1: String,
        @Path("param2") param2: String
    ): Response<Auth> // Replace "your_endpoint" with the actual endpoint path
}