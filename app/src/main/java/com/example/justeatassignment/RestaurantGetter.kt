package com.example.justeatassignment

import android.util.Log

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

// Data models
data class RestaurantResponse(
    @SerializedName("restaurants") val restaurants: List<Restaurant>
)

data class Address(
    @SerializedName("firstLine") val firstLine: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("postalCode") val postcode: String?
)

data class Rating(
    @SerializedName("starRating") val starRating: Double?,
    @SerializedName("count") val count: Int?,
    @SerializedName("userRating") val userRating: Double?
)

data class Cuisine(
    @SerializedName("name") val name: String
)


data class Restaurant(
    @SerializedName("name") val name: String,
    @SerializedName("cuisines") val cuisines: List<Cuisine>,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("address") val address: Address,

    //Wanted to split the deals from the cuisines

    var splitDeals: MutableList<String> = mutableListOf(),
    var actualCuisines: MutableList<String> = mutableListOf()
) {
    fun splitCuisines() {
        val dealNames = listOf(
            "Low Delivery Fee", "Deals", "Cheeky Tuesday", "Collect stamps",
            "£8 off", "Freebies", "Your favourites", "Local Legends"
        )
        if (splitDeals == null) splitDeals = mutableListOf()
        if (actualCuisines == null) actualCuisines = mutableListOf()

        cuisines.forEach { cuisine ->
            val cuisineName = cuisine.name ?: ""
            Log.d("DEBUG", "Current cuisine: $cuisineName")

            if (dealNames.contains(cuisineName)) {
                splitDeals!!.add(cuisineName) // Use !! to avoid null crashes
            } else {
                actualCuisines!!.add(cuisineName)
            }

            Log.d("DEBUG", "Updated splitDeals list: $splitDeals")
            Log.d("DEBUG", "Updated actualCuisines list: $actualCuisines")
        }
    }
}

class RestaurantGetter {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun fetchRestaurants(postcode: String, limit: Int = 10): List<Restaurant>? {
        val request = Request.Builder()
            .url("https://uk.api.just-eat.io/discovery/uk/restaurants/enriched/bypostcode/$postcode")
            .build()

        return try {
            val response = client.newCall(request).execute()

            Log.i("API_DEBUG", "Response Code: ${response.code}")
            Log.i("API_DEBUG", "Response Message: ${response.message}")
            Log.i("API_DEBUG", "Headers: ${response.headers}")

            if (!response.isSuccessful) throw Exception("API Request Failed: ${response.code}")

            val jsonResponse = response.body?.string()

            if (jsonResponse.isNullOrEmpty()) {
                Log.e("API_DEBUG", "Response Body is empty!")
                return null
            }

            Log.i("API_RESPONSE", "JSON: $jsonResponse")

            val parsedResponse = gson.fromJson(jsonResponse, RestaurantResponse::class.java)

            parsedResponse.restaurants.forEach { restaurant ->
                restaurant.splitCuisines() // Split cuisines after fetching
            }
            parsedResponse.restaurants.take(limit)

        } catch (e: Exception) {
            Log.e("API_ERROR", "Error: ${e.localizedMessage}", e)
            null
        }
    }
}
