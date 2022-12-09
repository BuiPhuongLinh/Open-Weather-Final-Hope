package com.cmc.openweather.presentation.wholeday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmc.openweather.R
import com.cmc.openweather.common.timeUTCToLocal
import com.cmc.openweather.databinding.ItemHourForecastBinding
import com.cmc.openweather.domain.model.Hourly

class WholeDayAdapter : RecyclerView.Adapter<WholeDayAdapter.HourForecastViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}
    val hourlyForecast = mutableListOf<Hourly>()
    var isCelsius: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HourForecastViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_hour_forecast, parent, false)
    )


    override fun onBindViewHolder(holder: HourForecastViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = hourlyForecast.size

    internal fun updateData(hourlyForecast: MutableList<Hourly>, isCelsius: Boolean) {
        this@WholeDayAdapter.hourlyForecast.clear()
        this@WholeDayAdapter.hourlyForecast.addAll(hourlyForecast)
        this@WholeDayAdapter.isCelsius = isCelsius
        notifyDataSetChanged()
    }

    inner class HourForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemHourForecastBinding.bind(itemView)
        fun bindView(position: Int) {
            binding.run {
                itemView.run {
                    textHour.text = hourlyForecast[position].dt?.timeUTCToLocal()
                    textTemperature.text = itemView.context.getString(
                        if (isCelsius) R.string.temperature_c else R.string.temperature_f,
                        hourlyForecast[position].temp.toString()
                    )
                    textHumidity.text = itemView.context.getString(
                        R.string.humidity,
                        hourlyForecast[position].humidity.toString()
                    )
                }
                itemHour.setOnClickListener {
                    onItemClicked.invoke(position)
                }
            }
        }
    }
}