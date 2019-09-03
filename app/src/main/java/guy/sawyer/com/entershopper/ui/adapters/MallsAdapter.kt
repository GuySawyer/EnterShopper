package guy.sawyer.com.entershopper.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import guy.sawyer.com.cms.model.Mall
import guy.sawyer.com.entershopper.R
import guy.sawyer.com.entershopper.databinding.MallItemBinding

class MallsAdapter(private var malls: List<Mall>, private var mallItemClickListener: MallItemClickListener) : RecyclerView.Adapter<MallsAdapter.MallsViewHolder>(){

    private lateinit var dataBinding: MallItemBinding

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MallsViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.mall_item, parent, false)
        return MallsViewHolder(dataBinding, mallItemClickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: MallsViewHolder, position: Int) {
        holder.mallName.text = malls[position].name
        holder.mallInfo.text = "${malls[position].shops.count()} Shops"
    }

    override fun getItemCount(): Int {
        return malls.count()
    }

    interface MallItemClickListener {
        fun onCityClicked(mall: Mall)
    }

    inner class MallsViewHolder(itemBinding: MallItemBinding, private var mallItemClickListener: MallItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {
        var mallName: TextView = itemBinding.mallName
        var mallInfo: TextView = itemBinding.mallInfo

        init {
            itemBinding.root.setOnClickListener {
                mallItemClickListener.onCityClicked(malls[adapterPosition])
            }
        }
    }
}