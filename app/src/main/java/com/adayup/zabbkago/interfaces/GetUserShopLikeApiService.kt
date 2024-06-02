import com.adayup.zabbkago.responsesDataClasses.GetUserShopLike
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetUserShopLikeApiService {
    @GET("/v1/shop/{shop_id}/like")
    suspend fun GetUserShopLike(
        @Path("shop_id") shop_id: Int,
        @Query("session_token") session_token: String
    ): Response<GetUserShopLike>

}