package com.example.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class Unsplash : Fragment() {

    private val unsplashBaseUrl = "https://api.unsplash.com/"
    private val randomPhotoEndpoint = "photos/random"
    private val apiKey = "B742vtmDqi_2b67hJQ1pzQilMyYsmpaVAK5n2P-j_lw" // Byt ut med din Unsplash API-nyckel

    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.randomimage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestQueue = Volley.newRequestQueue(requireContext())

        val randomBtn = view.findViewById<Button>(R.id.RandomBtn)

        randomBtn.setOnClickListener {
            Toast.makeText(context, "Ditt meddelande här", Toast.LENGTH_SHORT).show()
            loadRandomPhoto()
        }


    }


    private fun loadRandomPhoto() {
        val url = "$unsplashBaseUrl$randomPhotoEndpoint"
        val params = hashMapOf("client_id" to apiKey)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val imageUrl = response.getString("url").toString()
                    val randomImage = view?.findViewById<ImageView>(R.id.imageViewRandom)
                    if (randomImage != null) {
                        Glide.with(this).load(imageUrl).into(randomImage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}
