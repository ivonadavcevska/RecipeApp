package mk.ukim.finki.mamamealmagic.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mk.ukim.finki.mamamealmagic.models.Meal
import mk.ukim.finki.mamamealmagic.models.MealResponse
import mk.ukim.finki.mamamealmagic.retrofit.RetrofitInstance
import mk.ukim.finki.mamamealmagic.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(val appDatabase: AppDatabase): ViewModel() {

    private var recipeDetailsLiveData = MutableLiveData<Meal>()

    fun getRecipeDetails(id: String){
        RetrofitInstance.recipeApi
            .getRecipeDetails(id).enqueue(object : Callback<MealResponse>{
                    override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                        if(response.body() != null){
                            recipeDetailsLiveData.value = response.body()!!.meals[0]
                        }
                        else return
                    }
                    override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                        Log.d("RecipeActivity", t.message.toString())
                    }
                }
            )
    }

    fun observeRecipeDetailsLiveData(): LiveData<Meal>{
        return recipeDetailsLiveData
    }

    fun insertRecipe(meal: Meal){
        viewModelScope.launch {
            appDatabase.recipeDao().insert(meal)
        }
    }


}