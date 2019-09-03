package guy.sawyer.com.entershopper.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import guy.sawyer.com.cms.model.Shop
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.ShopItemBinding

class ShopsAdapter(private var shops: List<Shop>) : RecyclerView.Adapter<ShopsAdapter.ShopsViewHolder>(){

    private lateinit var dataBinding: ShopItemBinding

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ShopsViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.shop_item, parent, false)
        return ShopsViewHolder(dataBinding)
    }

    override fun onBindViewHolder(@NonNull holder: ShopsViewHolder, position: Int) {
        holder.shopName.text = shops[position].name
    }

    override fun getItemCount(): Int {
        return shops.count()
    }

    inner class ShopsViewHolder(itemBinding: ShopItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var shopName: TextView = itemBinding.shopName
    }
}