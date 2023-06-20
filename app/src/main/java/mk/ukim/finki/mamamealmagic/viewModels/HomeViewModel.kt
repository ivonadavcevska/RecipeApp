package mk.ukim.finki.mamamealmagic.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.mamamealmagic.models.*
import mk.ukim.finki.mamamealmagic.retrofit.RetrofitInstance
import mk.ukim.finki.mamamealmagic.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val appDatabase: AppDatabase) : ViewModel() {

    private var randomRecipeLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryRecipes>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteRecipesLiveData = appDatabase.recipeDao().getAll()
    private val searchRecipesLiveData = MutableLiveData<List<Meal>>()

    fun getRandomRecipe() {
        RetrofitInstance.recipeApi.getRandomRecipe().enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.body() != null) {
                    val randomRecipe: Meal = response.body()!!.meals[0]
                    randomRecipeLiveData.value = randomRecipe
                } else return
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularRecipes() {
        RetrofitInstance.recipeApi.getPopularRecipes("Pasta").enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getCategories() {
        RetrofitInstance.recipeApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoriesLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    fun searchRecipe(search: String) {
        RetrofitInstance.recipeApi.searchRecipe(search).enqueue(object : Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                val recipeList = response.body()?.meals
                recipeList.let {
                    searchRecipesLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }


    fun observeSearchRecipeLiveData(): LiveData<List<Meal>> {
        return searchRecipesLiveData
    }

    fun observeRandomRacipeLiveData(): LiveData<Meal> {
        return randomRecipeLiveData
    }

    fun observePopularRecipesLiveData(): LiveData<List<CategoryRecipes>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoriteRecipesLiveData(): LiveData<List<Meal>> {
        return favoriteRecipesLiveData
    }
}