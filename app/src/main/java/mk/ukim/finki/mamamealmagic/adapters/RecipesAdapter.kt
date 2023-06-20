package mk.ukim.finki.mamamealmagic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.databinding.RecipesByCategoryBinding
import mk.ukim.finki.mamamealmagic.models.Meal

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.FavoriteRecipesViewHolder>() {

    private var recipesList = ArrayList<Meal>()
    lateinit var onItemClick: ((Meal) -> Unit)

    fun bind(favoriteRecipesList: List<Meal>){
        this.recipesList = favoriteRecipesList as ArrayList<Meal>
        notifyDataSetChanged()
    }

    inner class FavoriteRecipesViewHolder(val binding: RecipesByCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder {
        return FavoriteRecipesViewHolder(RecipesByCategoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(recipesList[position].strMealThumb).into(holder.binding.recipeImageByCategory)
        holder.binding.recipeNameByCategory.text = recipesList[position].strMeal

        holder.itemView.setOnClickListener{
            onItemClick.invoke(recipesList[position])
        }
    }
}