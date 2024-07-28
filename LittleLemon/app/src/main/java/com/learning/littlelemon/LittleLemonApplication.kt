package com.learning.littlelemon

import android.app.Application
import androidx.room.Room
import com.learning.littlelemon.repository.Database
import com.learning.littlelemon.repository.MenuItemDao
import com.learning.littlelemon.repository.MenuRepository
import com.learning.littlelemon.viewmodel.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun provideDatabase(application: Application): Database =
    Room.databaseBuilder(
        application,
        Database::class.java,
        "table_menu_items"
    ).fallbackToDestructiveMigration().build()

fun provideDao(database: Database): MenuItemDao =
    database.menuItemDao()

val appModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }
    single { MenuRepository(get()) }
    viewModel { MenuViewModel(get()) }
}

class LittleLemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LittleLemonApplication)
            androidLogger()
            modules(appModule)
        }
    }
}
