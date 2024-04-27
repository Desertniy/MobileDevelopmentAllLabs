package com.example.testandroidstudioproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val apiKey = "06c6757f4856c1bb8bf621a23c860e42"
    private val baseUrl = "http://api.openweathermap.org/data/2.5/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherButton = findViewById<Button>(R.id.getWeather)
        val weatherText = findViewById<TextView>(R.id.weather)
        weatherButton.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(WeatherApiService::class.java)

            val call = service.getWeather("Moscow", apiKey)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        val weatherInfo = weatherResponse?.main

                        weatherText.text = "${weatherInfo?.temp?.toString()}"
                    }
                }

                override fun onFailure(p0: Call<WeatherResponse>, p1: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }


        val activity1ToActivity2 = findViewById<Button>(R.id.button)
        activity1ToActivity2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val activity1ToActivity3 = findViewById<Button>(R.id.button4)
        activity1ToActivity3.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}
