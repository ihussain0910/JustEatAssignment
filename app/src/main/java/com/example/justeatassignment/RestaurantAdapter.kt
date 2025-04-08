package com.example.justeatassignment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RestaurantAdapter(private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.restaurant_name)
        //val cuisineTextView: TextView = view.findViewById(R.id.restaurant_cuisine)
        //val dealsTextView: TextView = view.findViewById(R.id.restaurant_deals)
        val cuisineChipGroup: ChipGroup = view.findViewById(R.id.cuisine_chip_group)
        val dealChipGroup: ChipGroup = view.findViewById(R.id.deal_chip_group)
        val addressTextView: TextView = view.findViewById(R.id.restaurant_address)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        val cuisines = restaurant.actualCuisines.toString().removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
        val deals = restaurant.splitDeals.toString().removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
        val context = RestaurantViewHolder(holder.itemView).itemView.context


        holder.cuisineChipGroup.removeAllViews()
        for (cuisine in cuisines) {
            Log.e("Cuisine", cuisine)
            val chip = Chip(context)
            Log.e("Context", context.toString())
            chip.text = cuisine
            chip.isClickable = false
            chip.isCheckable = false
            chip.setChipBackgroundColorResource(R.color.material_blue_100)
            chip.setTextAppearance(R.style.Widget_App_Chip)
            Log.d("ChipProperties", "Text: ${chip.text}, Background: ${chip.background}")
            holder.cuisineChipGroup.addView(chip)
        }

        holder.dealChipGroup.removeAllViews()
        for (deal in deals) {
            val chip = Chip(context)
            chip.text = deal
            chip.isClickable = false
            chip.isCheckable = false
            chip.setChipBackgroundColorResource(R.color.material_orange_100)
            chip.setTextAppearance(R.style.Widget_App_Chip)
            holder.dealChipGroup.addView(chip)

        }

        holder.nameTextView.text = restaurant.name
        //holder.cuisineTextView.text = "Cuisines: ${restaurant.actualCuisines.joinToString()}"
        //holder.dealsTextView.text = "Deals: ${restaurant.splitDeals.joinToString()}"
        holder.addressTextView.text = "${restaurant.address.firstLine}, ${restaurant.address.city}, ${restaurant.address.postcode}"
        holder.ratingBar.rating = restaurant.rating.starRating?.toFloat() ?: 0f
    }

    override fun getItemCount(): Int = restaurants.size
}
