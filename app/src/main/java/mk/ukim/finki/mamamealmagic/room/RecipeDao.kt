package mk.ukim.finki.mamamealmagic.room

import androidx.lifecycle.LiveData
import androidx.room.*
import mk.ukim.finki.mamamealmagic.models.Meal

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM Meals")
    fun getAll(): LiveData<List<Meal>>


}