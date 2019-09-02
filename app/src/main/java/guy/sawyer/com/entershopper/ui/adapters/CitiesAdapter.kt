package guy.sawyer.com.entershopper.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import guy.sawyer.com.cms.model.City
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.CityItemBinding


class CitiesAdapter(private var cities: List<City>, private var cityItemClickListener: CityItemClickListener) : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    private lateinit var dataBinding: CityItemBinding

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CitiesViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.city_item, parent, false)
        return CitiesViewHolder(dataBinding, cityItemClickListener)
    }

    override fun onBindViewHolder(@NonNull holder: CitiesViewHolder, position: Int) {
        holder.cityId.text = cities[position].id.toString()
        holder.cityName.text = cities[position].name
    }

    override fun getItemCount(): Int {
        return cities.count()
    }

    interface CityItemClickListener {
        fun onCityClicked(city: City)
    }

    inner class CitiesViewHolder(itemBinding: CityItemBinding, private var cityItemClickListener: CityItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {
        var cityName: TextView = itemBinding.cityName
        var cityId: TextView = itemBinding.cityId

        init {
            itemBinding.root.setOnClickListener {
                cityItemClickListener.onCityClicked(cities[adapterPosition])
            }
        }
    }
}