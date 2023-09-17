package com.example.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONException
import com.android.volley.Request



class OpenWeather : Fragment() {

    private val apiKey = "aeb1d29b8db6b9a654e6d73b49038c3f" // Byt ut med din OpenWeather API-nyckel
    private val apiEndpoint = "https://api.openweathermap.org/data/2.5/weather"

    private lateinit var requestQueue: RequestQueue
    private lateinit var locationEditText: EditText
    private lateinit var buttonDark: Button
    private lateinit var textDark: TextView
    private lateinit var imageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.openweather, container, false)
        requestQueue = Volley.newRequestQueue(requireContext())
        locationEditText = view.findViewById(R.id.locationEditText)
        buttonDark = view.findViewById(R.id.sökButton)
        textDark = view.findViewById(R.id.textDark)
        imageView = view.findViewById(R.id.imageview)


        buttonDark.setOnClickListener { v->
            Toast.makeText(context, "Ditt meddelande här", Toast.LENGTH_SHORT).show()

            val city = locationEditText.text.toString()
            Log.d("Omid", city)
            if (city.isNotEmpty()) {
                fetchWeatherData(city)
            }
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun fetchWeatherData(cityName: String){

        val url = "$apiEndpoint?q=$cityName&appid=$apiKey"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                try {
                    val main = response.getJSONObject("main")
                    val temperatureKelvin = main.getDouble("temp")
                    val temperatureCelsius = temperatureKelvin - 273.15
                    val weather = response.getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("main")

                    val weatherInfo = "Temperature: ${
                        String.format(
                            "%.2f",
                            temperatureCelsius
                        )
                    }°C\nWeather: $weather"

                    textDark.text = weatherInfo

                    // Ladda väderikon baserat på ikon från API
                    val weatherIcon = response.getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("icon")

                    val iconUrl = "https://openweathermap.org/img/wn/$weatherIcon.png"
                    Glide.with(requireContext())
                        .load(iconUrl)
                        .into(imageView)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // Hantera fel här om något går fel med nätverksanropet
            })

        requestQueue.add(jsonObjectRequest)
    }


}
