import com.adayup.zabbkago.responsesDataClasses.Achievement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetAchievementDetailsService {
    @GET("/v1/achievement/{achievement_id}")
    suspend fun GetAcheivementsDetails(
        @Path("achievement_id") achievement_id: Int,
    ): Response<Achievement>
}