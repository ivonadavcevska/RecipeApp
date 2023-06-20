package mk.ukim.finki.mamamealmagic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.databinding.ActivityRecipeBinding
import mk.ukim.finki.mamamealmagic.fragments.HomeFragment
import mk.ukim.finki.mamamealmagic.models.Meal
import mk.ukim.finki.mamamealmagic.room.AppDatabase
import mk.ukim.finki.mamamealmagic.viewModels.RecipeViewModel
import mk.ukim.finki.mamamealmagic.viewModels.RecipeViewModelFactory

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appDatabase = AppDatabase.getInstance(this)
        val viewModelFactory = RecipeViewModelFactory(appDatabase)
        recipeViewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        val intent = intent
        val recipeId: String = intent.getStringExtra(HomeFragment.RecipeID)!!
        val recipeName: String = intent.getStringExtra(HomeFragment.RecipeName)!!
        val recipeImage: String = intent.getStringExtra(HomeFragment.RecipeThumb)!!

        Glide.with(applicationContext)
            .load(recipeImage)
            .into(binding.recipeImageDetail)


        recipeViewModel.getRecipeDetails(recipeId)

        var recipeToFavorites:Meal? = null

        recipeViewModel.observeRecipeDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                recipeToFavorites = t
                binding.recipeName.text = t!!.strMeal
                binding.categoryDetail.text = "Category : ${t!!.strCategory}"
                binding.areaDetail.text = "Area : ${t!!.strArea}"
                binding.detailsForInstructions.text = t.strInstructions
            }

        })

        binding.favoritesButton.setOnClickListener{
            recipeToFavorites?.let {
                recipeViewModel.insertRecipe(it)
                Toast.makeText(this, "Recipe Added To Favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }
}