package mk.ukim.finki.mamamealmagic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.databinding.CategoryItemsBinding
import mk.ukim.finki.mamamealmagic.models.Category

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun bind(categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.categoryImage)
        holder.binding.categoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }
}