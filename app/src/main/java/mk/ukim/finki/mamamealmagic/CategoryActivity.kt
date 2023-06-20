package mk.ukim.finki.mamamealmagic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import mk.ukim.finki.mamamealmagic.adapters.RecipesByCategoryAdapter
import mk.ukim.finki.mamamealmagic.databinding.ActivityCategoryBinding
import mk.ukim.finki.mamamealmagic.fragments.CategoriesFragment
import mk.ukim.finki.mamamealmagic.fragments.HomeFragment
import mk.ukim.finki.mamamealmagic.viewModels.CategoryRecipesViewModel

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var categoryRecipesViewModel: CategoryRecipesViewModel
    lateinit var recipesByCategoryAdapter: RecipesByCategoryAdapter

    companion object{
        const val RecipeID = "mk.ukim.finki.mamamealmagic.fragments.idRecipe"
        const val RecipeName = "mk.ukim.finki.mamamealmagic.fragments.nameRecipe"
        const val RecipeThumb = "mk.ukim.finki.mamamealmagic.fragments.thumbRecipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRecipesViewModel = ViewModelProviders.of(this)[CategoryRecipesViewModel::class.java]

        recipesByCategoryAdapter = RecipesByCategoryAdapter()
        binding.recipesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = recipesByCategoryAdapter
        }

        categoryRecipesViewModel.getRecipesByCategory(intent.getStringExtra(CategoriesFragment.CategoryName)!!)

        categoryRecipesViewModel.observeRecipesLiveData().observe(this) { recipesList ->
            recipesByCategoryAdapter.bind(recipesList)
        }

        recipesByCategoryAdapter.onItemClick = { recipe ->
            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra(RecipeID, recipe.idMeal)
            intent.putExtra(RecipeName, recipe.strMeal)
            intent.putExtra(RecipeThumb, recipe.strMealThumb)
            startActivity(intent)
        }

    }
}