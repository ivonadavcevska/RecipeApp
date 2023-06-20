package mk.ukim.finki.mamamealmagic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.databinding.PopularRecipesItemsBinding
import mk.ukim.finki.mamamealmagic.models.CategoryRecipes

class PopularRecipesAdapter(): RecyclerView.Adapter<PopularRecipesAdapter.PopularRecipeViewHolder>() {

    lateinit var onItemClick: ((CategoryRecipes) -> Unit)
    private var recipesList = ArrayList<CategoryRecipes>()

    fun bind(recipesList: ArrayList<CategoryRecipes>){
        this.recipesList = recipesList
        notifyDataSetChanged()
    }

   inner class PopularRecipeViewHolder(val binding: PopularRecipesItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRecipeViewHolder {
        return PopularRecipeViewHolder(PopularRecipesItemsBinding.inflate(LayoutInflater.
        from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: PopularRecipeViewHolder, position: Int) {
        Glide.with(holder.itemView).load(recipesList[position].strMealThumb).into(holder.binding.popularRecipeImage)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(recipesList[position])
        }
    }
}