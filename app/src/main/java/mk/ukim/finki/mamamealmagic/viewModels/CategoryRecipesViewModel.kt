package mk.ukim.finki.mamamealmagic.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mk.ukim.finki.mamamealmagic.models.CategoryRecipes
import mk.ukim.finki.mamamealmagic.models.CategoryResponse
import mk.ukim.finki.mamamealmagic.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryRecipesViewModel: ViewModel() {

    private var recipesLiveData = MutableLiveData<List<CategoryRecipes>>()

    fun getRecipesByCategory(categoryName: String){
        RetrofitInstance.recipeApi.getPopularRecipes(categoryName)
            .enqueue(object : Callback<CategoryResponse> {
                override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                    if(response.body() != null){
                        recipesLiveData.value = response.body()!!.meals
                    } else return
                }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    Log.e("CategoryRecipeViewModel", t.message.toString())
                }

            })
    }

    fun observeRecipesLiveData(): LiveData<List<CategoryRecipes>>{
        return recipesLiveData
    }
}