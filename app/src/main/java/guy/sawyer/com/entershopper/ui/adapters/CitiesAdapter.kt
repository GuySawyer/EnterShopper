package guy.sawyer.com.entershopper.ui.adapters

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: CitiesViewHolder, position: Int) {
        var shopCount = 0
        cities[position].malls.forEach {
           shopCount += it.shops.count()
        }
        holder.cityInfo.text = "${cities[position].malls.count()} Malls with $shopCount Shops"
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
        var cityInfo: TextView = itemBinding.cityInfo

        init {
            itemBinding.root.setOnClickListener {
                cityItemClickListener.onCityClicked(cities[adapterPosition])
            }
        }
    }
}