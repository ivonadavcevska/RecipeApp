package mk.ukim.finki.mamamealmagic.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mk.ukim.finki.mamamealmagic.room.AppDatabase

class HomeViewModelFactory(private val appDatabase: AppDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(appDatabase) as T
    }
}