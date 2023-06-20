package mk.ukim.finki.mamamealmagic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.databinding.RecipesByCategoryBinding
import mk.ukim.finki.mamamealmagic.models.CategoryRecipes


class RecipesByCategoryAdapter: RecyclerView.Adapter<RecipesByCategoryAdapter.RecipesByCategoryViewHolder>() {

    private var recipesList = ArrayList<CategoryRecipes>()
    lateinit var onItemClick: ((CategoryRecipes) -> Unit)

    fun bind(recipesList: List<CategoryRecipes>){
        this.recipesList = recipesList as ArrayList<CategoryRecipes>
        notifyDataSetChanged()
    }

    inner class RecipesByCategoryViewHolder(val binding: RecipesByCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesByCategoryViewHolder {
        return RecipesByCategoryViewHolder(RecipesByCategoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipesByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(recipesList[position].strMealThumb).into(holder.binding.recipeImageByCategory)
        holder.binding.recipeNameByCategory.text = recipesList[position].strMeal

        holder.itemView.setOnClickListener{
            onItemClick.invoke(recipesList[position])
        }
    }
}