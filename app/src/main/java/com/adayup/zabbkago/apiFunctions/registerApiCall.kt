package com.adayup.zabbkago.apiFunctions
import com.adayup.zabbkago.interfaces.RegisterApiService
import com.adayup.zabbkago.responsesDataClasses.Register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun registerApiCall (login: String, password: String): Register {
    val service = RetrofitClient.retrofitInstance.create(RegisterApiService::class.java)
    val response: Response<Register> = withContext(Dispatchers.IO) {
        service.Register(login, password)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return Register("Error", "Error in API call")
}