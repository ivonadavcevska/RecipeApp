package mk.ukim.finki.mamamealmagic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import mk.ukim.finki.mamamealmagic.MainActivity
import mk.ukim.finki.mamamealmagic.R
import mk.ukim.finki.mamamealmagic.RecipeActivity
import mk.ukim.finki.mamamealmagic.adapters.CategoriesAdapter
import mk.ukim.finki.mamamealmagic.adapters.PopularRecipesAdapter
import mk.ukim.finki.mamamealmagic.databinding.FragmentHomeBinding
import mk.ukim.finki.mamamealmagic.models.CategoryRecipes
import mk.ukim.finki.mamamealmagic.models.Meal
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModel



class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomRecipe: Meal
    private lateinit var popularRecipesAdapter: PopularRecipesAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val RecipeID = "mk.ukim.finki.mamamealmagic.fragments.idRecipe"
        const val RecipeName = "mk.ukim.finki.mamamealmagic.fragments.nameRecipe"
        const val RecipeThumb = "mk.ukim.finki.mamamealmagic.fragments.thumbRecipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
        popularRecipesAdapter = PopularRecipesAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomRecipe()

        homeViewModel.observeRandomRacipeLiveData().observe(viewLifecycleOwner) { recipe ->
            Glide.with(this@HomeFragment).load(recipe!!.strMealThumb)
                .into(binding.randomMealImage)
            this.randomRecipe = recipe
        }

        binding.cardMeal.setOnClickListener{
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra(RecipeID, randomRecipe.idMeal)
            intent.putExtra(RecipeName, randomRecipe.strMeal)
            intent.putExtra(RecipeThumb, randomRecipe.strMealThumb)
            startActivity(intent)
        }

        homeViewModel.getPopularRecipes()

        binding.popularItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularRecipesAdapter
        }

        homeViewModel.observePopularRecipesLiveData().observe(viewLifecycleOwner){ recipes ->
            popularRecipesAdapter.bind(recipesList = recipes as ArrayList<CategoryRecipes>)
        }

        popularRecipesAdapter.onItemClick = { recipe ->
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra(RecipeID, recipe.idMeal)
            intent.putExtra(RecipeName, recipe.strMeal)
            intent.putExtra(RecipeThumb, recipe.strMealThumb)
            startActivity(intent)
        }

        binding.searchIcon.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

}