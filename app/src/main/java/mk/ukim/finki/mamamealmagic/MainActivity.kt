package mk.ukim.finki.mamamealmagic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import mk.ukim.finki.mamamealmagic.room.AppDatabase
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModel
import mk.ukim.finki.mamamealmagic.viewModels.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by lazy {
        val appDatabase = AppDatabase.getInstance(this)
        val viewModelProviderFactory = HomeViewModelFactory(appDatabase)
        ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navigationController = Navigation.findNavController(this, R.id.hostFragment)

        NavigationUI.setupWithNavController(bottomNavigation, navigationController)
    }
}