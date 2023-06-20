package mk.ukim.finki.mamamealmagic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import mk.ukim.finki.mamamealmagic.MainActivity
import mk.ukim.finki.mamamealmagic.RecipeActivity

import mk.ukim.finki.mamamealmagic.adapters.RecipesAdapter
import mk.ukim.finki.mamamealmagic.databinding.FragmentFavoritesBinding
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesViewModel: HomeViewModel
    private lateinit var favoriteRecipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesViewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteRecipesAdapter = RecipesAdapter()
        binding.favoritesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = favoriteRecipesAdapter
        }

        favoritesViewModel.observeFavoriteRecipesLiveData().observe(viewLifecycleOwner) { recipes ->
            favoriteRecipesAdapter.bind(recipes)
        }

        favoriteRecipesAdapter.onItemClick = { recipe ->
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra(HomeFragment.RecipeID, recipe.idMeal)
            intent.putExtra(HomeFragment.RecipeName, recipe.strMeal)
            intent.putExtra(HomeFragment.RecipeThumb, recipe.strMealThumb)
            startActivity(intent)
        }
    }
}