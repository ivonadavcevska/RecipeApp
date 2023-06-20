package mk.ukim.finki.mamamealmagic.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import mk.ukim.finki.mamamealmagic.CategoryActivity
import mk.ukim.finki.mamamealmagic.MainActivity
import mk.ukim.finki.mamamealmagic.RecipeActivity
import mk.ukim.finki.mamamealmagic.adapters.RecipesAdapter
import mk.ukim.finki.mamamealmagic.databinding.FragmentSearchBinding
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecyclerViewAdapter = RecipesAdapter()
        binding.searchedRecipesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerViewAdapter
        }

        binding.searchImageView.setOnClickListener{
            val searchQuery = binding.searchEditText.text.toString()
            if(searchQuery.isNotEmpty()){
                searchViewModel.searchRecipe(searchQuery)
            }
        }

        searchViewModel.observeSearchRecipeLiveData().observe(viewLifecycleOwner) { recipesList ->
            searchRecyclerViewAdapter.bind(recipesList)
        }

        searchRecyclerViewAdapter.onItemClick = { recipe ->
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra(HomeFragment.RecipeID, recipe.idMeal)
            intent.putExtra(HomeFragment.RecipeName, recipe.strMeal)
            intent.putExtra(HomeFragment.RecipeThumb, recipe.strMealThumb)
            startActivity(intent)
        }
    }



}