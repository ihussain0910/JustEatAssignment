package com.example.justeatassignment


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RestaurantAdapter
    private lateinit var toolbar: Toolbar

    private val restaurantGetter = RestaurantGetter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)



        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        searchView.setQueryHint("Enter postcode" )
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { fetchRestaurants(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun noResturantsAlert(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Restaurants Found")
            builder.setMessage("Please enter a valid postcode")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

    }
    private fun fetchRestaurants(postcode: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val restaurants = restaurantGetter.fetchRestaurants(postcode)
            withContext(Dispatchers.Main) {

                if (!restaurants.isNullOrEmpty()) {
                    adapter = RestaurantAdapter(restaurants)
                    recyclerView.adapter = adapter
                } else {

                    Log.e("MainActivity", "No restaurants found!")
                    noResturantsAlert()
                }
            }
        }
    }


}
