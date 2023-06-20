package mk.ukim.finki.mamamealmagic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import mk.ukim.finki.mamamealmagic.CategoryActivity
import mk.ukim.finki.mamamealmagic.MainActivity

import mk.ukim.finki.mamamealmagic.R
import mk.ukim.finki.mamamealmagic.RecipeActivity
import mk.ukim.finki.mamamealmagic.adapters.CategoriesAdapter
import mk.ukim.finki.mamamealmagic.adapters.RecipesByCategoryAdapter
import mk.ukim.finki.mamamealmagic.databinding.FragmentCategoriesBinding
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesViewModel: HomeViewModel

    companion object{
        const val CategoryName = "mk.ukim.finki.mamamealmagic.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoriesViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }

        categoriesViewModel.getCategories()

        categoriesViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.bind(categories)
        }

        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CategoryName, category.strCategory)
            startActivity(intent)
        }


    }
}