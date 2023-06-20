package mk.ukim.finki.mamamealmagic.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mk.ukim.finki.mamamealmagic.models.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "recipe_database")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE as AppDatabase
        }
    }
}