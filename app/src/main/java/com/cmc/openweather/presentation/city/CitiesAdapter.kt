package com.cmc.openweather.presentation.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmc.openweather.R
import com.cmc.openweather.databinding.ItemCityBinding
import com.cmc.openweather.domain.model.CityForecast

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    val cities: MutableList<CityForecast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
    )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = cities.size

    internal fun updateData(cities: MutableList<CityForecast>) {
        this.cities.clear()
        this.cities.addAll(cities)
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemCityBinding.bind(itemView)

        fun bindView(position: Int) {
            binding.run {
                itemView.run {
                    tvCityName.text = cities[position].name
                }
                itemCity.setOnClickListener {
                    onItemClicked.invoke(position)
                }
            }
        }
    }
}