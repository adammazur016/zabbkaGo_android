import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://16.16.94.104:5000/" // Replace "your_base_url" with the actual base URL of your API

    val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
