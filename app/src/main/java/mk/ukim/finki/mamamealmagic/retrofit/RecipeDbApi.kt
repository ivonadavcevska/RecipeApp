package mk.ukim.finki.mamamealmagic.retrofit

import mk.ukim.finki.mamamealmagic.models.CategoryList
import mk.ukim.finki.mamamealmagic.models.CategoryResponse
import mk.ukim.finki.mamamealmagic.models.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeDbApi {

    @GET("random.php")
    fun getRandomRecipe(): Call<MealResponse>

    @GET("lookup.php?")
    fun getRecipeDetails(@Query("i") id: String): Call<MealResponse>

    @GET("filter.php?")
    fun getPopularRecipes(@Query("c") category: String): Call<CategoryResponse>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("search.php?")
    fun searchRecipe(@Query("s") search: String) : Call<MealResponse>
}