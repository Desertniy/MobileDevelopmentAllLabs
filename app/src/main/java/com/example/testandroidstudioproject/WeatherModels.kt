package com.example.testandroidstudioproject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class WeatherResponse(val main: WeatherInfo)
data class WeatherInfo(val temp: Float, val humidity: Int, val wind: WindInfo)
data class WindInfo(val speed: Float)


interface WeatherApiService {
    @GET("weather")
    fun getWeather(@Query("q") city: String, @Query("appid") apiKey: String): Call<WeatherResponse>
}