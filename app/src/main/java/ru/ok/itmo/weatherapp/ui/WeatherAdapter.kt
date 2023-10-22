package ru.ok.itmo.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.example.R
import ru.ok.itmo.weatherapp.network.dto.WeatherResponce

class WeatherAdapter : ListAdapter<WeatherResponce, WeatherAdapter.ViewHolder>(WeatherDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCity = itemView.findViewById<TextView>(R.id.tvCity)
        private val tvWeather = itemView.findViewById<TextView>(R.id.tvWeather)
        private val tvCountry = itemView.findViewById<TextView>(R.id.tvCountry)

        fun bind(item: WeatherResponce) {
            tvCity.text = item.location.city
            tvCountry.text = item.location.country
            tvWeather.text = item.current.tempC.toString()
        }
    }

    object WeatherDiffCallback : DiffUtil.ItemCallback<WeatherResponce>() {
        override fun areItemsTheSame(oldItem: WeatherResponce, newItem: WeatherResponce): Boolean {
            return oldItem.location.city == newItem.location.city
        }

        override fun areContentsTheSame(
            oldItem: WeatherResponce,
            newItem: WeatherResponce
        ): Boolean {
            return oldItem == newItem
        }
    }
}